package com.example.remaindermail.model.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;


public class MysqlConnection {
	

	protected String dataSource = "jdbc:mysql://localhost:8889/remaindermail";
	protected String dataSourcePassword = "root";
	protected String dataSourceUser = "root";
	
	private Connection connection;
	
	public Connection openConnection() throws ClassNotFoundException, SQLException
	{
		if(connection == null)
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = (Connection)DriverManager.getConnection(dataSource, dataSourceUser, dataSourcePassword);
			connection.setAutoCommit(false);
		}
		return connection;
	}
	
	public void closeConnection() throws SQLException
	{
		if(connection != null)
		{
			if(connection.isClosed() == false)
			{
				connection.close();
			}
			connection = null;
		}
	}

}
