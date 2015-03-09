package jiangsir.zerobb.Servlets.Ajax;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jiangsir.zerobb.Annotations.RoleSetting;
import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Interfaces.IAccessFilter;
import jiangsir.zerobb.Scopes.SessionScope;
import jiangsir.zerobb.Services.ArticleDAO;
import jiangsir.zerobb.Services.UpfileDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.Upfile;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tools.ENV;

@WebServlet(urlPatterns = { "/DeleteUpfile.api" })
@RoleSetting(allowHigherThen = User.ROLE.USER)
public class DeleteUpfileServlet extends HttpServlet implements IAccessFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3342080862925660852L;

	@Override
	public void init() throws ServletException {
		super.init();
		ENV.putServlet(this.getClass());
	}

	@Override
	public void AccessFilter(HttpServletRequest request) throws AccessException {
		HttpSession session = request.getSession(false);
		CurrentUser currentUser = new SessionScope(session).getCurrentUser();
		int upfileid = Integer.parseInt(request.getParameter("upfileid"));
		Upfile upfile = new UpfileDAO().getUpfile(upfileid);
		Article article = new ArticleDAO()
				.getArticleById(upfile.getArticleid());

		if (!article.isUpdatable(currentUser)) {
			throw new AccessException("您(" + currentUser.getAccount()
					+ ") 不能刪除這個附件。");
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		int upfileid = Integer.parseInt(request.getParameter("upfileid"));
		Upfile upfile = new UpfileDAO().getUpfile(upfileid);

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
