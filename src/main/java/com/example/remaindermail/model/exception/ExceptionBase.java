package com.example.remaindermail.model.exception;

import java.util.logging.Level;

import com.example.remaindermail.model.LogWrapper;

/**
 * 例外ベースクラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class ExceptionBase extends Exception {
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public ExceptionBase(String message) 
	{
		super(message);
	}
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param exception 例外
	 */
	public ExceptionBase(String message, Throwable exception)
	{
		super(message, exception);
	}

	/**
	 * ログ出力
	 */
	public void put()
	{
		LogWrapper.put(Level.SEVERE, getMessage());
		Throwable cause = getCause();
		if(cause != null)
		{
			LogWrapper.put(Level.SEVERE, cause);
		}
	}
	
}
