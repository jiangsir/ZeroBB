package jiangsir.zerobb.Servlets.Ajax;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.DAOs.ArticleDAO;
import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Servlets.DeleteArticle;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/DeleteUpfile" }, name = "DeleteUpfile.ajax")
public class DeleteUpfileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3342080862925660852L;
	public static String urlpattern = DeleteUpfileServlet.class.getAnnotation(
			WebServlet.class).urlPatterns()[0];

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	Upfile upfile;
	Article article;

	public boolean isAccessable(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String session_account = (String) session
				.getAttribute("session_account");
		int upfileid = Integer.parseInt(request.getParameter("upfileid"));
		upfile = new UpfileDAO().getUpfile(upfileid);
		article = new ArticleDAO().getArticleById(upfile.getArticleid());
		System.out.println("session_account=" + session_account
				+ ", article.account=" + article.getAccount());

		if (session_account != null
				&& !session_account.equals("")
				&& !article.getAccount().equals("")
				&& (session_account.equals(article.getAccount()) || session_account
						.equals("admin"))) {
			return true;
		}
		return false;
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!this.isAccessable(request, response)) {
			return;
		}
		File file;
		if (upfile.getId() <= Upfile.OLD_UPFILDID) { // 最早版本的檔案放置位置。放置在 /zerobb/
			// 各不同帳號內
			file = new File(ENV.APP_REAL_PATH + upfile.getFilepath(),
					upfile.getFilename());
		} else if (upfile.getId() <= Upfile.FILE_UPFILDID) {
			// 全部放在根目錄的 upfiles 裡。但必須透過 upfile servlet 才能存取。
			file = new File(upfile.getINNER_PATH(), upfile.getINNER_FILENAME());
		} else {
			// 最新的檔案處理方式使用 blob 因此直接將 upfile 資料欄位刪除即可刪除。
			new UpfileDAO().delete(upfile.getId());
			return;
		}
		System.out.println("RemoveUpfile filepath=" + file.getPath());
		if (file.delete()) {
			System.out.println("刪除成功！");
			new UpfileDAO().delete(upfile.getId());
		} else {
			System.out.println("刪除失敗！");
		}
		return;
	}
}
