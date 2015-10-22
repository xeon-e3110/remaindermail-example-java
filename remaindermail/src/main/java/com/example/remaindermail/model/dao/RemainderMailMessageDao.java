package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.example.remaindermail.model.LogWrapper;
import com.example.remaindermail.model.bean.RemainderMailMessageBean;

/**
 *マインダーメールメッセージDao
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailMessageDao extends Dao {
	
	/**
	 * テーブル名
	 */
	protected static final String tableName = "remainder_mail_message_t";

	/**
	 * コンストラクタ
	 * @param connection コネクション
	 */
	public RemainderMailMessageDao(Connection connection) {
		super(connection);
	}
	
	/**
	 * メッセージを登録
	 * @param message
	 * @return メッセージ登録が成功したかどうか (true:成功 false:失敗)
	 * @throws Exception
	 */
	public boolean registMessage(RemainderMailMessageBean message) throws Exception {
		String sql = "INSERT INTO `" + tableName + "` ("
				+ "id, title, message, send, createDate"
				+ ") VALUES ("
				+ "null, ?, ?, ?, ?"
				+ ")";
		
		PreparedStatement stmt = null;
		boolean success = false;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, message.getTitle());
			stmt.setString(2, message.getMessage());
			stmt.setInt(3, message.getSend());
			stmt.setString(4, message.getCreateDate());
			stmt.execute();
			success = false;
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
		return success;
	}

}
