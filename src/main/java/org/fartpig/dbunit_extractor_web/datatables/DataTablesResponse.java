package org.fartpig.dbunit_extractor_web.datatables;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thomas Weckert
 */
public class DataTablesResponse<T> implements Serializable {

	private static final long serialVersionUID = 4183379006497741525L;

	@JsonProperty(value = "iTotalRecords")
	public int totalRecords;

	@JsonProperty(value = "iTotalDisplayRecords")
	public int totalDisplayRecords;

	@JsonProperty(value = "sEcho")
	public String echo;

	@JsonProperty(value = "sColumns")
	public String columns;

	@JsonProperty(value = "aaData")
	public List<T> data = new ArrayList<T>();

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalDisplayRecords() {
		return totalDisplayRecords;
	}

	public void setTotalDisplayRecords(int totalDisplayRecords) {
		this.totalDisplayRecords = totalDisplayRecords;
	}

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
