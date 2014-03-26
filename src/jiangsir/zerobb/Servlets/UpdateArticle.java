package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.Article_TagDAO;
import jiangsir.zerobb.DAOs.TagDAO;
import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Interfaces.IAccessible;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.ArticleService;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Article_Tag;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.FileUploader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

@WebServlet(urlPatterns = { "/UpdateArticle" }, name = "UpdateArticle.do")
public class UpdateArticle extends HttpServlet implements IAccessible {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4970745549105351949L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	public boolean isAccessible(HttpServletRequest request)
			throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticleById(request
				.getParameter("id"));
		try {
			return article.isUpdatable(currentUser);
		} catch (DataException e) {
			e.printStackTrace();

			// throw new AccessException(currentUser.getAccount(), "您("
			// + currentUser.getAccount() + ") 不能編輯本題目。");
			// throw new AccessException("您(" + currentUser.getAccount()
			// + ") 不能編輯本題目。", e);
			throw new AccessException(e);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Article article = new ArticleDAO().getArticleById(request
				.getParameter("id"));

		request.setAttribute("tags", new TagDAO().getTags());
		// request.setAttribute("userBean", new
		// UserBean_Deprecated(article.getAccount()));
		request.setAttribute("article", article);
		request.setAttribute("article_tags",
				new Article_TagDAO().getArticle_TagNames(article.getId()));
		request.getRequestDispatcher("InsertArticle.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();

		// Uploader uploader = new Uploader(request, response);
		FileUploader uploader = new FileUploader();
		try {
			uploader.parse(request, "UTF-8");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		int articleid = Integer.parseInt(uploader.getParameter("articleid"));
		Article article = new ArticleDAO().getArticleById(articleid);
		// if (!this.isAccessable(session_account, article)) {
		// Message message = new Message();
		// message.setType(Message.MessageType_ERROR);
		// message.setPlainTitle("您不能編輯此文件！");
		// request.setAttribute("message", message);
		// ExceptionDAO exceptionDao = new ExceptionDAO();
		// exceptionDao.insert(request.getMethod() + ": "
		// + request.getRequestURL().toString(), session_account,
		// request.getRemoteAddr(), "您不能編輯此文件！", "id=" + articleid
		// + ", 擁有者：" + article.getAccount());
		// request.getRequestDispatcher("Message.jsp").forward(request,
		// response);
		// return;
		// }
		article.setInfo(uploader.getParameter("info"));
		article.setType(uploader.getParameter("type"));
		article.setHyperlink(uploader.getParameter("hyperlink"));
		// article.setPostdate(new Timestamp(Utils.parseDatetime(
		// uploader.getParameter("postdate")).getTime()));
		// article.setOutdate(new Timestamp(Utils.parseDatetime(
		// uploader.getParameter("outdate")).getTime()));
		try {
			article.setTitle(uploader.getParameter("title"));
			article.setPostdate(uploader.getParameter("postdate"));
			article.setOutdate(uploader.getParameter("outdate"));
		} catch (DataException e) {
			e.printStackTrace();
		}

		article.setContent(uploader.getParameter("content"));
		try {
			new ArticleDAO().update(article);
			String[] tagnames = uploader.getParameterValues("tagname");
			Article_TagDAO tagDao = new Article_TagDAO();
			tagDao.removeArticle_Tags(articleid);
			for (int i = 0; tagnames != null && i < tagnames.length; i++) {
				Article_Tag tag = new Article_Tag();
				tag.setId(articleid);
				tag.setArticleid(articleid);
				tag.setTagname(tagnames[i]);
				tagDao.insert(tag);
			}
			for (FileItem item : uploader.getFileItemList("upfile")) {
				if (item.getName() == null || item.getName().equals("")) {
					continue;
				}
				// IE 會傳上完整的路徑，不好
				String filename = item.getName();
				filename = filename.substring(filename.lastIndexOf("\\") + 1);

				// 再 加入資料庫記錄
				Upfile newupfile = new Upfile();
				newupfile.setArticleid(articleid);
				newupfile.setFilename(filename);
				newupfile.setFiletype(item.getContentType());
				newupfile.setFilesize((long) item.getSize());

				newupfile.setBinary(item.getInputStream());

				int upfileid = new UpfileDAO().insert(newupfile);
				newupfile.setId(upfileid);
				// 先將檔案上傳
				// FileUploader.write2file(item, new
				// File(newupfile.getINNER_PATH(),
				// newupfile.getINNER_FILENAME()));
				// uploader.uploadFile(item, newupfile.getINNER_PATH(),
				// newupfile
				// .getINNER_FILENAME());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./?account=" + currentUser.getAccount());
	}
}
