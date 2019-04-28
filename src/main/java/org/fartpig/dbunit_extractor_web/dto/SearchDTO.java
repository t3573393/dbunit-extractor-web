package org.fartpig.dbunit_extractor_web.dto;

import org.fartpig.dbunit_extractor_web.consts.Constants;

public class SearchDTO {

	private int maxNum = Constants.DEFAULT_MAX_NUM;

	private String sqlQuery;
	private String dbName;

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public void setSqlQuery(String sqlQuery) {
		this.sqlQuery = sqlQuery;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
