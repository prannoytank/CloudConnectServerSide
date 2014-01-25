package com.CloudConnect.bean;

public class DropboxDataBean 
{
	private int userId;
	private int _id;
	private int parentFolder;
	private String fileName;
	private String folderPath;
	private int isFolder;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int getParentFolder() {
		return parentFolder;
	}
	public void setParentFolder(int parentFolder) {
		this.parentFolder = parentFolder;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public int getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(int isFolder) {
		this.isFolder = isFolder;
	}
	
	

	
}
