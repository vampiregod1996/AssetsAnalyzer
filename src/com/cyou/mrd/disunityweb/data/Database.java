package com.cyou.mrd.disunityweb.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.cyou.mrd.disunityweb.conf.Configs;

public class Database {
	private Connection connection = null;
	private static Database instance = null;
	public static void Init() {
		if (instance == null) {
			instance = new Database();
		}
	}

	public static Database Instane() {
		return instance;
	}

	public static void Destroy() {
		instance = null;
	}

	private Database() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:"
					+ Configs.contextPath + "\\data\\data.db", null, null);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void commit()
	{
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	protected void finalize() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
	}

	public DatabaseOperator begin(String sql) {
		DatabaseOperator statement = null;
		try {
			statement = new DatabaseOperator(connection.prepareStatement(sql));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return statement;
	}
	
	public Statement begin()
	{
		try {
			return connection.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return null;
	}
}
