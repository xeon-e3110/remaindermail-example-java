package com.example.remaindermail.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.mysql.jdbc.Connection;

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
	 * @param connection
	 */
	public RemainderMailMessageDao(Connection connection) 
	{
		super(connection);
	}
	
	/**
	 * メッセージを登録
	 * @param message
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean registMessage(RemainderMailMessageBean message) throws ClassNotFoundException, SQLException
	{
		String[] params = {tableName, message.getTitle(), message.getMessage(), "1", message.getCreateDate()};
		String sql = "INSERT INTO `?` ("
				+ "id, title, message, send, createTime"
				+ ") VALUES ("
				+ "null, '?', '?', ?, '?'"
				+ ")";
		
		PreparedStatement stmt = null;
		boolean success = false;
		try
		{
			stmt = connection.prepareStatement(sql, params);
			stmt.execute();
			success = false;
		}
		catch(SQLException e)
		{
			// TODO Log
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
	 * 登録メッセージ一覧得する
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<RemainderMailMessageBean> getMessageList() throws ClassNotFoundException, SQLException
	{
		ArrayList<RemainderMailMessageBean> list = new ArrayList<RemainderMailMessageBean>();
		String[] params = {tableName};
		String sql = "SELECT mailAddress FROM `?`";
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			stmt = connection.prepareStatement(sql, params);
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
			// TODO Log
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
