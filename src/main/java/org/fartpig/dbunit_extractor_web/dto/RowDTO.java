package org.fartpig.dbunit_extractor_web.dto;

import java.util.ArrayList;
import java.util.List;

public class RowDTO {

	private List<String> columns = new ArrayList<>();

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

}
