package com.example.remaindermail.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ロガー
 * @author toshikiarai
 * @version 1.0.0
 */
public class LogWrapper {
	
	/**
	 * ロガーインスタンス
	 */
	private static Logger logger;
	
	/**
	 * ロガー取得
	 * @return logger ロガーオブジェクト
	 */
	protected static Logger getInstance() {
		if(logger == null) {
			logger = Logger.getLogger("com.example.remaindermail.model.Log");
		}
		return logger;
	}
	
	/**
	 * ログ出力
	 * @param level 出力レベル
	 * @param message 出力するメッセージ
	 */
	public static void put(Level level, String message) {
		getInstance().log(level, message);
	}
	
	/**
	 * ログ出力
	 * @param level 出力レベル
	 * @param exception 出力するメッセージ
	 */
	public static void put(Level level, Throwable exception) {
		getInstance().log(level, exception.getMessage(), exception);
		System.err.println(exception.getMessage());
		exception.printStackTrace(System.err);
	}

}
