package org.fartpig.dbunit_extractor_web.datatables;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Thomas Weckert
 */
public class DataTablesRequest implements Serializable {

	private static final long serialVersionUID = -4961407246623307317L;

	@JsonProperty(value = "sEcho")
	public String echo;

	@JsonProperty(value = "iColumns")
	public int numColumns;

	@JsonProperty(value = "sColumns")
	public String columns;

	@JsonProperty(value = "iDisplayStart")
	public int displayStart;

	@JsonProperty(value = "iDisplayLength")
	public int displayLength;

	@JsonProperty(value = "amDataProp")
	public List<Integer> dataProp;

	@JsonProperty(value = "sSearch")
	public String searchQuery;

	@JsonProperty(value = "asSearch")
	public List<String> columnSearches;

	@JsonProperty(value = "bRegex")
	public boolean hasRegex;

	@JsonProperty(value = "abRegex")
	public List<Boolean> regexColumns;

	@JsonProperty(value = "abSearchable")
	public List<Boolean> searchColumns;

	@JsonProperty(value = "iSortingCols")
	public int sortingCols;

	@JsonProperty(value = "aiSortCol")
	public List<Integer> sortedColumns;

	@JsonProperty(value = "asSortDir")
	public List<String> sortDirections;

	@JsonProperty(value = "abSortable")
	public List<Boolean> sortableColumns;

	public String getEcho() {
		return echo;
	}

	public void setEcho(String echo) {
		this.echo = echo;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public int getDisplayStart() {
		return displayStart;
	}

	public void setDisplayStart(int displayStart) {
		this.displayStart = displayStart;
	}

	public int getDisplayLength() {
		return displayLength;
	}

	public void setDisplayLength(int displayLength) {
		this.displayLength = displayLength;
	}

	public List<Integer> getDataProp() {
		return dataProp;
	}

	public void setDataProp(List<Integer> dataProp) {
		this.dataProp = dataProp;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public List<String> getColumnSearches() {
		return columnSearches;
	}

	public void setColumnSearches(List<String> columnSearches) {
		this.columnSearches = columnSearches;
	}

	public boolean isHasRegex() {
		return hasRegex;
	}

	public void setHasRegex(boolean hasRegex) {
		this.hasRegex = hasRegex;
	}

	public List<Boolean> getRegexColumns() {
		return regexColumns;
	}

	public void setRegexColumns(List<Boolean> regexColumns) {
		this.regexColumns = regexColumns;
	}

	public List<Boolean> getSearchColumns() {
		return searchColumns;
	}

	public void setSearchColumns(List<Boolean> searchColumns) {
		this.searchColumns = searchColumns;
	}

	public int getSortingCols() {
		return sortingCols;
	}

	public void setSortingCols(int sortingCols) {
		this.sortingCols = sortingCols;
	}

	public List<Integer> getSortedColumns() {
		return sortedColumns;
	}

	public void setSortedColumns(List<Integer> sortedColumns) {
		this.sortedColumns = sortedColumns;
	}

	public List<String> getSortDirections() {
		return sortDirections;
	}

	public void setSortDirections(List<String> sortDirections) {
		this.sortDirections = sortDirections;
	}

	public List<Boolean> getSortableColumns() {
		return sortableColumns;
	}

	public void setSortableColumns(List<Boolean> sortableColumns) {
		this.sortableColumns = sortableColumns;
	}

}
