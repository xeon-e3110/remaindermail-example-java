/**
 * 
 */
package com.example.remaindermail.model.exception;

/**
 * JDNIルックアップ例外
 * @author toshikiarai
 */
public class JNDILookupException extends ExceptionBase 
{
	/**
	 * コンストラクタ
	 * @param message
	 */
	public JNDILookupException(String message)
	{
		super(message);
	}
	
	/**
	 * コンストラクタ
	 * @param message
	 * @param exception
	 */
	public JNDILookupException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
