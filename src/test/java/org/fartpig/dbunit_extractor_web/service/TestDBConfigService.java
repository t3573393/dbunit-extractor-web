package org.fartpig.dbunit_extractor_web.service;

import org.fartpig.dbunit_extractor_web.model.DBConfig;

import junit.framework.TestCase;

public class TestDBConfigService extends TestCase {

	DBConfigService configService;

	public void setUp() {
		configService = new DBConfigService();
	}

	public void testGetAllDBConfig() {
		configService.getAllDBConfig();
	}

	public void testAddDBConfig() {
		DBConfig dbConfig = new DBConfig();
		dbConfig.setDbType("oracle");
		dbConfig.setDriverName("oracle.jdbc.driver.OracleDriver");
		dbConfig.setName("myTestOracle");
		dbConfig.setPassword("zzzzz");
		dbConfig.setUrl("wwww.baidu.com");
		dbConfig.setUser("w3c");
		configService.addDBConfig(dbConfig);

		dbConfig = new DBConfig();
		dbConfig.setDbType("oracle");
		dbConfig.setDriverName("oracle.jdbc.driver.OracleDriver");
		dbConfig.setName("myTestOracle2");
		dbConfig.setPassword("zzzzz");
		dbConfig.setUrl("wwww.baidu.com2");
		dbConfig.setUser("w3c");

		configService.addDBConfig(dbConfig);
	}

	public void testRemoveDBConfig() {
		DBConfig dbConfig = new DBConfig();
		dbConfig.setName("myTestOracle2");
		configService.removeDBConfig(dbConfig);
	}

}
