package org.fartpig.dbunit_extractor_web.service;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.fartpig.dbunit_extractor_web.consts.Constants;
import org.fartpig.dbunit_extractor_web.model.DBAllConfig;
import org.fartpig.dbunit_extractor_web.model.DBConfig;
import org.fartpig.dbunit_extractor_web.util.PathUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DBConfigService {

	private DBAllConfig allConfig;

	private String getFilePath() {
		return PathUtils.getLocation() + "/" + Constants.DBCONFIGFILE_NAME;
	}

	private boolean saveToFile(DBAllConfig allConfigs) {
		try {
			File file = new File(getFilePath());
			System.out.println(getFilePath());
			if (!file.exists()) {
				file.createNewFile();
			}

			JAXBContext jc = JAXBContext.newInstance(DBAllConfig.class);
			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(allConfigs, file);
			return true;
		} catch (JAXBException | IOException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	public DBAllConfig getAllDBConfig() {
		try {
			if (allConfig != null) {
				return allConfig;
			}
			File xmlFile = new File(getFilePath());
			if (xmlFile.exists()) {
				JAXBContext jc = JAXBContext.newInstance(DBAllConfig.class);
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				allConfig = (DBAllConfig) unmarshaller.unmarshal(xmlFile);
				return allConfig;
			}
		} catch (JAXBException e1) {
			e1.printStackTrace();
		}
		allConfig = new DBAllConfig();
		allConfig.setConfigs(new DBConfig[0]);
		return allConfig;
	}

	public Page<DBConfig> getAllDBConfigForPage(Pageable pageable) {
		DBConfig[] resultConfigs = new DBConfig[0];

		DBAllConfig allConfigs = getAllDBConfig();
		DBConfig[] configs = allConfigs.getConfigs();
		if (configs.length >= pageable.getOffset()) {
			resultConfigs = Arrays.copyOfRange(configs, pageable.getOffset(),
					pageable.getOffset() + pageable.getPageSize() > configs.length ? configs.length
							: pageable.getOffset() + pageable.getPageSize());
		} else {
			resultConfigs = configs;
		}
		return new PageImpl<DBConfig>(Arrays.asList(resultConfigs), pageable, configs.length);
	}

	public Page<DBConfig> getConfigStartWithName(String dbName, Pageable pageable) {
		DBAllConfig allConfig = this.getAllDBConfig();

		List<DBConfig> resultConfigs = new ArrayList<>();
		for (DBConfig config : allConfig.getConfigs()) {
			if (dbName == null || dbName.length() == 0 || config.getName().startsWith(dbName)) {
				resultConfigs.add(config);
			}
		}

		if (resultConfigs.size() >= pageable.getOffset()) {
			resultConfigs = resultConfigs.subList(pageable.getOffset(),
					pageable.getOffset() + pageable.getPageSize() > resultConfigs.size()
							? resultConfigs.size()
							: pageable.getOffset() + pageable.getPageSize());
		}

		return new PageImpl<DBConfig>(resultConfigs, pageable, allConfig.getConfigs().length);
	}

	public DBConfig getConfigByName(String dbName) {
		DBAllConfig allConfig = this.getAllDBConfig();
		for (DBConfig config : allConfig.getConfigs()) {
			if (dbName.equalsIgnoreCase(config.getName())) {
				return config;
			}
		}
		return null;
	}

	public boolean addDBConfig(DBConfig dbConfig) {
		boolean result = true;
		DBAllConfig allConfigs = getAllDBConfig();
		for (DBConfig config : allConfigs.getConfigs()) {
			if (dbConfig.getName().equalsIgnoreCase(config.getName())) {
				result = false;
				break;
			}
		}

		if (result) {
			int size = allConfigs.getConfigs().length;
			DBConfig[] newAllConfigs = Arrays.copyOf(allConfigs.getConfigs(), size + 1);
			newAllConfigs[newAllConfigs.length - 1] = dbConfig;
			allConfigs.setConfigs(newAllConfigs);

			result = saveToFile(allConfigs);

		}

		return result;
	}

	public boolean updateDBConfig(DBConfig dbConfig) {
		boolean result = false;
		DBAllConfig allConfigs = getAllDBConfig();
		for (DBConfig config : allConfigs.getConfigs()) {
			if (dbConfig.getName().equalsIgnoreCase(config.getName())) {
				BeanUtils.copyProperties(dbConfig, config);
				result = true;
				break;
			}
		}
		if (result) {
			result = saveToFile(allConfigs);
		}
		return result;
	}

	public boolean removeDBConfig(DBConfig dbConfig) {
		boolean result = false;
		DBAllConfig allConfigs = getAllDBConfig();
		DBConfig[] newAllConfigs = new DBConfig[allConfigs.getConfigs().length - 1];
		int index = 0;
		for (DBConfig config : allConfigs.getConfigs()) {
			if (!dbConfig.getName().equalsIgnoreCase(config.getName())) {
				newAllConfigs[index++] = config;
			} else {
				result = true;
			}
		}

		if (result) {
			allConfigs.setConfigs(newAllConfigs);
			result = saveToFile(allConfigs);
		}
		return result;
	}

	public String testDBConfig(DBConfig dbConfig) {
		String result = "OK";
		try {
			Class.forName(dbConfig.getDriverName());
			Connection connection = DriverManager.getConnection(dbConfig.getUrl(),
					dbConfig.getUser(), dbConfig.getPassword());
			connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			result = e.getMessage();
		} catch (SQLException e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	}

}
