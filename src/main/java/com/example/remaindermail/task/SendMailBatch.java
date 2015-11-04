package com.example.remaindermail.task;

import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

/**
 * メール送信バッチ
 * @author toshikiarai
 * @version 1.0.0
 */
public class SendMailBatch extends HttpServlet {

	/**
	 * タスク実行タイマー
	 */
	private Timer timer = null;
	
	/**
	 * 実行タスククラス
	 */
	private SendMailTask task = null;
	
	
	
	/**
	 * バッチ処理の登録
	 */
	public void init(ServletConfig servletConfig) {
		task = new SendMailTask();
		timer = new Timer(true);
		timer.schedule(task, 0, 60 * 1000);
	}

}
