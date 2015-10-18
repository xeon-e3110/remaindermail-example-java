package com.example.remaindermail.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.example.remaindermail.model.dao.RemainderMailMessageDao;
import com.opensymphony.xwork2.ActionSupport;

public class SendMail extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected static final String smtpHostName = "";
	//protected static final int smtpsPort = 465;
	protected static final int smtpPort = 587;
	protected static final String smtpUser = "";
	protected static final String smtpPassword = "";
	
	protected String title;
	protected String message;
	protected boolean isSend = false;
	protected ArrayList<String> errorList = new ArrayList<String>();
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String txt)
	{
		title = txt;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String txt)
	{
		message = txt;
	}
	
	public ArrayList<String> getErrorList()
	{
		return errorList;
	}
	
	public boolean getIsSend()
	{
		return isSend;
	}

	public String send() throws ClassNotFoundException, SQLException, MessagingException, UnsupportedEncodingException
	{
		//初回アクセス時
		if(title == null || message == null)
		{
			return SUCCESS;
		}
		
		//入力チェック
		if(title.isEmpty())
		{
			errorList.add("タイトルを指定してください");
		}
		if(message.isEmpty())
		{
			errorList.add("本文を指定してください");
		}
		if(errorList.isEmpty() == false)
		{
			return ERROR;
		}
		
		//送信先をまとめる
		RemainderMailAddressDao addressDao = new RemainderMailAddressDao();
		ArrayList<String> addressList = addressDao.getAddressList();
		ArrayList<InternetAddress> inetAddressList = new ArrayList<InternetAddress>();
		for(String address:addressList)
		{
			inetAddressList.add(new InternetAddress(address));
		}
		
		if(inetAddressList.isEmpty())
		{
			errorList.add("宛先が一件もありません");
			return ERROR;
		}
		
		//履歴に登録
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		RemainderMailMessageBean bean = new RemainderMailMessageBean();
		bean.setTitle(title);
		bean.setMessage(message);
		bean.setCreateDate(sdf.format(date));
		bean.setSend(1);
		
		RemainderMailMessageDao messageDao = new RemainderMailMessageDao();
		messageDao.registMessage(bean);
		
	
		//SMTPを使用してメールを送信
		Properties prop = new Properties();
		prop.put("mail.smtp.host", smtpHostName);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", smtpPort);
//		prop.put("mail.smtp.socketFactory.port", smtpsPort);
//		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
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
		msg.setSubject(title, "ISO-2022-JP");
		msg.setText(message, "ISO-2022-JP");
		Transport.send(msg);
		isSend = true;
		
		//入力をクリア
		title = "";
		message = "";
		
		return SUCCESS;
	}
}
