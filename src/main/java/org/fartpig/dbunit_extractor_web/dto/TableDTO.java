package org.fartpig.dbunit_extractor_web.dto;

import java.util.ArrayList;
import java.util.List;

public class TableDTO {

	private List<RowDTO> rows = new ArrayList<>();
	private List<String> columnNames = new ArrayList<>();
	private String tableName;

	public List<RowDTO> getRows() {
		return rows;
	}

	public void setRows(List<RowDTO> rows) {
		this.rows = rows;
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
