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
import com.google.gson.Gson;


@WebServlet("/FolderClickServlet")
public class FolderClickServlet extends HttpServlet 
{
	DropboxModel dm=new DropboxModel();
	private static final long serialVersionUID = 1L;
       
    
    public FolderClickServlet() 
    {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
       int parent_id=Integer.parseInt(request.getParameter("parentId"));
        try {
			ArrayList<DropboxDataBean> list=dm.GetDataForList(id, parent_id);
			String json = new Gson().toJson(list);
			
			response.getWriter().write(json);
			
		} 
        catch (SQLException e)
        {
			
			e.printStackTrace();
		}
        
       
       
		
		
		
	}

}
