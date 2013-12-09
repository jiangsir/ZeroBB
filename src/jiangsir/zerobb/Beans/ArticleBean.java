/**
 * idv.jiangsir.Beans - CourseBean.java
 * 2009/12/20 下午5:07:14
 * nknush-001
 */
package jiangsir.zerobb.Beans;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import jiangsir.zerobb.DAOs.UpfileDAO;
import jiangsir.zerobb.Tables.Article;
import jiangsir.zerobb.Tables.Upfile;

/**
 * @author nknush-001
 * 
 */
public class ArticleBean extends Article {
	private String session_account = "";
	private ArrayList<Upfile> upfiles;

	public ArticleBean() {
	}

	public ArticleBean(Article article) {
		for (Field field : article.getClass().getDeclaredFields()) {
			String name = field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1);
			Method getter;
			Method setter;
			try {
				getter = article.getClass().getMethod("get" + name,
						new Class[] {});
				Object obj = getter.invoke(article, new Object() {
				});
				setter = article.getClass().getMethod("set" + name,
						new Class[] { obj.getClass() });
				setter.invoke(this, new Object[] { obj });
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.setUpfiles(new UpfileDAO().getUpfiles(article.getId()));
	}

	public String getSession_account() {
		return session_account;
	}

	public void setSession_account(String sessionAccount) {
		session_account = sessionAccount;
	}

	// ===================================================================================


	public boolean isAccessable() {
		if (!"".equals(session_account)
				&& ("admin".equals(session_account) || this.getAccount()
						.equals(session_account))) {
			return true;
		}
		return false;
	}

	public ArrayList<Upfile> getUpfiles() {
		return upfiles;
	}

	public void setUpfiles(ArrayList<Upfile> upfiles) {
		this.upfiles = upfiles;
	}
}
