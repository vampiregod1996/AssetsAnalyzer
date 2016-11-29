package com.cyou.mrd.disunityweb.data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseOperator {
	private PreparedStatement prep = null;
	public DatabaseOperator(PreparedStatement prep)
	{
		this.prep = prep;
	}
	
	public void Close()
	{
		try {
			prep.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void Batch()
	{
		try {
			prep.executeBatch();
			Database.Instane().commit();
			prep.clearBatch();
			System.out.print("Write Bath to Databse\n");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void setString(int parameterIndex, String x)
	{
		try {
			prep.setString(parameterIndex, x);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void setBytes(int parameterIndex, byte[] x) {
		try {
			prep.setBytes(parameterIndex, x);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void setLong(int parameterIndex, long x)
	{
		try {
			prep.setLong(parameterIndex, x);
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
	
	public void addBatch()
	{
		try {
			prep.addBatch();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
