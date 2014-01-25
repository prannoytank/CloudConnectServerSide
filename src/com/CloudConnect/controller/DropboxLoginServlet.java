package com.CloudConnect.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import com.CloudConnect.bean.DropboxLoginBean;
import com.CloudConnect.model.DropboxModel;

import com.dropbox.client2.exception.DropboxException;


@WebServlet("/DropboxLoginServlet")
public class DropboxLoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private String maxId;
	javax.servlet.ServletContext context=getServletContext();   
    
    public DropboxLoginServlet()
    {
        super();
      
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		    PrintWriter out = response.getWriter();
	        response.setContentType("text/html");
	        response.setHeader("Cache-control", "no-cache, no-store");
	        response.setHeader("Pragma", "no-cache");
	        response.setHeader("Expires", "-1");
	        response.setHeader("Access-Control-Allow-Origin", "*");
	        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
	        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.setHeader("Access-Control-Max-Age", "86400");
	        
	        
	        DropboxLoginBean dlb=new DropboxLoginBean();
	        dlb.setUsername(request.getParameter("UserName"));
	        dlb.setDropboxUid(request.getParameter("uid"));
	        dlb.setKey(request.getParameter("key"));
	        dlb.setSecret(request.getParameter("secret"));
	        
	        DropboxModel dlm=new DropboxModel();
	        try {
	        	
				boolean result=dlm.DropboxLoginInsert(dlb);
				if(result==true)
				{
					System.out.print("Inserted");
				  String[] storekey=dlm.getKeys(request.getParameter("uid"));
				  dlm.InsertDropboxData(storekey[0],storekey[1],storekey[2]);
				  String directoryPath =getServletContext().getRealPath("Dropbox" + "/" + request.getParameter("uid"));
					File directory = new File(directoryPath);

					if(!directory.exists()) 
					{
					    directory.mkdirs();
					}
				  
				  
				}
					else
					System.out.print("error");
			} 
	        catch (SQLException | DropboxException e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        //JSONObject id=new JSONObject();
	       // id.put("MaxId",request.getParameter("uid"));
	        //out.println(id.toString());
	        
	}


	

}
