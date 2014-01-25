package com.CloudConnect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CloudConnect.bean.DropboxDataBean;
import com.CloudConnect.model.DropboxModel;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.WebAuthSession;
import com.dropbox.client2.session.Session.AccessType;
import com.google.gson.Gson;


@WebServlet("/DropboxMetaDataInsertServlet")
public class DropboxMetaDataInsertServlet extends HttpServlet
{
	DropboxModel dm=new DropboxModel();
	private static final long serialVersionUID = 1L;
       
   
    public DropboxMetaDataInsertServlet()
    {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Max-Age", "86400");
     
        String id=request.getParameter("uId");
       String filename=request.getParameter("FileName");
        try {
			ArrayList<DropboxDataBean> list=dm.getSearchData(id, filename);
			String json = new Gson().toJson(list);
			
			response.getWriter().write(json);
			
		} 
        catch (SQLException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
