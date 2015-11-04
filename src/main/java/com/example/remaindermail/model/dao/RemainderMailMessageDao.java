package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
	 * @return 登録したメッセージ情報
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
				return null;
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
	
	/**
	 * 登録されているメッセージリストを取得する
	 * @return メッセージリスト
	 * @throws Exception 発生した例外
	 */
	public List<RemainderMailMessageBean> getNotSendMessageList() throws Exception {
		String sql = "SELECT `id`, `title`, `message`, `send`, `createDate`, `deleteFlg` FROM `" + tableName + "` WHERE `deleteFlg` = 0 AND `send` = 0 ORDER BY `id`";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<RemainderMailMessageBean> beanList = new ArrayList<RemainderMailMessageBean>();
		try {
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			// リストのサイズをあらかじめ拡張しておく
			beanList.ensureCapacity(rs.getFetchSize());
			while(rs.next()) {
				RemainderMailMessageBean bean = new RemainderMailMessageBean();
				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				bean.setMessage(rs.getString("message"));
				bean.setSend(rs.getInt("send"));
				bean.setCreateDate(rs.getString("createDate"));
				beanList.add(bean);
			}
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
		return beanList;
	}
	
	/**
	 * メールを送信済み状態にする
	 * @param messageID 送信済み状態にするメッセージID
	 * @throws Exception 発生した例外
	 */
	public void setSend(int messageID) throws Exception {
		String sql = "UPDATE `" + tableName + "` SET `send` = 1 WHERE `deleteFlg` = 0 AND `id` = ?";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, messageID);
			stmt.executeUpdate();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}

}
