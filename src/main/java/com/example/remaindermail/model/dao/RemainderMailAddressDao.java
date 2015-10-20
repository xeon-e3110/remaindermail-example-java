package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import com.example.remaindermail.model.Log;
import com.example.remaindermail.model.bean.RemainderMailAddressBean;

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
	 * @return メールアドレスが登録されているかどうか (true:登録済み false:未登録)
	 */
	public boolean isRegistedAddress(String address)
	{
		String sql = "SELECT `mailAddress` FROM `" + tableName + "` WHERE `mailAddress` = ? AND `deleteFlg` = 0";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean exists = false;
		try
		{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, address);
			rs = stmt.executeQuery();
			exists = rs.next();
			rs.close();
			stmt.close();
		}
		catch(Exception e)
		{
			Log.put(Level.SEVERE, e);
		}
		finally 
		{
			if(rs != null)
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e) 
				{
					Log.put(Level.SEVERE, e);
				}
			}
			if(stmt != null)
			{
				try 
				{
					stmt.close();
				} 
				catch (SQLException e) 
				{
					Log.put(Level.SEVERE, e);
				}
			}
		}
		
		return exists;
	}
	
	/**
	 * メールアドレス登録
	 * @param address
	 * @return 登録が成功したかどうか (true:登録成功 false:登録失敗)
	 */
	public boolean registAddress(String address)
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String dateStr = sdf.format(date);
		String sql = "INSERT INTO `" + tableName + "` ("
				+ "`mailAddress`, `updateDate`, `createDate`, `deleteFlg`"
				+ ") VALUES ("
				+ "?, ?, ?, ?"
				+ ")";
		PreparedStatement stmt = null;
		boolean success = false;
		try
		{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, address);
			stmt.setString(2, dateStr);
			stmt.setString(3, dateStr);
			stmt.setString(4, "0");
			stmt.execute();
			success = true;
		}
		catch(Exception e)
		{
			Log.put(Level.SEVERE, e);
		}
		finally
		{
			if(stmt != null)
			{
				try 
				{
					stmt.close();
				} 
				catch (SQLException e) 
				{
					Log.put(Level.SEVERE, e);
				}
			}
		}
		return success;
	}
	
	/**
	 * 登録されているリマインダーメールリストを取得する
	 * @return 登録されているメールアドレスのリスト
	 * @throws Exception
	 */
	public ArrayList<RemainderMailAddressBean> getAddressList() throws Exception
	{
		ArrayList<RemainderMailAddressBean> list = new ArrayList<RemainderMailAddressBean>();
		String sql = "SELECT "
				+ "`id`, `mailAddress`, `updateDate`, `createDate`, `deleteFlg`"
				+ " FROM " + tableName + " WHERE `deleteFlg` = 0";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			stmt = connection.prepareStatement(sql);
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
