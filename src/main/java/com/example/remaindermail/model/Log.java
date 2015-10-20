package com.example.remaindermail.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ロガー
 * @author toshikiarai
 * @version 1.0.0
 */
public class Log 
{
	/**
	 * ロガーインスタンス
	 */
	private static Logger logger;
	
	/**
	 * ロガー取得
	 * @return logger
	 */
	protected static Logger getInstance()
	{
		if(logger == null)
		{
			logger = Logger.getLogger("com.example.remaindermail.model.Log");
		}
		return logger;
	}
	
	/**
	 * ログ出力
	 * @param level
	 * @param message
	 */
	public static void put(Level level, String message)
	{
		getInstance().log(level, message);
	}
	
	/**
	 * ログ出力
	 * @param level
	 * @param exception
	 */
	public static void put(Level level, Throwable exception)
	{
		getInstance().log(level, exception.getMessage(), exception);
	}

}
