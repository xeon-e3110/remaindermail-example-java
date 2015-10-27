package com.example.remaindermail.model.bean;

/**
 * メール送信ビューBean
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMailBean extends ViewBeanBase {
	
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
	private boolean isSend;
	
	/**
	 * 再送フラグ
	 */
	private boolean isResend;

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
	public boolean getIsSend() {
		return isSend;
	}

	/**
	 * 登録フラグ設定
	 * @param isSend 登録したフラグ
	 */
	public void setIsSend(boolean isSend) {
		this.isSend = isSend;
	}

	/**
	 * 再送フラグ取得
	 * @return isResend
	 */
	public boolean getIsResend() {
		return isResend;
	}

	/**
	 * 再送フラグ設定
	 * @param isResend
	 */
	public void setIsResend(boolean isResend) {
		this.isResend = isResend;
	}
}
