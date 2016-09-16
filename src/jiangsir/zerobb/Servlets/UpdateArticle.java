package jiangsir.zerobb.Servlets;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

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
import jiangsir.zerobb.Tools.ENV;

@MultipartConfig(maxFileSize = 20 * 1024 * 1024, maxRequestSize = 50 * 1024 * 1024)
@WebServlet(urlPatterns = {"/UpdateArticle"}, name = "UpdateArticle.do")
public class UpdateArticle extends HttpServlet implements IAccessFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4970745549105351949L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	public void AccessFilter(HttpServletRequest request) throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		Article article = new ArticleDAO().getArticleById(request.getParameter("id"));
		if (!article.isUpdatable(currentUser)) {
			throw new AccessException("您(" + currentUser.getAccount() + ") 不能編輯本題目。");
		}
	}

	public String getFilename(Part part) {
		String header = part.getHeader("Content-Disposition");
		String filename = header.substring(header.indexOf("filename=\"") + 10, header.lastIndexOf("\""));
		return filename;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Article article = new ArticleDAO().getArticleById(request.getParameter("id"));

		request.setAttribute("tags", new TagDAO().getTags());
		request.setAttribute("article", article);
		request.setAttribute("maxFileSize", this.getClass().getAnnotation(MultipartConfig.class).maxFileSize());
		request.getRequestDispatcher("InsertArticle.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		int articleid = Integer.parseInt(request.getParameter("articleid"));
		Article article = new ArticleDAO().getArticleById(articleid);
		article.setInfo(request.getParameter("info"));
		article.setType(request.getParameter("type"));
		article.setHyperlink(request.getParameter("hyperlink"));
		try {
			article.setTitle(request.getParameter("title"));
			article.setPostdate(request.getParameter("postdate"));
			article.setOutdate(request.getParameter("outdate"));
		} catch (DataException e) {
			e.printStackTrace();
		}

		article.setContent(request.getParameter("content"));
		try {
			new ArticleDAO().update(article);
			String[] tagnames = request.getParameterValues("tagname");
			Article_TagDAO tagDao = new Article_TagDAO();
			tagDao.removeArticle_Tags(articleid);
			for (int i = 0; tagnames != null && i < tagnames.length; i++) {
				Article_Tag tag = new Article_Tag();
				tag.setId(articleid);
				tag.setArticleid(articleid);
				tag.setTagname(tagnames[i]);
				tagDao.insert(tag);
			}

			for (Part part : request.getParts()) {
				if ("upfile".equals(part.getName())) {
					if (part.getName() == null || part.getName().equals("")) {
						continue;
					}
					String filename = this.getFilename(part);
					Upfile newupfile = new Upfile();
					newupfile.setArticleid(articleid);
					newupfile.setFilename(filename);
					newupfile.setFiletype(part.getContentType());
					newupfile.setFilesize(part.getSize());
					newupfile.setBinary(part.getInputStream());

					int upfileid = new UpfileDAO().insert(newupfile);
					newupfile.setId(upfileid);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("./?account=" + currentUser.getAccount());
	}
}
