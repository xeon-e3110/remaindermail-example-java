package com.example.remaindermail.model.bean;

/**
 * メール送信ビューBean
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMailBean extends ViewBeanBase 
{
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
	 * タイトル取得
	 * @return title
	 */
	public String getTitle() 
	{
		return title;
	}

	/**
	 * タイトル設定
	 * @param title
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}

	/**
	 * 本文取得
	 * @return message
	 */
	public String getMessage() 
	{
		return message;
	}

	/**
	 * 本文設定
	 * @param message
	 */
	public void setMessage(String message) 
	{
		this.message = message;
	}

	/**
	 * 登録フラグ取得
	 * @return isSend
	 */
	public boolean isSend() 
	{
		return isSend;
	}

	/**
	 * 登録フラグ設定
	 * @param isSend
	 */
	public void setSend(boolean isSend) 
	{
		this.isSend = isSend;
	}
}
