package jiangsir.zerobb.Factories;

import jiangsir.zerobb.Tables.Article;

public class ArticleFactory {
	private static Article nullArticle = new Article();

	public static Article getNullArticle() {
		return nullArticle;
	}

}
