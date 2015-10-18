package com.example.remaindermail.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.mysql.jdbc.Connection;


public class RemainderMailMessageDao extends Dao {
	
	protected static final String tableName = "remainder_mail_message_t";
	
	public boolean registMessage(RemainderMailMessageBean message) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO " + tableName + " (id, title, message, send, createTime) VALUES ("
				+ "null, "
				+ "'" + message.getTitle() + "', "
				+ "'" + message.getMessage() + "', "
				+ message.getSend() + ", "
				+ "'" + message.getCreateDate() + ""
				+ "')";
		
		
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		stmt.close();
		closeConnection();
		return true;
	}
	
	public ArrayList<RemainderMailMessageBean> getMessageList() throws ClassNotFoundException, SQLException
	{
		ArrayList<RemainderMailMessageBean> list = new ArrayList<RemainderMailMessageBean>();
		String sql = "SELECT mailAddress FROM " + tableName;
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
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
		return list;
	}

}
