package com.example.remaindermail.model.bean;

/**
 * リマインダーメールアドレス
 * remainder_mail_address_tにマッピングされます
 * @author toshikiarai
 * @version 1.0.0
 */
public class RemainderMailAddressBean {
	
	/**
	 * メールアドレス
	 */
	private String mailAddress;
	
	/**
	 * メッセージID
	 */
	private int messageID;
	
	/**
	 * 登録日 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 */
	private String createDate;
	
	/**
	 * 更新日 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 */
	private String updateDate;

	/**
	 * 論理削除フラグ (0:削除していない 1:削除済み)
	 */
	private int delete;
	
	/**
	 * メールアドレス取得
	 * @return mailAddress メールアドレス
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	
	/**
	 * メールアドレス設定
	 * @param mailAddress メールアドレス
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	/**
	 * メッセージID取得
	 * @return messageID メッセージID
	 */
	public int getMessageID() {
		return messageID;
	}

	/**
	 * メッセージID設定
	 * @param messageID メッセージID
	 */
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	/**
	 * 作成日取得 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @return createDate 作成日
	 */
	public String getCreateDate() {
		return createDate;
	}
	
	/**
	 * 作成日設定 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @param createDate 作成日
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 更新日取得 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @return updateDate 更新日
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	
	/**
	 * 更新日設定 ("yyyy-MM-dd hh:mm:ss":形式の文字列)
	 * @param updateDate 更新日
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	/**
	 * 論理削除フラグ取得 (0:削除していない 1:削除済み)
	 * @return delete 論理削除フラグ
	 */
	public int getDelete() {
		return delete;
	}
	
	/**
	 * 論理削除フラグ設定 (0:削除していない 1:削除済み)
	 * @param delete 論理削除フラグ
	 */
	public void setDelete(int delete) {
		this.delete = delete;
	}
	
}
