package jiangsir.zerobb.TLDs;

import jiangsir.zerobb.Tables.CurrentUser;
import jiangsir.zerobb.Tables.User;

public class UserTLD {
	/**
	 * 給 JSTL 呼叫是用在 View 端的
	 * 
	 * @return
	 */

	public static boolean isAdmin(CurrentUser currentUser) {
		if (currentUser == null) {
			return false;
		}
		return currentUser.getRole() == User.ROLE.ADMIN;
	}

}
