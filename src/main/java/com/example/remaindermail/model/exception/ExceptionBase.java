/**
 * 
 */
package com.example.remaindermail.model.exception;

import java.util.logging.Level;

import com.example.remaindermail.model.Log;

/**
 * 例外ベースクラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class ExceptionBase extends Exception 
{
	/**
	 * コンストラクタ
	 * @param message
	 */
	public ExceptionBase(String message) 
	{
		super(message);
	}
	
	/**
	 * コンストラクタ
	 * @param message
	 * @param exception
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
		Log.put(Level.SEVERE, getMessage());
		Throwable cause = getCause();
		if(cause != null)
		{
			Log.put(Level.SEVERE, cause);
		}
	}
}
