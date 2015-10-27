package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import com.example.remaindermail.model.LogWrapper;
import com.example.remaindermail.model.bean.RemainderMailAddressBean;

/**
 * リマインダーメールアドレステーブルアクセス
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailAddressDao extends Dao {

	/**
	 * テーブル名
	 */
	protected static final String tableName = "remainder_mail_address_t";
	
	/**
	 * コンストラクタ
	 * @param connection コネクション
	 */
	public RemainderMailAddressDao(Connection connection) {
		super(connection);
	}
	
	/**
	 * メールアドレスの登録チェック
	 * @param address 検索するメールアドレス
	 * @return boolean メールアドレスが登録されているかどうか (true:登録済み false:未登録)
	 */
	public boolean isRegistedAddress(String address) throws Exception {
		String sql = "SELECT `mailAddress` FROM `" + tableName + "` WHERE `mailAddress` = ? AND `deleteFlg` = 0";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean exists = false;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, address);
			rs = stmt.executeQuery();
			exists = rs.next();
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
		
		return exists;
	}
	
	/**
	 * メールアドレス登録
	 * @param address
	 * @return 登録が成功したかどうか (true:登録成功 false:登録失敗)
	 */
	public void registAddress(String address) throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		String sql = "INSERT INTO `" + tableName + "` ("
				+ "`mailAddress`, `updateDate`, `createDate`, `deleteFlg`"
				+ ") VALUES ("
				+ "?, ?, ?, ?"
				+ ")";
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, address);
			stmt.setString(2, dateStr);
			stmt.setString(3, dateStr);
			stmt.setString(4, "0");
			stmt.execute();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * メッセージIDを更新する
	 * @param address 対象メールアドレス
	 * @param messageID 更新するメッセージID
	 * @throws Exception
	 */
	public void updateMessageID(String address, int messageID) throws Exception {
		String sql = "UPDATE `" + tableName + "` SET `messageID` = ?, `updateDate` = ? WHERE `mailAddress` = ?";
		PreparedStatement stmt = null;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, messageID);
			stmt.setString(2, dateStr);
			stmt.setString(3, address);
			stmt.execute();
		} finally {
			if(stmt != null) {
				stmt.close();
			}
		}
	}
	
	/**
	 * 登録されているリマインダーメールリストを取得する
	 * @return 登録されているメールアドレスのリスト
	 * @throws Exception
	 */
	public ArrayList<RemainderMailAddressBean> getAddressList(int messageID) throws Exception {
		ArrayList<RemainderMailAddressBean> list = new ArrayList<RemainderMailAddressBean>();
		String sql = "SELECT "
				+ "`id`, `mailAddress`, `messageID`, `updateDate`, `createDate`, `deleteFlg`"
				+ " FROM " + tableName + " WHERE `messageID` < ? AND `deleteFlg` = 0";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, messageID);
			rs = stmt.executeQuery();
			while(rs.next()) {
				RemainderMailAddressBean bean = new RemainderMailAddressBean();
				bean.setMailAddress(rs.getString("mailAddress"));
				bean.setMessageID(rs.getInt("messageID"));
				bean.setUpdateDate(rs.getString("updateDate"));
				bean.setCreateDate(rs.getString("createDate"));
				bean.setDelete(rs.getInt("deleteFlg"));
				list.add(bean);
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
		
		return list;
	}

}
