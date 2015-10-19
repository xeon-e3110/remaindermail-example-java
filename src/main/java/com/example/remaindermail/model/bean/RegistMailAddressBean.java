package com.example.remaindermail.model.bean;

/**
 * メールアドレス登録画面用Bean
 * @author toshikiarai
 * @version 1.0.0
 */
public class RegistMailAddressBean extends ViewBeanBase
{
	
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	
	/**
	 * 確認用メールアドレス
	 */
	private String confirmMailAddress;
	
	/**
	 * 登録フラグ
	 */
	private boolean isRegist;

	/**
	 * メールアドレス取得
	 * @return mailAddress
	 */
	public String getMailAddress() 
	{
		return mailAddress;
	}

	/**
	 * メールアドレス設定
	 * @param mailAddress
	 */
	public void setMailAddress(String mailAddress) 
	{
		this.mailAddress = mailAddress;
	}

	/**
	 * 確認用メールアドレス取得
	 * @return confirmMailAddress
	 */
	public String getConfirmMailAddress() 
	{
		return confirmMailAddress;
	}

	/**
	 * 確認用メールアドレス設定
	 * @param confirmMailAddress
	 */
	public void setConfirmMailAddress(String confirmMailAddress) 
	{
		this.confirmMailAddress = confirmMailAddress;
	}

	/**
	 * 登録フラグ取得
	 * @return isRegist
	 */
	public boolean isRegist() {
		return isRegist;
	}

	/**
	 * 登録フラグセット
	 * @param isRegist
	 */
	public void setRegist(boolean isRegist) {
		this.isRegist = isRegist;
	}
	
}
