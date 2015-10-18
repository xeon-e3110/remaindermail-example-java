package com.example.remaindermail.model.bean;

public class RemainderMailMessageBean {
	private int id;
	private String title;
	private String message;
	private String createDate;
	private int send;

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}
	
	public int getSend()
	{
		return send;
	}

	public void setSend(int send)
	{
		this.send = send;
	}
}
