package org.fartpig.dbunit_extractor_web.dto;

import java.util.ArrayList;
import java.util.List;

public class TableSetDTO {

	private List<TableDTO> tables = new ArrayList<>();

	public List<TableDTO> getTables() {
		return tables;
	}

	public void setTables(List<TableDTO> tables) {
		this.tables = tables;
	}

}
