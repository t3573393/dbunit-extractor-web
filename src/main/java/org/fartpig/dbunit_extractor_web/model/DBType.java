package org.fartpig.dbunit_extractor_web.model;

import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.dbunit.ext.mssql.MsSqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.oracle.OracleDataTypeFactory;

public enum DBType {

	MYSQL("com.mysql.jdbc.Driver", "jdbc:mysql://", MySqlDataTypeFactory.class), ORACLE(
			"oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@",
			OracleDataTypeFactory.class), SQLSERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver",
					"jdbc:sqlserver://", MsSqlDataTypeFactory.class);

	private String driverName;
	private String jdbcPrefix;
	private Class<? extends DefaultDataTypeFactory> dataTypeFactoryClass;

	private DBType(String driverName, String jdbcPrefix,
			Class<? extends DefaultDataTypeFactory> dataTypeFactoryClass) {

		this.driverName = driverName;
		this.jdbcPrefix = jdbcPrefix;
		this.dataTypeFactoryClass = dataTypeFactoryClass;
	}

	public String getDriverName() {

		return driverName;
	}

	public String getJdbcPrefix() {

		return jdbcPrefix;
	}

	public Class<? extends DefaultDataTypeFactory> getDataTypeFactoryClass() {

		return dataTypeFactoryClass;
	}

	public static DBType getDbTypeByString(String type) throws IllegalArgumentException {

		for (DBType dbType : DBType.values()) {
			if (dbType.name().equalsIgnoreCase(type.trim())) {
				return dbType;
			}
		}

		throw new IllegalArgumentException("doesn't support db type. type=" + type.trim());
	}
}