package jiangsir.zerobb.Factories;

import jiangsir.zerobb.Tables.User;

public class UserFactory {
	private static User nullUser = new User();

	public static User getNullUser() {
		return nullUser;
	}

}
