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
import com.example.remaindermail.model.bean.RegistMailBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.example.remaindermail.model.dao.RemainderMailMessageDao;
import com.example.remaindermail.task.SendMailTask;
import com.opensymphony.xwork2.ActionSupport;

/**
 * リマインダーメール登録コントローラ
 * @author toshikiarai
 * @version 1.0.0
 */
public class RegistMailAction extends ActionSupport {
	
	/**
	 * リマインダーメールメッセージ
	 */
	private RegistMailBean registMail;
	
	/**
	 * ビューデータ取得
	 * @return sendMail ビューデータ
	 */
	public RegistMailBean getRegistMail() {
		return registMail;
	}

	/**
	 * ビューデータ設定
	 * @param sendMail ビューデータ
	 */
	public void setRegistMail(RegistMailBean sendMail) {
		this.registMail = sendMail;
	}

	/**
	 * メール送信
	 * @return String 実行結果
	 */
	public String regist() {
		
		// 初回アクセス時
		if(registMail == null) {
			return SUCCESS;
		}
		
		// 入力チェック
		if(registMail.getTitle().isEmpty()) {
			registMail.getErrorList().add("タイトルを指定してください");
		}
		
		if(registMail.getMessage().isEmpty()) {
			registMail.getErrorList().add("本文を指定してください");
		}
		
		if(registMail.getErrorList().isEmpty() == false) {
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
			RemainderMailMessageDao messageDao = new RemainderMailMessageDao(connection);
			RemainderMailMessageBean bean = new RemainderMailMessageBean();
			bean.setTitle(registMail.getTitle());
			bean.setMessage(registMail.getMessage());
			bean.setCreateDate(sdf.format(date));
			bean.setSend(0);
			bean = messageDao.registMessage(bean);
			
			// 登録完了状態にする
			registMail.setIsRegist(true);
			
			// 入力をクリア
			registMail.setTitle("");
			registMail.setMessage("");

			// DBコミット
			connection.commit();
			
		}
		catch(Exception e) {
			LogWrapper.put(Level.SEVERE, e);
			registMail.getErrorList().add("エラーが発生しました");
			if(connection != null) {
				try {
					// ロールバック
					connection.rollback();
				} catch (Exception exception1) {
					LogWrapper.put(Level.SEVERE, exception1);
				}	
			}
			return ERROR;
		} finally {
			if(mysqlConnection != null) {
				try {
					// コネクションをクローズ
					mysqlConnection.closeConnection();
				} catch (Exception e) {
					LogWrapper.put(Level.SEVERE, e);
				}
			}
		}
		
		return SUCCESS;
	}

}
