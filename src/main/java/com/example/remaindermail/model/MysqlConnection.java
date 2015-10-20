package com.example.remaindermail.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * MySQLコネクション管理クラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class MysqlConnection {
	
	/**
	 * JDNI検索文字列
	 */
	protected String jdniLookupName = "java:comp/env/jdni/mysql/remaindermail";
	
	/**
	 * MySQLコネクション
	 */
	private Connection connection;
	
	/**
	 * コネクションを取得または生成する
	 * @return コネクション
	 * @throws SQLException
	 */
	public Connection openConnection() throws SQLException
	{
		if(connection == null)
		{
			InitialContext iniCtx = null;
			DataSource ds = null;
			try {
				iniCtx = new InitialContext();
				ds = (DataSource) iniCtx.lookup(jdniLookupName);
				connection = ds.getConnection();
				connection.setAutoCommit(false);
			} catch (NamingException e) {
				Log.put(Level.SEVERE, e);
			}
		}
		return connection;
	}
	
	/**
	 * コネクションを閉じる
	 * @throws SQLException
	 */
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