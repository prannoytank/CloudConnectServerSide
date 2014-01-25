package com.CloudConnect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CloudConnect.bean.DropboxLoginBean;
import com.CloudConnect.bean.SkydriveLoginBean;
import com.CloudConnect.model.DropboxModel;
import com.CloudConnect.model.SkydriveModel;


@WebServlet("/SkydriveLoginServlet")
public class SkydriveLoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       String folderid="me/skydrive";
    
    public SkydriveLoginServlet()
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
        
        SkydriveLoginBean slb=new SkydriveLoginBean();
        slb.setUsername(request.getParameter("username"));
        slb.setSkydriveUid(request.getParameter("uid"));
        slb.setAccessToken(request.getParameter("Access"));
        slb.setRefreshToken(request.getParameter("Refresh"));
        
        SkydriveModel sm=new SkydriveModel();
		try 
		{
			boolean result=sm.SkydriveLoginInsert(slb);
			if(result==true)
			{
				
			System.out.print("Skydrive Inserted");
			String accessToken=sm.getAccessToken(request.getParameter("uid"));
			
			int UserId=sm.getUserId(request.getParameter("uid"));
			int fileid=sm.maxId();
			
			sm.InsertData(accessToken,folderid, UserId,fileid);
			
			}
			else
				System.out.print("Skydrive error");
			
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
