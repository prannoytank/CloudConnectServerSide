package com.CloudConnect.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.CloudConnect.bean.DropboxDataBean;
import com.CloudConnect.bean.DropboxLoginBean;
import com.CloudConnect.utils.DropboxApiKeys;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;


public class DropboxModel 
{
	Connection con=DBConnection.getConnection();
	DropboxAPI<WebAuthSession> mDBApi;
	int user_id;
	AccessTokenPair accessToken;
	WebAuthSession session;
	AppKeyPair akp;
	
	public ArrayList<DropboxDataBean> getSearchData(String uid,String FileName) throws SQLException
	{
		DropboxDataBean dbb;
		ArrayList<DropboxDataBean> allList=new ArrayList<DropboxDataBean>();
		String getList="SELECT * FROM dropboxdata where isFolder=0 and FileName like '"+FileName+"%' and DropboxId=(Select DropboxId from dropboxmaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		dbb=new DropboxDataBean();
    		dbb.set_id(rs.getInt(1));
    		dbb.setFileName(rs.getString(3));
    		dbb.setIsFolder(rs.getInt(5));
    		dbb.setFolderPath(rs.getString(4));
    		allList.add(dbb);
    		
    		
    	}
		return allList;
		
	}
	
	
	
	public ArrayList<DropboxDataBean> getFolderClickData(String uid,int parentid) throws SQLException
	{
		DropboxDataBean dbb;
		ArrayList<DropboxDataBean> allList=new ArrayList<DropboxDataBean>();
		String getList="Select * from dropboxdata where ParentFolder="+parentid + " and DropboxId=(Select DropboxId from dropboxmaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		dbb=new DropboxDataBean();
    		dbb.set_id(rs.getInt(1));
    		dbb.setFileName(rs.getString(3));
    		dbb.setIsFolder(rs.getInt(5));
    		dbb.setFolderPath(rs.getString(4));
    		allList.add(dbb);
    		
    		
    	}
		return allList;
		
	}
	
	public ArrayList<DropboxDataBean> GetDataForList(String uid,int parentid) throws SQLException
	{
		DropboxDataBean dbb;
		ArrayList<DropboxDataBean> allList=new ArrayList<DropboxDataBean>();
		String getList="Select * from dropboxdata where ParentFolder="+parentid + " and DropboxId=(Select DropboxId from dropboxmaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		dbb=new DropboxDataBean();
    		dbb.set_id(rs.getInt(1));
    		dbb.setFileName(rs.getString(3));
    		dbb.setIsFolder(rs.getInt(5));
    		dbb.setFolderPath(rs.getString(4));
    		allList.add(dbb);
    		
    		
    	}
		return allList;
		
		
	}
	
	
	
	
	public boolean DropboxLoginInsert(DropboxLoginBean dlb) throws SQLException
	{
		String add_dropboxUser="insert into dropboxmaster(UserName,Uid,TokenKey,TokenSecret) values(?,?,?,?)";
		PreparedStatement psmt=con.prepareStatement(add_dropboxUser);
		psmt.setString(1,dlb.getUsername());
		psmt.setString(2,dlb.getDropboxUid());
		psmt.setString(3,dlb.getKey());
		psmt.setString(4,dlb.getSecret());
		
		psmt.executeUpdate();
		con.commit();
		return true;
	}
	
	
	public boolean DropboxDataInsert(DropboxDataBean ddb) throws SQLException
	{
		String add_dropboxData="insert into dropboxdata(DropboxId,FileName,FolderPath,isFolder,ParentFolder) values(?,?,?,?,?)";
		PreparedStatement psmt=con.prepareStatement(add_dropboxData);
		psmt.setInt(1,ddb.get_id());
		psmt.setString(2,ddb.getFileName());
		psmt.setString(3,ddb.getFolderPath());
		psmt.setInt(4,ddb.getIsFolder());
		psmt.setInt(5,ddb.getParentFolder());
		
		psmt.executeUpdate();
		con.commit();
		return true;
		
	}
	
    public int maxId() throws SQLException
    {
    	String get_max="Select max(DboxId) from dropboxdata";
    	int max = 0;
    	Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(get_max);
    	while(rs.next())
    	{
    		max=rs.getInt(1);
    	}
		return max;
    }
    
   public void InsertDropboxData(String id,String key,String secret) throws DropboxException, SQLException
   {
	    akp=new AppKeyPair(DropboxApiKeys.APP_KEY,DropboxApiKeys.APP_SECRET);
	    accessToken = new AccessTokenPair(key,secret);
	    session=new WebAuthSession(akp, AccessType.DROPBOX,accessToken);
		mDBApi=new DropboxAPI<WebAuthSession>(session);
	    user_id=Integer.parseInt(id);
		Entry list = mDBApi.metadata("/",1000, null, true, null);
		int max=maxId();
		if(max==0)
		listFilesForFolder(list,maxId());
		else
		listFilesForFolder(list,maxId()+1);
	   
	   
	   
   }
   private void listFilesForFolder(Entry list,int fileid) throws DropboxException, SQLException
   {
   	
   	final int parentId =fileid;
   	int dboxId;
   	for (Entry ent : list.contents) 
   	{
   		if (ent.isDir) 
   		{
   			
   			Entry list1 = mDBApi.metadata(ent.path,1000, null,true,null);
   			dboxId=maxId()+1;
   		    DataEntry(dboxId,user_id,ent.fileName(),parentId,ent.path,1);
   			listFilesForFolder(list1,dboxId);
   			
   		}
   		else 
   		{
   			DataEntry(maxId()+1,user_id,ent.fileName(),parentId,"null",0);
   			
   			
   		}
   	}

   }
   
   
   
   public void DataEntry(int DropboxId,int UserId,String FileName,int ParentFolder,String path,int isFolder) throws SQLException
   {
	   String add_dropboxData="insert into dropboxdata(DboxId,DropboxId,FileName,FolderPath,isFolder,ParentFolder) values(?,?,?,?,?,?)";
		PreparedStatement psmt=con.prepareStatement(add_dropboxData);
		psmt.setInt(1,DropboxId);
		psmt.setInt(2,UserId);
		psmt.setString(3,FileName);
		psmt.setString(4,path);
		psmt.setInt(5,isFolder);
		psmt.setInt(6,ParentFolder);
		
		psmt.executeUpdate();
		con.commit();
		
	   
	   
	   
   }
   public String[] getKeys(String Uid) throws SQLException
   {
	   String[] keys = new String[3];
	   String get_key="Select DropboxId,TokenKey,TokenSecret from dropboxmaster where uid="+Uid;
	   Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(get_key);
   	while(rs.next())
   	{
   		keys[0]=String.valueOf(rs.getInt(1));
   		keys[1]=rs.getString(2);
   		keys[2]=rs.getString(3);
   	}
	   
	return keys;
   }
   
    public boolean UploadToDropbox(String id,String key,String secret,String filename)
    {
    	 akp=new AppKeyPair(DropboxApiKeys.APP_KEY,DropboxApiKeys.APP_SECRET);
    	 accessToken = new AccessTokenPair(key,secret);
 	     session=new WebAuthSession(akp, AccessType.DROPBOX,accessToken);
 		 mDBApi=new DropboxAPI<WebAuthSession>(session);
    	
 		FileInputStream inputStream = null;
		try {
		    File file = new File(filename);
		    inputStream = new FileInputStream(file);
		    Entry newEntry = mDBApi.putFile("/"+filename,inputStream,file.length(), null, null);
		  
		    file.delete();
			}
		catch (DropboxUnlinkedException e)
		{
		    // User has unlinked, ask them to link again here.
		    e.printStackTrace();
		}
		catch (DropboxException e)
		{
		   e.printStackTrace();
		}
		catch (FileNotFoundException e)
		{
		    e.printStackTrace();
		}
		finally 
		{
		    if (inputStream != null)
		    {
		        try {
		            inputStream.close();
		        }
		        catch (IOException e) {}
		    }
		}
	 
 		 
 		 
    	
		return true;
    	
    }
    
    public boolean DownloadFromDropbox(String DropboxFolderStorage,String path,String service,String key,String secret)
    {
    	akp=new AppKeyPair(DropboxApiKeys.APP_KEY,DropboxApiKeys.APP_SECRET);
    	accessToken = new AccessTokenPair(key,secret);
	    session=new WebAuthSession(akp, AccessType.DROPBOX,accessToken);
		mDBApi=new DropboxAPI<WebAuthSession>(session);
		FileOutputStream outputStream = null;
		File file=null;
		try
		{
		
		String fullpath=path+service;
		file=new File(service);
		outputStream = new FileOutputStream(file);
		    
		    
		    
		    
		    DropboxFileInfo info = mDBApi.getFile(fullpath, null, outputStream, null);
		   
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 // }
		 
		
		
		finally
		{
		    if (outputStream != null)
		    {
		        try 
		        {
		            outputStream.close();
		        } 
		        catch (IOException e) {}
		    }
		}
    	
    	
		return false;
    	
    }
}
