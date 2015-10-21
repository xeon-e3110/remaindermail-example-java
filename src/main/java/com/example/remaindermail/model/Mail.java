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
public class Mail 
{
	
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
			
			Session session = JNDI.lookup(jndiLookupName);
			
			MimeMessage msg = new MimeMessage(session);
			String from = JNDI.lookup(jndiLookupFrom);
			InternetAddress fromAddress = new InternetAddress(from);
			
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
