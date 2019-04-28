package org.fartpig.dbunit_extractor_web.dto;

import org.fartpig.dbunit_extractor_web.consts.Constants;

public class SearchConfigDTO {

	private int maxNum = Constants.DEFAULT_MAX_NUM;
	private String dbName;
	private int pageIndex;

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

}
