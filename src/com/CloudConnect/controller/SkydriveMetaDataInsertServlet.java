package com.CloudConnect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CloudConnect.bean.DropboxDataBean;
import com.CloudConnect.bean.SkydriveDataBean;
import com.CloudConnect.model.SkydriveModel;


@WebServlet("/SkydriveMetaDataInsertServlet")
public class SkydriveMetaDataInsertServlet extends HttpServlet
{
	SkydriveModel sm=new SkydriveModel();
	private static final long serialVersionUID = 1L;
       
    
    public SkydriveMetaDataInsertServlet()
    {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	
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
        
        int maxId=0;
        SkydriveDataBean sdb=new SkydriveDataBean();
        sdb.setUserId(Integer.parseInt(request.getParameter("UserId")));
        sdb.setFileName(request.getParameter("FileName"));
        sdb.setFolderPath(request.getParameter("FolderPath"));
        sdb.setIsFolder(Integer.parseInt(request.getParameter("isFolder")));
        sdb.setParentFolder(Integer.parseInt("ParentFolder"));
        
	/*	try {
			//boolean res=sm.SkydriveDataInsert(sdb);
			if(res==true)
			{
				System.out.print("inserted");
				maxId=sm.maxId();
			}
				else
			System.out.print("not inserted");	
		
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}*/
        }
}
