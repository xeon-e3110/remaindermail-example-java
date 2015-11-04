package com.example.remaindermail.model.bean;

/**
 * メール送信ビューBean
 * @author toshikiarai
 * @version 1.0.0
 */
public class RegistMailBean extends ViewBeanBase {
	
	/**
	 * タイトル
	 */
	private String title;
	
	/**
	 * 本文
	 */
	private String message;
	
	/**
	 * 送信フラグ
	 */
	private boolean isRegist;

	/**
	 * メールタイトル取得
	 * @return title メールタイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * メールタイトル設定
	 * @param title メールタイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * メール本文取得
	 * @return message メール本文
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * メール本文設定
	 * @param message メール本文
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 登録フラグ取得
	 * @return isSend 登録したフラグ
	 */
	public boolean getIsRegist() {
		return isRegist;
	}

	/**
	 * 登録フラグ設定
	 * @param isRegist 登録したフラグ
	 */
	public void setIsRegist(boolean isRegist) {
		this.isRegist = isRegist;
	}
	
}
