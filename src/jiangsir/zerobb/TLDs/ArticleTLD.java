package jiangsir.zerobb.TLDs;

import jiangsir.zerobb.Exceptions.AccessException;
import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.CurrentUser;

public class ArticleTLD {
	/**
	 * 給 JSTL 呼叫是用在 View 端的
	 * 
	 * @return
	 */

	public static boolean isUpdatable(Article article, CurrentUser currentUser) {
		try {
			return article.isUpdatable(currentUser);
		} catch (AlertException e) {
			return false;
		}
	}

}
