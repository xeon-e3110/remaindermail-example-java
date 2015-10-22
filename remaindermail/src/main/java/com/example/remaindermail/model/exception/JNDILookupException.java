/**
 * 
 */
package com.example.remaindermail.model.exception;

/**
 * JDNIルックアップ例外
 * @author toshikiarai
 */
public class JNDILookupException extends ExceptionBase {
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 */
	public JNDILookupException(String message)
	{
		super(message);
	}
	
	/**
	 * コンストラクタ
	 * @param message メッセージ
	 * @param exception 例外
	 */
	public JNDILookupException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
