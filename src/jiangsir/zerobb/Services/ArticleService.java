package jiangsir.zerobb.Services;

import java.util.ArrayList;

import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.User.DIVISION;
import jiangsir.zerobb.Tools.ENV;

public class ArticleService {

	public ArrayList<Article> getArticlesByTabnames(String[] tagnames,
			int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticlesByTabnames(tagnames, page, pagesize);
	}

	public ArrayList<Article> getArticlesByInfoDivision(Article.INFO[] infos,
			DIVISION division, int page, int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(infos, division, page, pagesize);
	}

	public ArrayList<Article> getArticlesByInfo(Article.INFO[] infos, int page,
			int pagesize) {
		if (page <= 0) {
			page = 1;
		}
		if (pagesize <= 0) {
			page = ENV.getPAGESIZE();
		}
		return new ArticleDAO().getArticles(infos, null, page, pagesize);
	}

	public ArrayList<Article> getArticlesByDivision(DIVISION division,
			int page, int pagesize) {
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
}
