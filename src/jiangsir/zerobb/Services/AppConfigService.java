package jiangsir.zerobb.Services;

import java.sql.SQLException;
import java.util.TreeMap;

import jiangsir.zerobb.Exceptions.AlertException;
import jiangsir.zerobb.Tables.AppConfig;

public class AppConfigService {

	public int insert(AppConfig appConfig) throws AlertException {
		new AppConfigDAO().truncate();
		try {
			return new AppConfigDAO().insert(appConfig);
		} catch (SQLException e) {
			throw new AlertException(e.getLocalizedMessage());
		}
	}

	public void update(AppConfig appConfig) throws AlertException {
		try {
			new AppConfigDAO().update(appConfig);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AlertException(e.getLocalizedMessage());
		}
	}

	public void delete(long id) throws AlertException {
		try {
			new AppConfigDAO().delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AlertException(e.getLocalizedMessage());
		}
	}

	public AppConfig getAppConfig() {
		TreeMap<String, Object> fields = new TreeMap<String, Object>();
		for (AppConfig appConfig : new AppConfigDAO().getAppConfigByFields(fields, "id DESC", 0)) {
			return appConfig;
		}
		return new AppConfig();
	}

}
