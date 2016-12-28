package jiangsir.zerobb.Services;

import java.util.ArrayList;
import java.util.Calendar;

import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Factories.ArticleFactory;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.User;
import jiangsir.zerobb.Tables.User.DIVISION;
import jiangsir.zerobb.Tools.ENV;

public class ArticleService {

	public ArrayList<Article> getArticlesByTabnames(Article.INFO[] infos, String[] tagnames, int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticlesByTagnames(infos, tagnames, page, pagesize);
	}

	public ArrayList<Article> getArticlesByInfoDivision(Article.INFO[] infos, DIVISION division, int page,
			int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(infos, division, page, pagesize);
	}

	public ArrayList<Article> getHeadLines() {
		return new ArticleService().getArticlesByInfoDivision(new Article.INFO[]{Article.INFO.HEADLINE}, DIVISION.none,
				1, 10);
	}

	public ArrayList<Article> getArticlesByInfo(Article.INFO[] infos, int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(infos, null, page, pagesize);
	}

	public ArrayList<Article> getArticlesByDivision(DIVISION division, int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(null, division, page, pagesize);
	}

	public ArrayList<Article> getArticles(int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(null, null, page, pagesize);
	}

	public Article getArticleById(String articleid) {
		if (articleid == null || !articleid.matches("[0-9]+")) {
			return ArticleFactory.getNullArticle();
		}
		return new ArticleDAO().getArticleById(Integer.parseInt(articleid));
	}

	/**
	 * 
	 * @param articleid
	 * @return
	 * @throws AlertException
	 */
	public Article getArticle(CurrentUser currentUser, int articleid) throws AlertException {
		Article article = new ArticleDAO().getArticleById(articleid);
		// String sql = "SELECT * FROM articles WHERE id=" + articleid;
		//
		// for (Article a : executeQuery(sql, Article.class)) {
		// article = a;
		// break;
		// }

		if (currentUser == null) {
			if (article.getVisible() == false) {
				throw new AlertException("本公告已設定為不顯示！");
			}
			if (article.getOutdate().before(Calendar.getInstance().getTime())) {
				throw new AlertException("本公告已經過期，請登入發文者身份才能瀏覽。");
			}
			if (article.getPostdate().after(Calendar.getInstance().getTime())) {
				throw new AlertException("本公告尚未發佈。");
			}
		} else if (currentUser.getRole() == User.ROLE.USER) {
			if (article.getVisible() == false) {
				throw new AlertException("本公告已設定為不顯示！");
			}
		}
		return article;
	}

}
