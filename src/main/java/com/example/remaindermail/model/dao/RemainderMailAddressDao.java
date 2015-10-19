package com.example.remaindermail.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.example.remaindermail.model.bean.RemainderMailAddressBean;
import com.mysql.jdbc.Connection;

/**
 * リマインダーメールアドレステーブルアクセス
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailAddressDao extends Dao 
{

	/**
	 * テーブル名
	 */
	protected static final String tableName = "remainder_mail_address_t";
	
	/**
	 * コンストラクタ
	 * @param connection
	 */
	public RemainderMailAddressDao(Connection connection) 
	{
		super(connection);
	}
	
	/**
	 * メールアドレスの登録チェック
	 * @param address
	 * @return boolean (true:登録済み false:未登録)
	 * @throws SQLException
	 */
	public boolean isRegistedAddress(String address) throws Exception
	{
		String sql = "SELECT `mailAddress` FROM `?` WHERE `mailAddress` = '?'";
		String[] params = {tableName, address};
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean exists = false;
		try
		{
			stmt = connection.prepareStatement(sql, params);
			rs = stmt.executeQuery();
			exists = rs.next();
			rs.close();
			stmt.close();
		}
		catch(SQLException e)
		{
			throw e;
		}
		finally 
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
		}
		
		return exists;
	}
	
	/**
	 * メールアドレス登録
	 * @param address
	 * @return boolean (true:登録成功 false:登録失敗)
	 * @throws SQLException
	 */
	public boolean registAddress(String address) throws Exception
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		String[] params = {tableName, address, dateStr, dateStr, "0"};
		String sql = "INSERT INTO `?` ("
				+ "`mailAddress`, `updateTime`, `createTime`, `deleteFlg`"
				+ ") VALUES ("
				+ "'?', '?', '?', ?"
				+ ")";
		PreparedStatement stmt = null;
		boolean success = false;
		try
		{
			stmt = connection.prepareStatement(sql, params);
			stmt.execute();
			success = true;
		}
		catch(SQLException e)
		{
			// TODO ロガーに出力する
		}
		finally
		{
			if(stmt != null)
			{
				stmt.close();
			}
		}
		return success;
	}
	
	/**
	 * 登録されているリマインダーメールリストを取得する
	 * @return ArrayList<RemainderMailAddressBean> 登録されているメールアドレスのリスト
	 * @throws Exception
	 */
	public ArrayList<RemainderMailAddressBean> getAddressList() throws Exception
	{
		ArrayList<RemainderMailAddressBean> list = new ArrayList<RemainderMailAddressBean>();
		String[] params = {tableName};
		String sql = "SELECT "
				+ "`id`,"
				+ "`mailAddress`,"
				+ "`updateTime`,"
				+ "`createTime`,"
				+ "`deleteFlg`"
				+ " FROM `?` WHERE `delete` = 0";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = connection.prepareStatement(sql, params);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				RemainderMailAddressBean bean = new RemainderMailAddressBean();
				bean.setMailAddress(rs.getString("mailAddress"));
				bean.setUpdateDate(rs.getString("updateDate"));
				bean.setCreateDate(rs.getString("createDate"));
				bean.setDelete(rs.getInt("deleteFlg"));
				list.add(bean);
			}
		}
		catch(SQLException e)
		{
			throw e;
		}
		finally
		{
			if(rs != null)
			{
				rs.close();
			}
			if(stmt != null)
			{
				stmt.close();
			}
		}
		return list;
	}

}
