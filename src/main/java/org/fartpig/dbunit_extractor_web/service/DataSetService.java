package org.fartpig.dbunit_extractor_web.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.CachedResultSetTableFactory;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.XmlDataSet;
import org.fartpig.dbunit_extractor_web.consts.Constants;
import org.fartpig.dbunit_extractor_web.dto.SearchDTO;
import org.fartpig.dbunit_extractor_web.dto.TableSetDTO;
import org.fartpig.dbunit_extractor_web.model.DBConfig;
import org.fartpig.dbunit_extractor_web.model.DBType;
import org.fartpig.dbunit_extractor_web.util.ViewUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.util.TablesNamesFinder;

@Service
public class DataSetService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DataSetService.class);
	private static CCJSqlParserManager parserManager = new CCJSqlParserManager();

	@Autowired
	DBConfigService dbConfigService;

	private IDataSet findDataSetInternal(SearchDTO dto, IDatabaseConnection connection) {
		String dbName = dto.getDbName();
		DBConfig config = dbConfigService.getConfigByName(dbName);
		if (config != null) {
			try {
				LOGGER.debug("connect success.");
				String sqlQuery = dto.getSqlQuery();
				String[] queries = sqlQuery.split("\n");

				// Paginator paginator = new
				// Paginator(org.fartpig.dbunit_extractor_web.util.Database
				// .valueOf(config.getDbType().toUpperCase()));
				//
				// List<String> newSqls = new ArrayList<>();
				// for (String aQuery : queries) {
				// // convert sql to paginate style
				// if ("oracle".equalsIgnoreCase(config.getDbType())) {
				// String newSql = aQuery;
				// if (!aQuery.contains("rownum") && !aQuery.contains("ROWNUM"))
				// {
				// newSql = paginator.paginate(aQuery, dto.getMaxNum());
				// }
				// newSqls.add(newSql);
				// } else if ("mysql".equalsIgnoreCase(config.getDbType())) {
				// String newSql = aQuery;
				// if (!aQuery.contains("limit") && !aQuery.contains("LIMIT")) {
				// newSql = paginator.paginate(aQuery, dto.getMaxNum());
				// }
				// newSqls.add(newSql);
				// } else if ("sqlserver".equalsIgnoreCase(config.getDbType()))
				// {
				// String newSql = aQuery;
				// if (!aQuery.contains("top") && !aQuery.contains("TOP")) {
				// newSql = paginator.paginate(aQuery, dto.getMaxNum());
				// }
				// newSqls.add(newSql);
				// } else {
				// // other db, just use the original sql
				// String newSql = aQuery;
				// newSqls.add(newSql);
				// }
				// }
				//
				// IDataSet dataSet = createDataSet(connection,
				// newSqls.toArray(new String[newSqls.size()]));
				IDataSet dataSet = createDataSet(connection, queries);
				return dataSet;
			} catch (AmbiguousTableNameException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		return null;
	}

	private String getTableName(String originSql) {
		Statement statement = null;
		try {
			statement = parserManager.parse(new StringReader(originSql));
		} catch (JSQLParserException e) {
			LOGGER.error("parse error:" + originSql, e);
		}

		// parse fail ,then return the original sql
		if (statement == null) {
			return originSql;
		}
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableNames = tablesNamesFinder.getTableList(statement);
		if (tableNames.size() > 0) {
			return tableNames.get(0);
		}
		return originSql;
	}

	public TableSetDTO findDataSet(SearchDTO dto) {
		String dbName = dto.getDbName();
		DBConfig config = dbConfigService.getConfigByName(dbName);
		if (config != null) {
			IDatabaseConnection connection = null;
			try {
				connection = getConnection(config);
				IDataSet dataSet = findDataSetInternal(dto, connection);
				if (dataSet != null) {
					return ViewUtils.convertToDTO(dataSet);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
				}
			}
		}

		return new TableSetDTO();
	}

	private QueryDataSet createDataSet(IDatabaseConnection connection, String[] queries)
			throws AmbiguousTableNameException {

		try {
			QueryDataSet dataSet = new QueryDataSet(connection);
			for (String query : queries) {
				if (query.trim().toUpperCase().startsWith("SELECT")) {
					dataSet.addTable(getTableName(query.trim()), query.trim());
				} else {
					dataSet.addTable(query.trim());
				}
			}
			return dataSet;
		} catch (AmbiguousTableNameException e) {
			throw new AmbiguousTableNameException("createDataSet is failed. tableName is ambiguous",
					e);
		}
	}

	private IDatabaseConnection getConnection(DBConfig config) throws Exception {

		try {
			DBType dbType = DBType.getDbTypeByString(config.getDbType());
			Class.forName(dbType.getDriverName());
			Connection connection = DriverManager.getConnection(config.getUrl(), config.getUser(),
					config.getPassword());
			IDatabaseConnection dbConnection = new DatabaseConnection(connection);

			DatabaseConfig dbConfig = dbConnection.getConfig();
			dbConfig.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,
					dbType.getDataTypeFactoryClass().newInstance());
			dbConfig.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY,
					new CachedResultSetTableFactory());
			dbConfig.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, Constants.DEFAULT_MAX_NUM);
			dbConfig.setProperty(DatabaseConfig.PROPERTY_FETCH_SIZE, Constants.DEFAULT_MAX_NUM);
			return dbConnection;
		} catch (Exception e) {
			throw new Exception("getConnection is failed.", e);
		}
	}

	public String extractDataSetBySearchDto(SearchDTO dto) {
		StringWriter sw = new StringWriter();
		String dbName = dto.getDbName();
		DBConfig config = dbConfigService.getConfigByName(dbName);
		if (config != null) {
			IDatabaseConnection connection = null;
			try {
				connection = getConnection(config);
				IDataSet dataSet = findDataSetInternal(dto, connection);
				XmlDataSet.write(dataSet, sw);
			} catch (DataSetException | IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
				}
			}
		}
		return sw.toString();
	}

	public void extractDataSetBySearchDto(SearchDTO dto, OutputStream out) {
		String dbName = dto.getDbName();
		DBConfig config = dbConfigService.getConfigByName(dbName);
		if (config != null) {
			IDatabaseConnection connection = null;
			try {
				connection = getConnection(config);
				IDataSet dataSet = findDataSetInternal(dto, connection);
				XmlDataSet.write(dataSet, out);
			} catch (DataSetException | IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
				}
			}
		}
	}
}
