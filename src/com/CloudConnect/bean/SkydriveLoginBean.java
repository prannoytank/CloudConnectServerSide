package com.CloudConnect.bean;

public class SkydriveLoginBean 
{
	private String skydriveUid;
	private String username;
    private String accessToken;
    private String refreshToken;
    
	public String getSkydriveUid() {
		return skydriveUid;
	}
	public void setSkydriveUid(String skydriveUid) {
		this.skydriveUid = skydriveUid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
    
    

}
