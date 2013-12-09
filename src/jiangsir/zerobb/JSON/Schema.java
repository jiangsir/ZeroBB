/**
 * idv.jiangsir.Object - JudgeObject.java
 * 2011/7/21 上午12:08:17
 * nknush-001
 */
package jiangsir.zerobb.JSON;

import java.util.LinkedHashMap;

/**
 * @author nknush-001
 * 
 */
public class Schema {

	private String version = "Unknown";
	LinkedHashMap<String, LinkedHashMap<String, Column>> tables = new LinkedHashMap<String, LinkedHashMap<String, Column>>();

	public String getVersion() {
		return version;
	}

	public LinkedHashMap<String, LinkedHashMap<String, Column>> getTables() {
		return tables;
	}

	public void setTables(
			LinkedHashMap<String, LinkedHashMap<String, Column>> tables) {
		this.tables = tables;
	}

}
