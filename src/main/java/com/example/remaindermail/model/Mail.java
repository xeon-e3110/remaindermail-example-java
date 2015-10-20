package com.example.remaindermail.model;

import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * メールクラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class Mail 
{
	
	/**
	 * SMTP送信先
	 */
	private String smtpHostName = "aradius.net";
	
	/**
	 * SMTPポート
	 */
	private int smtpPort = 587;
	
	/**
	 * SMTP認証ユーザー
	 */
	private String smtpUser = "sample2";
	
	/**
	 * SMTP認証パスワード
	 */
	private String smtpPassword = "sample2";
	
	/**
	 * SMTP設定
	 */
	private Properties smtpProperties;
	
	/**
	 * コンストラクタ
	 * プロパティの読み込みと設定を行う
	 */
	public Mail()
	{
		// TODO load config
		smtpProperties = new Properties();
		smtpProperties.put("mail.smtp.host", smtpHostName);
		smtpProperties.put("mail.smtp.auth", "true");
		smtpProperties.put("mail.smtp.port", smtpPort);
		smtpProperties.put("mail.transport.protocol", "smtp");
		smtpProperties.put("mail.smtp.debug", "true");
	}
	
	/**
	 * メール送信処理
	 * @param addressList 送信先アドレスリスト
	 * @param subject メールタイトル
	 * @param message メール本文
	 * @return boolean (true:送信完了 false:送信失敗)
	 */
	public boolean send(List<String> addressList, String subject, String message)
	{
		try
		{
			// 宛先がない場合は送信したことにする
			if(addressList.isEmpty())
			{
				return true;
			}
			
			//SMTPを使用してメールを送信
			Session session = Session.getInstance(smtpProperties, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(smtpUser, smtpPassword);
				}
			});
			
			MimeMessage msg = new MimeMessage(session);
			InternetAddress fromAddress = new InternetAddress(smtpUser + "@" + smtpHostName);
			
			Vector<InternetAddress> toAddressList = new Vector<InternetAddress>();
			for(String address:addressList)
			{
				toAddressList.add(new InternetAddress(address));
			}
			
			msg.setFrom(fromAddress);
			msg.setRecipients(Message.RecipientType.TO, toAddressList.toArray(new InternetAddress[0]));
			msg.setSubject(subject, "ISO-2022-JP");
			msg.setText(message, "ISO-2022-JP");
			Transport.send(msg);
			return true;
		}
		catch(Exception e)
		{
			Log.put(Level.SEVERE, e);
		}
		return false;
	}

}
