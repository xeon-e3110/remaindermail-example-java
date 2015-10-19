package com.example.remaindermail.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	
	protected static final String smtpHostName = "";
	protected static final int smtpPort = 587;
	protected static final String smtpUser = "";
	protected static final String smtpPassword = "";
	
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
		
		try
		{
			
			// Mysqlコネクションを開く
			mysqlConnection = new MysqlConnection();
			Connection connection = mysqlConnection.openConnection();
			
			//送信先をまとめる
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			ArrayList<RemainderMailAddressBean> addressList = addressDao.getAddressList();
			ArrayList<InternetAddress> inetAddressList = new ArrayList<InternetAddress>();
			for(RemainderMailAddressBean bean:addressList)
			{
				inetAddressList.add(new InternetAddress(bean.getMailAddress()));
			}
			
			if(inetAddressList.isEmpty())
			{
				sendMail.getErrorList().add("宛先が一件もありません");
				return ERROR;
			}
			
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
			
		
			//SMTPを使用してメールを送信
			Properties prop = new Properties();
			prop.put("mail.smtp.host", smtpHostName);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.port", smtpPort);
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.debug", "true");
			
			Session session = Session.getInstance(prop, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(smtpUser, smtpPassword);
				}
			});
			
			MimeMessage msg = new MimeMessage(session);
			InternetAddress fromAddress = new InternetAddress(smtpUser + "@" + smtpPassword);
			
			msg.setFrom(fromAddress);
			msg.setRecipients(Message.RecipientType.TO,
					inetAddressList.toArray(new InternetAddress[0])
					);
			msg.setSubject(sendMail.getTitle(), "ISO-2022-JP");
			msg.setText(sendMail.getMessage(), "ISO-2022-JP");
			Transport.send(msg);
			sendMail.setSend(true);
			
			//入力をクリア
			sendMail.setTitle("");
			sendMail.setMessage("");
			
			connection.commit();
		}
		catch(Exception e)
		{
			// TODO Log
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
