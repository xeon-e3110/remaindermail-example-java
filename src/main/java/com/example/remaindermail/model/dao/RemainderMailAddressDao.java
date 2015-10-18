package com.example.remaindermail.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;


public class RemainderMailAddressDao extends Dao {
	
	protected static final String tableName = "remainder_mail_address_t";
	
	public boolean isRegistedAddress(String address) throws SQLException, ClassNotFoundException
	{
		String sql = "SELECT mailAddress FROM " + tableName + " WHERE mailAddress = '" + address + "'";
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		boolean next = rs.next();
		rs.close();
		stmt.close();
		closeConnection();
		
		return next;
	}
	
	public boolean registAddress(String address) throws ClassNotFoundException, SQLException
	{
		String sql = "INSERT INTO " + tableName + " (mailAddress) VALUES ('" + address + "')";
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.execute();
		stmt.close();
		closeConnection();
		return true;
	}
	
	public ArrayList<String> getAddressList() throws ClassNotFoundException, SQLException
	{
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT mailAddress FROM " + tableName;
		Connection con = openConnection();
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next())
		{
			String address = rs.getString("mailAddress");
			list.add(address);
		}
		return list;
	}

}
