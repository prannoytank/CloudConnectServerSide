package com.CloudConnect.bean;

public class DropboxLoginBean
{
	
	private String dropboxUid;
	private String username;
    private String key;
    private String secret;
	
	public String getKey() 
	{
		return key;
	}
	public void setKey(String key) 
	{
		this.key = key;
	}
	public String getSecret()
	{
		return secret;
	}
	public void setSecret(String secret) 
	{
		this.secret = secret;
	}
	public String getDropboxUid() 
	{
		return dropboxUid;
	}
	public void setDropboxUid(String dropboxUid)
	{
		this.dropboxUid = dropboxUid;
	}
	
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

}
