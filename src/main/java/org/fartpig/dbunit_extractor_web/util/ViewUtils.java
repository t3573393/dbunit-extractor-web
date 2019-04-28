package org.fartpig.dbunit_extractor_web.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.dataset.ITableMetaData;
import org.fartpig.dbunit_extractor_web.consts.Constants;
import org.fartpig.dbunit_extractor_web.dto.RowDTO;
import org.fartpig.dbunit_extractor_web.dto.TableDTO;
import org.fartpig.dbunit_extractor_web.dto.TableSetDTO;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public final class ViewUtils {

	public static TableSetDTO convertToDTO(IDataSet dataSet) {
		TableSetDTO dto = new TableSetDTO();
		try {
			List<TableDTO> tables = new ArrayList<>();
			dto.setTables(tables);

			ITableIterator iterator = dataSet.iterator();
			while (iterator.next()) {
				TableDTO tableDto = new TableDTO();
				List<String> columnNames = new ArrayList<>();
				ITable table = iterator.getTable();
				tableDto.setTableName(table.getTableMetaData().getTableName());
				ITableMetaData tableMetaData = table.getTableMetaData();
				for (Column column : tableMetaData.getColumns()) {
					columnNames.add(column.getColumnName());
				}
				tableDto.setColumnNames(columnNames);
				int rowCount = table.getRowCount();

				List<RowDTO> rows = new ArrayList<>();
				if (rowCount > Constants.DEFAULT_MAX_NUM) {
					rowCount = Constants.DEFAULT_MAX_NUM;
				}
				tableDto.setRows(rows);
				for (int i = 0; i < rowCount; i++) {
					RowDTO rowDto = new RowDTO();
					List<String> columnValues = new ArrayList<>();
					rowDto.setColumns(columnValues);

					for (String aColumnName : columnNames) {
						String columnValue = String.valueOf(table.getValue(i, aColumnName));
						columnValues.add(columnValue);
					}
					rows.add(rowDto);
				}
				tables.add(tableDto);
			}
		} catch (DataSetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return dto;

	}

	public static String prettyPrintXml(String originalXml) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			Source source = new StreamSource(new StringReader(originalXml));
			StringWriter strWriter = new StringWriter();
			StreamResult result = new StreamResult(strWriter);
			transformer.transform(source, result);
			return strWriter.getBuffer().toString();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return originalXml;
	}

	public static String getMessage(MessageSource messageSource, String result, Object[] params) {
		String message = "";
		try {
			Locale locale = LocaleContextHolder.getLocale();
			message = messageSource.getMessage(result, params, locale);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

}
