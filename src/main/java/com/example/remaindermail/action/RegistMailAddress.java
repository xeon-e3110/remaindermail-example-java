package com.example.remaindermail.action;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.remaindermail.model.Log;
import com.example.remaindermail.model.MysqlConnection;
import com.example.remaindermail.model.bean.RegistMailAddressBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.opensymphony.xwork2.ActionSupport;

/**
 * リマインダーメールアドレス登録コントローラ
 * @author toshikiarai
 * @version 1.0.0
 */
public class RegistMailAddress extends ActionSupport {
	
	/**
	 * ビューデータ
	 */
	private RegistMailAddressBean registMailAddress;

	/**
	 * ビューデータ取得
	 * @return registMailAddress
	 */
	public RegistMailAddressBean getRegistMailAddress() 
	{
		return registMailAddress;
	}

	/**
	 * ビューデータ設定
	 * @param registMailAddress
	 */
	public void setRegistMailAddress(RegistMailAddressBean registMailAddress) 
	{
		this.registMailAddress = registMailAddress;
	}
	
	/**
	 * リマインダーメールアドレス登録
	 * @return String
	 * @throws Exception
	 */
	public String regist() throws Exception
	{
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try
		{
			// 初回アクセス時はそのまま表示する
			if(registMailAddress == null)
			{
				return SUCCESS;
			}
			
			// メールアドレス判別
			String regex = "^[a-zA-Z0-9_]+"
					+ "[a-zA-Z0-9.-_]*"
					+ "@"
					+ "[a-zA-Z0-9_-]+"
					+ "[a-zA-Z0-9.-]+[a-zA-Z0-9-]$"
					+ "";
			Pattern mailAddressPattern = Pattern.compile(regex);
			Matcher matcher = mailAddressPattern.matcher(registMailAddress.getMailAddress());
			
			// 無効なメールアドレス
			if(matcher.matches() == false)
			{
				registMailAddress.getErrorList().add("無効なメールアドレスです");
				registMailAddress.setIsRegist(false);
				return ERROR;
			}
			
			// 入力確認
			if(registMailAddress.getMailAddress().equals(registMailAddress.getConfirmMailAddress()) == false)
			{
				registMailAddress.getErrorList().add("確認用メールアドレスが違います");
				registMailAddress.setIsRegist(false);
				return ERROR;
			}
			
			// DBコネクション
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			// すでに登録されている場合
			RemainderMailAddressDao dao = new RemainderMailAddressDao(connection);
			if(dao.isRegistedAddress(registMailAddress.getMailAddress()))
			{
				registMailAddress.getErrorList().add("すでに登録されています");
				registMailAddress.setIsRegist(false);
				return ERROR;
			}
			
			// メールアドレス登録
			if(dao.registAddress(registMailAddress.getMailAddress()) == false)
			{
				registMailAddress.getErrorList().add("登録に失敗しました");
				registMailAddress.setIsRegist(false);
				return ERROR;
			}

			registMailAddress.setIsRegist(true);
			
			// DBコミット
			connection.commit();
			
		}
		catch(Exception e)
		{
			Log.put(Level.SEVERE, e);
			registMailAddress.getErrorList().add("エラーが発生しました");
			if(connection != null)
			{
				// DBロールバック
				connection.rollback();
			}
		}
		finally
		{
			if(mysqlConnection != null)
			{
				// コネクションをクローズ
				mysqlConnection.closeConnection();
			}
		}
		return SUCCESS;
	}
}
