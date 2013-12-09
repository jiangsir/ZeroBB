package jiangsir.zerobb.DAOs;

import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import jiangsir.zerobb.JSON.Column;
import jiangsir.zerobb.JSON.Schema;

public class DBUpdateDAO extends GeneralDAO<Schema> {

	@Override
	public int insert(Schema t) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Schema t) throws SQLException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public Schema getSchema() {
		DatabaseMetaData meta;
		Schema schema = new Schema();

		try {
			meta = this.getConnection().getMetaData();
			String[] types = { "TABLE" };
			ResultSet tableRs = meta.getTables(null, "zerobb", null, types);
			LinkedHashMap<String, LinkedHashMap<String, Column>> tables = new LinkedHashMap<String, LinkedHashMap<String, Column>>();
			while (tableRs.next()) {
				String tableName = tableRs.getString("TABLE_NAME");
				System.out.println("tableName=" + tableName);
				ResultSet rsColumns = meta.getColumns(null, "zerobb",
						tableName, null);
				// TreeSet<Column> columns = new TreeSet<Column>();
				LinkedHashMap<String, Column> columns = new LinkedHashMap<String, Column>();
				while (rsColumns.next()) {
					Column column = new Column();
					column.setName(rsColumns.getString("COLUMN_NAME"));
					column.setTypename(rsColumns.getString("TYPE_NAME"));
					column.setSize(rsColumns.getString("COLUMN_SIZE"));
					columns.put(column.getName(), column);
				}
				tables.put(tableName, columns);
			}
			schema.setTables(tables);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return schema;
	}

	/**
	 * 把所有的 article 加上 default_tag
	 */
	public void updateDefault_tags() {
	}
}
