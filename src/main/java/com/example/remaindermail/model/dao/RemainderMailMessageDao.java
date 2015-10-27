package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.example.remaindermail.model.exception.DaoException;
import com.mysql.jdbc.Statement;

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
	 * @param message メッセージ
	 * @return メッセージ登録が成功したかどうか (true:成功 false:失敗)
	 * @throws Exception 発生した例外
	 */
	public RemainderMailMessageBean registMessage(RemainderMailMessageBean message) throws Exception {
		String sql = "INSERT INTO `" + tableName + "` ("
				+ "id, title, message, send, createDate"
				+ ") VALUES ("
				+ "null, ?, ?, ?, ?"
				+ ")";
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, message.getTitle());
			stmt.setString(2, message.getMessage());
			stmt.setInt(3, message.getSend());
			stmt.setString(4, message.getCreateDate());
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			if(rs.next()) {
				message.setId(rs.getInt(1));
			} else {
				throw new DaoException("リマインダーメールの登録に失敗しました");
			}
			return message;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} finally {
					if(stmt != null) {
						stmt.close();	
					}
				}
			} else if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * 最後に登録したメッセージを取得する
	 * @return 最後に登録されたメッセージ
	 * @throws Exception 発生した例外
	 */
	public RemainderMailMessageBean getLatestMessage() throws Exception {
		String sql = "SELECT `id`, `title`, `message`, `send`, `createDate`, `deleteFlg` FROM `" + tableName + "` WHERE `deleteFlg` = 0 ORDER BY `id` DESC LIMIT 1";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		RemainderMailMessageBean bean = new RemainderMailMessageBean();
		try {
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				bean.setMessage(rs.getString("message"));
				bean.setCreateDate(rs.getString("createDate"));
				bean.setSend(rs.getInt("send"));
			} else {
				throw new DaoException("データの取得に失敗しました");
			}
			return bean;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} finally {
					if(stmt != null) {
						stmt.close();	
					}
				}
			} else if(stmt != null) {
				stmt.close();
			}
		}
	}

}
