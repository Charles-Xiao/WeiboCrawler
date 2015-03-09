package com.xy.weibocrawler.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public enum JDBC {

	/** 枚举实现的JDBC单实例 */
	INSTANCE;

	/** mySQL数据库连接 */
	private Connection mConnection = null;

	/** mySQL数据库SQL语句 */
	private PreparedStatement mPstmt;

	/** mySQL数据库查询结果 */
	private ResultSet mResultSet;

	/** 数据库用户名 */
	private static final String USERNAME = "root";

	/** 数据库用户名 */
	private static final String PASSWORD = "";

	/** 数据库Driver */
	private static final String DRIVER = "com.mysql.jdbc.Driver";

	/** 数据库地址url */
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test";

	private JDBC() {

	}

	public Connection getDbConnection() {
		try {
			Class.forName(DRIVER);
			mConnection = DriverManager.getConnection(DB_URL, USERNAME,
					PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("数据库驱动加载出错！");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("数据库连接参数出错！");
			e.printStackTrace();
		}
		System.out.println("数据库连接成功！");
		return mConnection;
	}

	public boolean dbUpdateBySQL(String sql, List<Object> params)
			throws SQLException {
		mPstmt = mConnection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				mPstmt.setObject(index++, params.get(i));
			}
		}
		return (mPstmt.executeUpdate() > 0) ? true : false;
	}

	/**
	 * 利用SQL语句查询单条数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> dbSelectSimpleData(String sql,
			List<Object> params) throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		mPstmt = mConnection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				mPstmt.setObject(index++, params.get(i));
			}
		}
		mResultSet = mPstmt.executeQuery();
		ResultSetMetaData metaData = mResultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (mResultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = mResultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		return map;
	}

	/**
	 * 利用SQL语句查询多条数据
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> dbSelectMultiData(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		mPstmt = mConnection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				mPstmt.setObject(index++, params.get(i));
			}
		}
		mResultSet = mPstmt.executeQuery();
		ResultSetMetaData metaData = mResultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (mResultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = mResultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}
		return list;
	}

	/**
	 * 释放数据库连接资源
	 * 
	 * @return
	 */
	public boolean dbClose() {
		try {
			if (mConnection != null) {
				mConnection.close();
			}
			if (mResultSet != null) {
				mResultSet.close();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
