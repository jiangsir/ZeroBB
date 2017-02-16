package jiangsir.zerobb.Tables;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import jiangsir.zerobb.Services.ArticleDAO;
import jiangsir.zerobb.Tables.User.DIVISION;

public class ArticleTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		User user1 = new User();
		user1.setRole(User.ROLE.DIVISION_LEADER);
		user1.setDivision(DIVISION.jiaowu);
		User user2 = new User();
		user2.setRole(User.ROLE.USER);
		user2.setDivision(DIVISION.jiaowu);

		Article article = new ArticleDAO().getArticleById(18766);
		// article.isUpdatable(user1);

	}

}
