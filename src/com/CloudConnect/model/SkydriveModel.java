package com.CloudConnect.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import com.CloudConnect.bean.DropboxDataBean;
import com.CloudConnect.bean.SkydriveDataBean;
import com.CloudConnect.bean.SkydriveLoginBean;
import com.dropbox.client2.jsonextract.JsonExtractionException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class SkydriveModel 

{
	Connection con=DBConnection.getConnection();
	String AccessToken;
	Client client;
	WebResource webResource;
	ClientResponse response;
	int parent_id;
	
	public ArrayList<SkydriveDataBean> getSearchData(String uid,String FileName) throws SQLException
	{
		SkydriveDataBean sbb;
		ArrayList<SkydriveDataBean> allList=new ArrayList<SkydriveDataBean>();
		String getList="SELECT * FROM skydrivedata where isFolder=0 and FileName like '"+FileName+"%' and SkydriveId=(Select SkydriveId from skydrivemaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		sbb=new SkydriveDataBean();
    		sbb.set_id(rs.getInt(1));
    		sbb.setFileName(rs.getString(3));
    		sbb.setIsFolder(rs.getInt(6));
    		sbb.setFolderPath(rs.getString(4));
    		allList.add(sbb);
    		
    		
    	}
		return allList;
		
	}
	
	
	
	public ArrayList<SkydriveDataBean> getFolderClickData(String uid,int parentid) throws SQLException
	{
		SkydriveDataBean sbb;
		ArrayList<SkydriveDataBean> allList=new ArrayList<SkydriveDataBean>();
		String getList="Select * from skydrivedata where ParentFolder="+parentid + " and SkydriveId=(Select SkydriveId from skydrivemaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		sbb=new SkydriveDataBean();
    		sbb.set_id(rs.getInt(1));
    		sbb.setFileName(rs.getString(3));
    		sbb.setIsFolder(rs.getInt(5));
    		sbb.setFolderPath(rs.getString(4));
    		allList.add(sbb);
    		
    		
    	}
		return allList;
		
	}
	
	public ArrayList<SkydriveDataBean> GetDataForList(String uid,int parentid) throws SQLException
	{
		SkydriveDataBean sbb;
		ArrayList<SkydriveDataBean> allList=new ArrayList<SkydriveDataBean>();
		String getList="Select * from skydrivedata where ParentFolder="+parentid + " and SkydriveId=(Select SkydriveId from skydrivemaster where Uid='"+uid+"')";
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getList);
    	while(rs.next())
    	{
    		sbb=new SkydriveDataBean();
    		sbb.set_id(rs.getInt(1));
    		sbb.setFileName(rs.getString(3));
    		sbb.setIsFolder(rs.getInt(6));
    		sbb.setFolderPath(rs.getString(4));
    		allList.add(sbb);
    		
    		
    	}
		return allList;
		
		
	}
	
	
	
	
	
	
	
	public boolean SkydriveLoginInsert(SkydriveLoginBean slb) throws SQLException
	{
		String add_skydriveUser="insert into skydrivemaster(UserName,Uid,AccessToken,RefreshToken) values(?,?,?,?)";
		PreparedStatement psmt=con.prepareStatement(add_skydriveUser);
		psmt.setString(1,slb.getUsername());
		psmt.setString(2,slb.getSkydriveUid());
		psmt.setString(3,slb.getAccessToken());
		psmt.setString(4,slb.getRefreshToken());
		
		psmt.executeUpdate();
		con.commit();
		return true;
	}
	
	
	public String getAccessToken(String Uid) throws SQLException
	{
		String accessToken="";
		String get_Token="Select AccessToken from skydrivemaster where Uid="+Uid;
    	
    	Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(get_Token);
    	while(rs.next())
    	{
    		accessToken=rs.getString(1);
    	}
		
		
		return accessToken;
	}
	
	public void SkydriveDataInsert(int id,int userid,String filename,String filefolderid,int parentfolder,int isfolder) throws SQLException
	{
		String add_dropboxData="insert into skydrivedata(SkyId,SkydriveId,FileName,FileFolderId,ParentFolder,isFolder) values(?,?,?,?,?,?)";
		PreparedStatement psmt=con.prepareStatement(add_dropboxData);
		psmt.setInt(1,id);
		psmt.setInt(2,userid);
		psmt.setString(3,filename);
		psmt.setString(4,filefolderid);
		psmt.setInt(5,parentfolder);
		psmt.setInt(6,isfolder);
		
        psmt.executeUpdate();
		con.commit();
		
		
	}
	
	public int getUserId(String uid) throws SQLException
	{
		
	    int _id=0;
		String getId="Select SkydriveId from skydrivemaster where Uid="+uid;
		Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(getId);
    	while(rs.next())
    	{
    		_id=rs.getInt(1);
    	}
		
		return _id;
	}
	
    public int maxId() throws SQLException
    {
    	String get_max="Select max(SkyId) from skydrivedata";
    	int max = 0;
    	Statement st=con.createStatement();
    	ResultSet rs=st.executeQuery(get_max);
    	while(rs.next())
    	{
    		max=rs.getInt(1);
    	}
		return max;
    }
    
    public void InsertData(String AccessToken,String FolderId,int UserId,int _id) throws SQLException
    {
    	
    	if(_id!=0)
    	{
    		 parent_id =0;
    	}
    	else
    	{
    		parent_id=_id;
    	}
    	 client = Client.create();
         webResource = client.resource("https://apis.live.net/v5.0/"+FolderId +"?access_token="+AccessToken);
         response = webResource.accept("application/json").get(ClientResponse.class);
    	int i;
    	if (response.getStatus() != 200)
    	{
    	throw new RuntimeException("Failed : HTTP error code :"+ response.getStatus());
    	}

     JSONObject output = response.getEntity(JSONObject.class);
     JSONArray data = (JSONArray) output.get("data");
     
     for(i=0;i<((CharSequence) data).length();i++)
		{
			JSONObject data1=(JSONObject) data.get(i);
			
			if(((ResultSet) data1).getString("type").equalsIgnoreCase("folder"))
			{
				
				SkydriveDataInsert(++_id,UserId, data1.get("name").toString(),data1.get("id").toString(),parent_id,1);
				InsertData(AccessToken,data1.get("id").toString(),UserId,++_id);
			}
			else
			{
				SkydriveDataInsert(++_id,UserId,data1.get("name").toString(),data1.get("id").toString(),parent_id,0);
			}
    	}
     }
   public boolean UploadToSkydrive() 
   {
	return false;
    }
   public boolean DownloadFromSkydrive()
   {
	return false;
	   
	   
   }
	   
   }
    
    
    
  
