package org.fartpig.dbunit_extractor_web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DBAllConfig {

	@XmlElement(name = "config")
	private DBConfig[] configs;

	public DBConfig[] getConfigs() {
		return configs;
	}

	public void setConfigs(DBConfig[] configs) {
		this.configs = configs;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
