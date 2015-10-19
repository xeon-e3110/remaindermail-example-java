package com.example.remaindermail.model.bean;

import java.util.ArrayList;

/**
 * ビュー用Beanベースクラス
 * @author toshikiarai
 * @version 1.0.0
 */
public class ViewBeanBase 
{
	
	/**
	 * エラーリスト
	 */
	private ArrayList<String> errorList = new ArrayList<String>();

	/**
	 * エラーリスト取得
	 * @return errorList
	 */
	public ArrayList<String> getErrorList() 
	{
		return errorList;
	}

	/**
	 * エラーリストセット
	 * @param errorList
	 */
	public void setErrorList(ArrayList<String> errorList) 
	{
		this.errorList = errorList;
	}
	
}
