package com.hid.pdf.persistence.dao;

import com.hid.pdf.domain.Config;

@DatabaseTable("Config")
@EntityClass(Config.class)
public class ConfigDAO extends DAO<Config> {
	private static ConfigDAO dao;

	public static ConfigDAO getInstance() {
		if (dao == null) {
			dao = new ConfigDAO();
		}

		return dao;
	}

	public String value(String key) {
		Config config = find(String.format("select * from Config where `key` = '%s'", key));

		if (config == null) {
			return "";
		} else {
			return config.getValue();
		}
	}

	public Config get(String key) {
		return find(String.format("select * from Config where `key` = '%s'", key));
	}

	public String valueBlob(String key) {
		Config config = find(String.format("select * from Config where `key` = '%s'", key));

		if (config == null) {
			return "";
		} else {
			return config.getValueBlob();
		}
	}

}
