package com.example.remaindermail.task;

import java.sql.Connection;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;

import com.example.remaindermail.model.LogWrapper;
import com.example.remaindermail.model.MailWrapper;
import com.example.remaindermail.model.MysqlConnection;
import com.example.remaindermail.model.bean.RemainderMailAddressBean;
import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.example.remaindermail.model.dao.RemainderMailMessageDao;

/**
 * メール送信タスク（バッチから定期的に呼ばれる）
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMailTask extends TimerTask {

	/**
	 * メール送信処理
	 */
	public void run() {
		
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try {
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			RemainderMailMessageDao messageDao = new RemainderMailMessageDao(connection);
			List<RemainderMailMessageBean> messageList = messageDao.getNotSendMessageList();
			// 全て送信済みなら最新のメッセージをピックアップする
			if(messageList.isEmpty()) {
				RemainderMailMessageBean latestMessageBean = messageDao.getLatestMessage();
				
				// 取得できなかった場合は終了
				if(latestMessageBean == null) {
					return;
				}
				messageList.add(latestMessageBean);
			}
			
			// メッセージがない場合は正常終了する
			if(messageList.isEmpty()) {
				return;
			}
			
			int messageID = 0;
			for(RemainderMailMessageBean message: messageList) {
				if(messageID == 0 || messageID < message.getId()) {
					messageID = message.getId();	
				}
			}
			
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			List<RemainderMailAddressBean> addressList = addressDao.getAddressList(messageID);
			
			for(RemainderMailAddressBean address: addressList) {
				RemainderMailMessageBean sendedMessage = null;
				for(RemainderMailMessageBean message: messageList) {
					try {
						MailWrapper.send(address.getMailAddress(), message.getTitle(), message.getMessage());
						sendedMessage = message;
					} catch (Exception e) {
						LogWrapper.put(Level.SEVERE, e);
						break;
					}
				}
				// 送信できたメッセージのIDを更新する
				if(sendedMessage != null) {
					addressDao.updateMessageID(address.getMailAddress(), sendedMessage.getId());
				}
			}
			
			for(RemainderMailMessageBean message: messageList) {
				messageDao.setSend(message.getId());
			}
			
			connection.commit();
			
		} catch (Exception e) {
			if(connection != null) {
				try {
					connection.close();	
				} catch (Exception connectionException) {
					LogWrapper.put(Level.SEVERE, connectionException);
				}
			}
			LogWrapper.put(Level.SEVERE, e);
		} finally {
			if(mysqlConnection != null) {
				try {
					mysqlConnection.closeConnection();
				} catch (Exception connectionException) {
					LogWrapper.put(Level.SEVERE, connectionException);
				}
			}
		}
	}
	
}
