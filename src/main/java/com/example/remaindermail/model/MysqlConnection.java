package com.example.remaindermail.model;

import java.sql.Connection;

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
	protected String jndiLookupName = "jndi/mysql/remaindermail";
	
	/**
	 * MySQLコネクション
	 */
	private Connection connection;
	
	
	/**
	 * コネクションを取得または生成する
	 * @return コネクション
	 * @throws Exception 発生した例外
	 */
	public Connection openConnection() throws Exception {
		if(connection == null) {
			DataSource dataSource = JNDIWrapper.lookup(jndiLookupName);
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		}
		return connection;
	}
	
	/**
	 * コネクションを閉じる
	 * @throws Exception 発生した例外
	 */
	public void closeConnection() throws Exception {
		if(connection != null) {
			if(connection.isClosed() == false) {
				connection.close();
			}
			connection = null;
		}
	}

}
