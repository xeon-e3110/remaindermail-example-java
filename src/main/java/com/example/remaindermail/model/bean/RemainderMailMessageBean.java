package com.example.remaindermail.model.bean;

/**
 * リマインダーメールメッセージクラス
 * reminder_mail_message_tテーブルにマッピングされます
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailMessageBean
{
	
	/**
	 * 通し番号
	 */
	private int id;
	
	/**
	 * メールタイトル
	 */
	private String title;
	
	/**
	 * メール本文
	 */
	private String message;
	
	/**
	 * 登録日("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 */
	private String createDate;
	
	/**
	 * 送信フラグ
	 */
	private int send;

	/**
	 * 通し番号取得
	 * @return id
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * 通し番号設定
	 * @param id
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	
	/**
	 * メールタイトル取得
	 * @return title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * メールタイトル設定
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * メール本文取得
	 * @return message
	 */
	public String getMessage()
	{
		return message;
	}
	
	/**
	 * メール本文設定
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	/**
	 * 作成日取得("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @return createDate
	 */
	public String getCreateDate()
	{
		return createDate;
	}

	/**
	 * 作成日設定("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @param createDate 作成日
	 */
	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	
	/**
	 * 送信フラグ取得(0:未送信 1:送信済み)
	 * @return send
	 */
	public int getSend()
	{
		return send;
	}

	/**
	 * 送信フラグ設定(0:未送信 1:送信済み)
	 * @param send　
	 */
	public void setSend(int send)
	{
		this.send = send;
	}
}
