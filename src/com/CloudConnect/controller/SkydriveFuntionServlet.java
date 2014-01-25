package com.CloudConnect.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.CloudConnect.bean.SkydriveDataBean;
import com.CloudConnect.model.SkydriveModel;
import com.google.gson.Gson;


@WebServlet("/SkydriveFuntionServlet")
public class SkydriveFuntionServlet extends HttpServlet
{
	SkydriveModel sm=new SkydriveModel();
	private static final long serialVersionUID = 1L;
       
    
    public SkydriveFuntionServlet() 
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
        
        
        String Funtion_Type=request.getParameter("request_type");
        String id=request.getParameter("uId");
        try
        {
        if(Funtion_Type.equalsIgnoreCase("GetUserData"))
        {
        	 int parent_id=Integer.parseInt(request.getParameter("parentId"));
        	 ArrayList<SkydriveDataBean> list=sm.GetDataForList(id, parent_id);
        	 String json = new Gson().toJson(list);
 			
 			response.getWriter().write(json);
        }
        else if(Funtion_Type.equalsIgnoreCase("GetSearchData"))
        {
        	String filename=request.getParameter("FileName");
        	ArrayList<SkydriveDataBean> search_data=sm.getSearchData(id, filename);
             String json1 = new Gson().toJson(search_data);
 			
 			response.getWriter().write(json1);
        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
		
	}

}
