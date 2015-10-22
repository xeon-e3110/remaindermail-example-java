package com.example.remaindermail.model.dao;

import java.sql.Connection;

/**
 * MysqlDAO
 * @author toshikiarai
 *
 */
public class Dao {

	/**
	 * Mysqlコネクション
	 */
	protected Connection connection;
	
	/**
	 * コンストラクタ
	 * @param connection コネクション
	 */
	public Dao(Connection connection) {
		this.connection = connection;
	}
}
