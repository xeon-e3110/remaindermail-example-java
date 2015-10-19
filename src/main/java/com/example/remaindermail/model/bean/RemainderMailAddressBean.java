package com.example.remaindermail.model.bean;

/**
 * リマインダーメールアドレス
 * remainder_mail_address_tにマッピングされます
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailAddressBean
{
	
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	
	/**
	 * 登録日("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 */
	private String createDate;
	
	/**
	 * 更新日("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 */
	private String updateDate;

	/**
	 * 論理削除フラグ(0:削除していない 1:削除済み)
	 */
	private int delete;
	
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
	 * 作成日取得("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @return createDate
	 */
	public String getCreateDate() 
	{
		return createDate;
	}
	
	/**
	 * 作成日設定("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @param createDate
	 */
	public void setCreateDate(String createDate) 
	{
		this.createDate = createDate;
	}
	
	/**
	 * 更新日取得("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @return updateDate
	 */
	public String getUpdateDate() 
	{
		return updateDate;
	}
	
	/**
	 * 更新日設定("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate) 
	{
		this.updateDate = updateDate;
	}
	
	/**
	 * 論理削除フラグ取得(0:削除していない 1:削除済み)
	 * @return delete
	 */
	public int getDelete() 
	{
		return delete;
	}
	
	/**
	 * 論理削除フラグ設定(0:削除していない 1:削除済み)
	 * @param delete
	 */
	public void setDelete(int delete) 
	{
		this.delete = delete;
	}
	
}
