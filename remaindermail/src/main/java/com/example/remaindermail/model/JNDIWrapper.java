package com.example.remaindermail.model;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.example.remaindermail.model.exception.JNDILookupException;

/**
 * JNDIラッパークラス<br>
 * JNDIからオブジェクトを検索するラッパークラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class JNDIWrapper {
	
	/**
	 * JNDI検索用プレフィックス
	 */
	private static final String jndiPrefix = "java:comp/env";
	
	/**
	 * JNDI検索コンテキスト
	 */
	private static InitialContext initialContext = null;
	
	/**
	 * 検索コンテキストの取得
	 * @return コンテキスト
	 * @throws Exception 発生した例外
	 */
	private static InitialContext getInitialContext() throws Exception {
		if(initialContext == null) {
			initialContext = new InitialContext();
		}
		return initialContext;
	}
	
	/**
	 * JNDIからオブジェクトを取得する
	 * @param lookupName 検索する名前
	 * @return T 検索したオブジェクト
	 * @throws Exception 発生した例外
	 */
	public static <T> T lookup(String lookupName) throws Exception {
		String lookupString = jndiPrefix + "/" + lookupName;
		Object lookupObj = getInitialContext().lookup(lookupString);
		if(lookupObj == null) {
			throw new JNDILookupException("JDNIからインスタンスを取得できませんでした lookup:" + lookupString);
		}
		return (T) lookupObj;
	}

}
