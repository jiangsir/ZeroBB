package jiangsir.zerobb.DAOs;

import java.util.ArrayList;
import jiangsir.zerobb.Tables.Loginlog;
import jiangsir.zerobb.Tools.Utils;

public class LoginlogDAO extends GeneralDAO<Loginlog> {

	public LoginlogDAO() {

	}

	public int insert(Loginlog loginlog) {
		String sql = "INSERT INTO loginlogs (userid, useraccount, ipaddr, "
				+ "message, logintime, logouttime)VALUES('"
				+ loginlog.getUserid() + "', '" + loginlog.getUseraccount()
				+ "', '" + loginlog.getIpaddr() + "', '"
				+ loginlog.getMessage() + "', '"
				+ Utils.parseDatetime(loginlog.getLogintime().getTime())
				+ "', '"
				+ Utils.parseDatetime(loginlog.getLogouttime().getTime())
				+ "')";
		this.execute(sql);
		return 0;
	}

	@Override
	public int update(Loginlog t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Loginlog> getLoginlogs(String useraccount) {
		String sql = "SELECT * FROM loginlogs WHERE useraccount='"
				+ useraccount + "'";
		return this.executeQuery(sql, Loginlog.class);
	}

	public ArrayList<Loginlog> getLoginlogs(Integer userid) {
		String sql = "SELECT * FROM loginlogs WHERE userid=" + userid;
		return this.executeQuery(sql, Loginlog.class);
	}

}
