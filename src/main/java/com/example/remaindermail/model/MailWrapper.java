package com.example.remaindermail.model;

import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * メールクラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class MailWrapper {
	
	/**
	 * 検索用文字列
	 */
	private static final String jndiLookupName = "jndi/mail/session";
	
	/**
	 * メール送信元アドレス
	 */
	private static final String jndiLookupFrom = "jndi/mail/from";
	
	/**
	 * メール送信処理
	 * @param address 送信先アドレス
	 * @param subject メールタイトル
	 * @param message メール本文
	 * @throws Exception 発生した例外
	 */
	public static void send(String address, String subject, String message) throws Exception {
		
		Session session = JNDIWrapper.lookup(jndiLookupName);
		MimeMessage msg = new MimeMessage(session);
		String from = JNDIWrapper.lookup(jndiLookupFrom);
		InternetAddress fromAddress = new InternetAddress(from);
		InternetAddress toAddress = new InternetAddress(address);
		
		msg.setFrom(fromAddress);
		msg.setRecipient(Message.RecipientType.TO, toAddress);
		msg.setSubject(subject, "ISO-2022-JP");
		msg.setText(message, "ISO-2022-JP");
		Transport.send(msg);
	}

}
