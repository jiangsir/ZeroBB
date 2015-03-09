package jiangsir.zerobb.Services;

import jiangsir.zerobb.Tables.Download;

public class DownloadDAO extends GeneralDAO<Download> {

	@Override
	public int insert(Download t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Download t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public Download getDownload(Integer id) {
		String sql = "SELECT * FROM downloads WHERE id=" + id;
		for (Download download : this.executeQuery(sql, Download.class)) {
			return download;
		}
		return new Download();
	}

}
