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
			return ERROR;
		}
		
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try {
			
			// Mysqlコネクションを開く
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			// 送信先をまとめる
			RemainderMailAddressDao addressDao = new RemainderMailAddressDao(connection);
			ArrayList<RemainderMailAddressBean> addressList = addressDao.getAddressList();
			ArrayList<String> inetAddressList = new ArrayList<String>();
			for(RemainderMailAddressBean bean:addressList) {
				inetAddressList.add(bean.getMailAddress());
			}
			
			if(inetAddressList.isEmpty()) {
				sendMail.getErrorList().add("宛先が一件もありません");
				return SUCCESS;
			}
			
			// それぞれにメールを送る
			ArrayList<String> faildSendAddressList = new ArrayList<String>();
			for(String address: inetAddressList) {
				try {
					MailWrapper.send(address, sendMail.getTitle(), sendMail.getMessage());	
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
					faildSendAddressList.add(address);
				}
			}
			
			// メールを送れなかった人の一覧を表示
			if(faildSendAddressList.isEmpty() == false)
			{
				String msg = "一部のアドレスにメールが送れませんでした<br>";
				for(String address:faildSendAddressList)
				{
					msg += (address + "<br>");
				}
				sendMail.getErrorList().add(msg);
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
			
			// 送信済み状態にする
			sendMail.setIsSend(true);
			
			// 入力をクリア
			sendMail.setTitle("");
			sendMail.setMessage("");

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
