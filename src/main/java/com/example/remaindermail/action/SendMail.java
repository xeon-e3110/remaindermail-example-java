package com.example.remaindermail.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import com.example.remaindermail.model.Log;
import com.example.remaindermail.model.Mail;
import com.example.remaindermail.model.bean.RemainderMailAddressBean;
import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.example.remaindermail.model.bean.SendMailBean;
import com.example.remaindermail.model.dao.MysqlConnection;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.example.remaindermail.model.dao.RemainderMailMessageDao;
import com.mysql.jdbc.Connection;
import com.opensymphony.xwork2.ActionSupport;

/**
 * リマインダーメール登録コントローラ
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMail extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * リマインダーメールメッセージ
	 */
	private SendMailBean sendMail;
	
	/**
	 * ビューデータ取得
	 * @return sendMail
	 */
	public SendMailBean getSendMail() 
	{
		return sendMail;
	}

	/**
	 * ビューデータ設定
	 * @param sendMail
	 */
	public void setSendMail(SendMailBean sendMail) 
	{
		this.sendMail = sendMail;
	}

	/**
	 * メール送信
	 * @return String
	 * @throws Exception
	 */
	public String send() throws Exception
	{
		//初回アクセス時
		if(sendMail == null)
		{
			return SUCCESS;
		}
		
		//入力チェック
		if(sendMail.getTitle().isEmpty())
		{
			sendMail.getErrorList().add("タイトルを指定してください");
		}
		if(sendMail.getMessage().isEmpty())
		{
			sendMail.getErrorList().add("本文を指定してください");
		}
		if(sendMail.getErrorList().isEmpty() == false)
		{
			return ERROR;
		}
		
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try
		{
			
			// Mysqlコネクションを開く
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			//送信先をまとめる
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			ArrayList<RemainderMailAddressBean> addressList = addressDao.getAddressList();
			ArrayList<String> inetAddressList = new ArrayList<String>();
			for(RemainderMailAddressBean bean:addressList)
			{
				inetAddressList.add(bean.getMailAddress());
			}
			
			if(inetAddressList.isEmpty())
			{
				sendMail.getErrorList().add("宛先が一件もありません");
				return ERROR;
			}
			
			// メール送信
			Mail mail = new Mail();
			boolean send = mail.send(inetAddressList, sendMail.getTitle(), sendMail.getMessage());
			
			if(send)
			{
				//履歴に登録
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				RemainderMailMessageBean bean = new RemainderMailMessageBean();
				bean.setTitle(sendMail.getTitle());
				bean.setMessage(sendMail.getMessage());
				bean.setCreateDate(sdf.format(date));
				bean.setSend(1);
				
				RemainderMailMessageDao messageDao = new RemainderMailMessageDao(connection);
				messageDao.registMessage(bean);
				
				// 送信済み状態にする
				sendMail.setSend(true);
				
				// 入力をクリア
				sendMail.setTitle("");
				sendMail.setMessage("");

				// DBコミット
				connection.commit();
			}
			else
			{
				sendMail.getErrorList().add("メールは送信されませんでした");
				return ERROR;
			}
			
		}
		catch(Exception e)
		{
			Log.put(Level.SEVERE, e);
			if(connection != null)
			{
				connection.rollback();	
			}
		}
		finally
		{
			if(mysqlConnection != null)
			{
				mysqlConnection.closeConnection();
			}
		}
		
		return SUCCESS;
	}

}
