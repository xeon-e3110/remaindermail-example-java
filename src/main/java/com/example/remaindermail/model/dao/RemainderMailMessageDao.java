package com.example.remaindermail.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import com.example.remaindermail.model.Log;
import com.example.remaindermail.model.bean.RemainderMailMessageBean;

/**
 *マインダーメールメッセージDao
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailMessageDao extends Dao 
{
	
	/**
	 * テーブル名
	 */
	protected static final String tableName = "remainder_mail_message_t";

	/**
	 * コンストラクタ
	 * @param connection
	 */
	public RemainderMailMessageDao(Connection connection) 
	{
		super(connection);
	}
	
	/**
	 * メッセージを登録
	 * @param message
	 * @return メッセージ登録が成功したかどうか (true:成功 false:失敗)
	 */
	public boolean registMessage(RemainderMailMessageBean message)
	{
		String sql = "INSERT INTO `" + tableName + "` ("
				+ "id, title, message, send, createDate"
				+ ") VALUES ("
				+ "null, ?, ?, ?, ?"
				+ ")";
		
		PreparedStatement stmt = null;
		boolean success = false;
		try
		{
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, message.getTitle());
			stmt.setString(2, message.getMessage());
			stmt.setInt(3, message.getSend());
			stmt.setString(4, message.getCreateDate());
			stmt.execute();
			success = false;
		}
		catch(SQLException e)
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
	 * 登録メッセージ一覧を取得得する
	 * @return メッセージリスト
	 */
	public ArrayList<RemainderMailMessageBean> getMessageList()
	{
		ArrayList<RemainderMailMessageBean> list = new ArrayList<RemainderMailMessageBean>();
		String sql = "SELECT mailAddress FROM `" + tableName + "`";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				RemainderMailMessageBean bean = new RemainderMailMessageBean();
				bean.setId(rs.getInt("id"));
				bean.setTitle(rs.getString("title"));
				bean.setMessage(rs.getString("message"));
				bean.setSend(rs.getInt("send"));
				bean.setCreateDate(rs.getString("createDate"));
				list.add(bean);
			}
		}
		catch(SQLException e)
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
		return list;
	}

}
