/**
 * 
 */
package com.example.remaindermail.model.exception;

/**
 * Dao例外
 * @author toshikiarai
 * @version 1.0.0
 */
public class DaoException extends ExceptionBase {
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public DaoException(String message)
	{
		super(message);
	}
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param exception 例外
	 */
	public DaoException(String message, Throwable exception)
	{
		super(message, exception);
	}
	
}
