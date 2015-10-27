package com.example.remaindermail.action;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.remaindermail.model.LogWrapper;
import com.example.remaindermail.model.MailWrapper;
import com.example.remaindermail.model.MysqlConnection;
import com.example.remaindermail.model.bean.RegistMailAddressBean;
import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.opensymphony.xwork2.ActionSupport;

/**
 * リマインダーメールアドレス登録コントローラ
 * @author toshikiarai
 * @version 1.0.0
 */
public class RegistMailAddressAction extends ActionSupport {
	
	/**
	 * ビューデータ
	 */
	private RegistMailAddressBean registMailAddress;

	/**
	 * ビューデータ取得
	 * @return registMailAddress ビューデータ
	 */
	public RegistMailAddressBean getRegistMailAddress() {
		return registMailAddress;
	}

	/**
	 * ビューデータ設定
	 * @param registMailAddress ビューデータ
	 */
	public void setRegistMailAddress(RegistMailAddressBean registMailAddress) {
		this.registMailAddress = registMailAddress;
	}
	
	/**
	 * リマインダーメールアドレス登録
	 * @return String 実行結果
	 */
	public String regist() {
		MysqlConnection mysqlConnection = null;
		Connection connection = null;
		
		try {
			// 初回アクセス時はそのまま表示する
			if(registMailAddress == null) {
				return SUCCESS;
			}
			
			// メールアドレスの長さを判別
			if(registMailAddress.getMailAddress().length() >= 256) {
				registMailAddress.getErrorList().add("メールアドレスが長すぎます");
				registMailAddress.setIsRegist(false);
				return SUCCESS;
			}
			
			// メールアドレス判別
			String regex = "^[a-zA-Z0-9_]+"
					+ "[a-zA-Z0-9.-_]*"
					+ "@"
					+ "[a-zA-Z0-9_-]+"
					+ "[a-zA-Z0-9.-]+[a-zA-Z0-9-]$"
					+ "";
			Pattern mailAddressPattern = Pattern.compile(regex);
			Matcher matcher = mailAddressPattern.matcher(registMailAddress.getMailAddress());
			
			// 無効なメールアドレス
			if(matcher.matches() == false) {
				registMailAddress.getErrorList().add("無効なメールアドレスです");
				registMailAddress.setIsRegist(false);
				return SUCCESS;
			}
			
			// 入力確認
			if(registMailAddress.getMailAddress().equals(registMailAddress.getConfirmMailAddress()) == false) {
				registMailAddress.getErrorList().add("確認用メールアドレスが違います");
				registMailAddress.setIsRegist(false);
				return SUCCESS;
			}
			
			// DBコネクション
			mysqlConnection = new MysqlConnection();
			connection = mysqlConnection.openConnection();
			
			// すでに登録されている場合
			RemainderMailAddressDao dao = new RemainderMailAddressDao(connection);
			if(dao.isRegistedAddress(registMailAddress.getMailAddress())) {
				registMailAddress.getErrorList().add("すでに登録されています");
				registMailAddress.setIsRegist(false);
				return SUCCESS;
			}
			
			// メールアドレス登録
			dao.registAddress(registMailAddress.getMailAddress());
			
			// 登録確認メールを送信する
			MailWrapper.send(registMailAddress.getMailAddress(), "登録確認", "リマインダーメールに登録しました");
			
			// フォームを空にする
			registMailAddress.setMailAddress("");
			registMailAddress.setConfirmMailAddress("");
			registMailAddress.setIsRegist(true);
			
			// DBコミット
			connection.commit();
			
			return SUCCESS;
		} catch(Exception exceptoin) {
			LogWrapper.put(Level.SEVERE, exceptoin);
			registMailAddress.getErrorList().add("エラーが発生しました");

			try {
				if(connection != null) {
					// DBロールバック
					connection.rollback();
				}
			} catch (Exception catchException) {
				LogWrapper.put(Level.SEVERE, catchException);
			}
			return ERROR;
		} finally {
			try {
				if(mysqlConnection != null) {
					// コネクションをクローズ
					mysqlConnection.closeConnection();
				}
			} catch (Exception catchException) {
				LogWrapper.put(Level.SEVERE, catchException);
			}
		}
	}
	
}
