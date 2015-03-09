package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Exceptions.DataException;
import jiangsir.zerobb.Interfaces.IAccessFilter;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.ArticleDAO;
import jiangsir.zerobb.Services.Article_TagDAO;
import jiangsir.zerobb.Services.TagDAO;
import jiangsir.zerobb.Services.UpfileDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Article_Tag;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;
import jiangsir.zerobb.Tools.FileUploader;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

@WebServlet(urlPatterns = { "/InsertArticle" })
@RoleSetting(allowHigherThen = User.ROLE.USER)
public class InsertArticle extends HttpServlet implements IAccessFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6375120086269725826L;

	// public static String urlpattern = InsertArticle.class.getAnnotation(
	// WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * jiangsir.zerobb.Interfaces.IAccessible#isAccessible(javax.servlet.http
	 * .HttpServletRequest)
	 */
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		// 只要有登入的都可以新增。
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("article", new Article());
		request.setAttribute("tags", new TagDAO().getTags());
		// HttpSession session = request.getSession(false);
		// String session_account = (String) session
		// .getAttribute("session_account");
		// request.setAttribute("userBean", new UserBean(session_account));
		request.getRequestDispatcher("InsertArticle.jsp").forward(request,
				response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// String session_account = (String) session
		// .getAttribute("session_account");
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		// Uploader uploader = new Uploader(request, response);
		FileUploader uploader = new FileUploader();
		try {
			uploader.parse(request, "UTF-8");
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Article newarticle = new Article();
		newarticle.setAccount(currentUser.getAccount());
		newarticle.setInfo(uploader.getParameter("info"));
		newarticle.setType(uploader.getParameter("type"));
		newarticle.setHyperlink(uploader.getParameter("hyperlink"));
		// newarticle.setPostdate(new Date(Utils.parseDatetime(
		// uploader.getParameter("postdate")).getTime()));
		// newarticle.setOutdate(new Date(Utils.parseDatetime(
		// uploader.getParameter("outdate")).getTime()));
		newarticle.setTitle(uploader.getParameter("title"));
		newarticle.setPostdate(uploader.getParameter("postdate"));
		newarticle.setOutdate(uploader.getParameter("outdate"));
		newarticle.setContent(uploader.getParameter("content"));
		int articleid;
		try {
			articleid = new ArticleDAO().insert(newarticle);
			Article_TagDAO article_tagDao = new Article_TagDAO();

			String[] tagnames = uploader.getParameterValues("tagname");
			for (int i = 0; tagnames != null && i < tagnames.length; i++) {
				Article_Tag article_tag = new Article_Tag();
				// tag.setId(articleid);
				article_tag.setArticleid(articleid);
				article_tag.setTagname(tagnames[i]);
				article_tagDao.insert(article_tag);
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

				// newupfile.setBinary(item.getInputStream());
				int upfileid = new UpfileDAO().insert(newupfile);
				newupfile.setId(upfileid);
				// 檔案是否就不用上傳了。
				// FileUploader.write2file(item, new
				// File(newupfile.getINNER_PATH(),
				// newupfile.getINNER_FILENAME()));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DataException(e);
		}

		response.sendRedirect("./?account=" + currentUser.getAccount());
	}
}
