package com.example.remaindermail.action;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

import com.example.remaindermail.model.LogWrapper;
import com.example.remaindermail.model.MailWrapper;
import com.example.remaindermail.model.MysqlConnection;
import com.example.remaindermail.model.bean.RemainderMailAddressBean;
import com.example.remaindermail.model.bean.RemainderMailMessageBean;
import com.example.remaindermail.model.bean.SendMailBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.example.remaindermail.model.dao.RemainderMailMessageDao;
import com.opensymphony.xwork2.ActionSupport;

/**
 * リマインダーメール登録コントローラ
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMailAction extends ActionSupport {
	
	/**
	 * リマインダーメールメッセージ
	 */
	private SendMailBean sendMail;
	
	/**
	 * ビューデータ取得
	 * @return sendMail ビューデータ
	 */
	public SendMailBean getSendMail() {
		return sendMail;
	}

	/**
	 * ビューデータ設定
	 * @param sendMail ビューデータ
	 */
	public void setSendMail(SendMailBean sendMail) {
		this.sendMail = sendMail;
	}

	/**
	 * メール送信
	 * @return String 実行結果
	 */
	public String send() {
		// 初回アクセス時
		if(sendMail == null) {
			return SUCCESS;
		}
		
		// 入力チェック
		if(sendMail.getTitle().isEmpty()) {
			sendMail.getErrorList().add("タイトルを指定してください");
		}
		
		if(sendMail.getMessage().isEmpty()) {
			sendMail.getErrorList().add("本文を指定してください");
		}
		
		if(sendMail.getErrorList().isEmpty() == false) {
			return SUCCESS;
		}
		
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try {
			
			// Mysqlコネクションを開く
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			// 履歴に登録
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			RemainderMailMessageBean bean = new RemainderMailMessageBean();
			bean.setTitle(sendMail.getTitle());
			bean.setMessage(sendMail.getMessage());
			bean.setCreateDate(sdf.format(date));
			bean.setSend(1);
			
			RemainderMailMessageDao messageDao = new RemainderMailMessageDao(connection);
			bean = messageDao.registMessage(bean);
			
			// 送信先をまとめる
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			ArrayList<RemainderMailAddressBean> addressList = addressDao.getAddressList(bean.getId());
			ArrayList<String> inetAddressList = new ArrayList<String>();
			for(RemainderMailAddressBean rmaBean:addressList) {
				inetAddressList.add(rmaBean.getMailAddress());
			}
			
			if(inetAddressList.isEmpty()) {
				sendMail.getErrorList().add("宛先が一件もありません");
				connection.rollback();
				return SUCCESS;
			}
			
			
			// それぞれにメールを送る
			ArrayList<String> faildSendAddressList = new ArrayList<String>();
			for(String address: inetAddressList) {
				try {
					MailWrapper.send(address, bean.getTitle(), bean.getMessage());
					addressDao.updateMessageID(address, bean.getId());
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
					faildSendAddressList.add(address);
				}
			}
			
			// 送信済み状態にする
			sendMail.setIsSend(true);
			
			// 入力をクリア
			sendMail.setTitle("");
			sendMail.setMessage("");
			
			// メールを送れなかった人の一覧を表示
			if(faildSendAddressList.isEmpty() == false)
			{
				sendMail.getErrorList().add("一部のアドレスにメールが送れませんでした");
				for(String address:faildSendAddressList)
				{
					sendMail.getErrorList().add("    " + address);
				}
				
				sendMail.setIsResend(true);
			}

			// DBコミット
			connection.commit();
			
		}
		catch(Exception e) {
			LogWrapper.put(Level.SEVERE, e);
			sendMail.getErrorList().add("エラーが発生しました");
			if(connection != null) {
				try {
					connection.rollback();
				} catch (Exception exception1) {
					LogWrapper.put(Level.SEVERE, exception1);
				}	
			}
			return ERROR;
		} finally {
			if(mysqlConnection != null) {
				try {
					mysqlConnection.closeConnection();
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
				}
			}
		}
		
		return SUCCESS;
	}
	
	public String resend()
	{
		// 初回アクセス時
		if(sendMail == null) {
			return send();
		}
		
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try {
			
			// Mysqlコネクションを開く
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			// 最後に登録されたメッセージの取得
			RemainderMailMessageDao messageDao = new RemainderMailMessageDao(connection);
			RemainderMailMessageBean latestMessage = messageDao.getLatestMessage();
			
			// 送信先をまとめる
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			ArrayList<RemainderMailAddressBean> addressList = addressDao.getAddressList(latestMessage.getId());
			ArrayList<String> inetAddressList = new ArrayList<String>();
			for(RemainderMailAddressBean bean:addressList) {
				inetAddressList.add(bean.getMailAddress());
			}
			
			if(inetAddressList.isEmpty()) {
				sendMail.getErrorList().add("宛先が一件もありません");
				connection.rollback();
				return SUCCESS;
			}

			
			// それぞれにメールを送る
			ArrayList<String> faildSendAddressList = new ArrayList<String>();
			for(String address: inetAddressList) {
				try {
					MailWrapper.send(address, latestMessage.getTitle(), latestMessage.getMessage());
					addressDao.updateMessageID(address, latestMessage.getId());
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
					faildSendAddressList.add(address);
				}
			}
			
			// 送信済み状態にする
			sendMail.setIsSend(true);
			
			// 入力をクリア
			sendMail.setTitle("");
			sendMail.setMessage("");
			sendMail.setIsResend(false);
			
			// 送れなかった人がいたら再送処理を表示する
			if(faildSendAddressList.isEmpty() == false)
			{
				sendMail.setIsResend(true);
			}

			// DBコミット
			connection.commit();
			
		}
		catch(Exception e) {
			LogWrapper.put(Level.SEVERE, e);
			sendMail.getErrorList().add("エラーが発生しました");
			if(connection != null) {
				try {
					connection.rollback();
				} catch (Exception exception1) {
					LogWrapper.put(Level.SEVERE, exception1);
				}	
			}
			return ERROR;
		} finally {
			if(mysqlConnection != null) {
				try {
					mysqlConnection.closeConnection();
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
				}
			}
		}
		return SUCCESS;
	}

}
