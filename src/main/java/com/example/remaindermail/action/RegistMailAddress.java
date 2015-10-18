package com.example.remaindermail.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.remaindermail.model.dao.RemainderMailAddressDao;
import com.opensymphony.xwork2.ActionSupport;

public class RegistMailAddress extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String mailAddress;
	protected String confilrmMailAddress;
	protected boolean isRegist = false;
	protected ArrayList<String> errorList = new ArrayList<String>();
	
	public boolean getIsRegist()
	{
		return isRegist;
	}

	public String getMailAddress()
	{
		return mailAddress;
	}
	
	public void setMailAddress(String address)
	{
		mailAddress = address;
	}

	public String getConfirmMailAddress()
	{
		return confilrmMailAddress;
	}
	
	public void setConfirmMailAddress(String address)
	{
		confilrmMailAddress = address;
	}
	
	public ArrayList<String> getErrorList()
	{
		return errorList;
	}
	
	public String regist() throws ClassNotFoundException, SQLException
	{
		//errorList.add("entry::mailAddress " + mailAddress);
		//errorList.add("entry::confirmMailAddress " + confilrmMailAddress);
		
		//初回アクセス時はそのまま表示する
		if(mailAddress == null)
		{
			return SUCCESS;
		}
		
		String regex = "^[a-zA-Z0-9_]+"
				+ "[a-zA-Z0-9.-_]*"
				+ "@"
				+ "[a-zA-Z0-9_-]+"
				+ "[a-zA-Z0-9.-]+[a-zA-Z0-9-]$"
				+ "";
		Pattern mailAddressPattern = Pattern.compile(regex);
		Matcher matcher = mailAddressPattern.matcher(mailAddress);
		
		//無効なメールアドレス
		if(matcher.matches() == false)
		{
			errorList.add("無効なメールアドレスです");
			isRegist = false;
			return ERROR;
		}
		
		//入力確認
		if(mailAddress.equals(confilrmMailAddress) == false)
		{
			errorList.add("確認用メールアドレスが違います");
			isRegist = false;
			return ERROR;
		}
		
		//すでに登録されている場合
		RemainderMailAddressDao dao = new RemainderMailAddressDao();
		if(dao.isRegistedAddress(mailAddress))
		{
			errorList.add("すでに登録されています");
			isRegist = false;
			return ERROR;
		}
		
		//メールアドレス登録
		if(dao.registAddress(mailAddress) == false)
		{
			errorList.add("登録に失敗しました");
			isRegist = false;
			return ERROR;
		}
		
		isRegist = true;
		return SUCCESS;
	}
}
