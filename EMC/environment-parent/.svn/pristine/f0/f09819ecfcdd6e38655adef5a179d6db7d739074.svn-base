package com.briup.util;

import java.io.FileInputStream;

/**
 * 数据库连接池工具类
 * 
 * @author ghost
 *
 */

import java.sql.Connection;
import java.util.Properties;

import com.alibaba.druid.pool.DruidDataSource;

public class ConnectionUtil {
	public static DruidDataSource dataSource = null;
	
	static {
		/*
		 * 把数据库连接信息从配置文件中读取出来进行连接
		 */
		try {
			// 读取properties
			Properties properties = new Properties();
			properties.load(new FileInputStream("src/main/resources/jdbc.properties"));

			dataSource = new DruidDataSource();
			dataSource.setDriverClassName(properties.getProperty("jdbc.driverClassName"));
			dataSource.setUrl(properties.getProperty("jdbc.url"));
			dataSource.setUsername(properties.getProperty("jdbc.userName"));
			dataSource.setPassword(properties.getProperty("jdbc.password"));

			dataSource.setInitialSize(Integer.parseInt(properties.getProperty("jdbc.initialSize")));

			dataSource.setMaxActive(Integer.parseInt(properties.getProperty("jdbc.maxActive")));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Connection getConnections() throws Exception {
		return getConnections(false);
	}

	public Connection getConnections(boolean autoCommit) throws Exception {
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(autoCommit);
		return connection;
	}
}
