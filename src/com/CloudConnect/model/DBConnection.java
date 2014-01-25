package com.CloudConnect.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	private static Connection con;
	private static Statement st;
	private static ResultSet rs;
	public static synchronized Connection getConnection() {
		if (con == null) {
			try {

				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/cloudconnect?autoReconnect=true","root","bhuv");
				con.setAutoCommit(false);
			} catch (ClassNotFoundException ex) {
				System.out.println("Class not found in DBConnection:-"
						+ ex.getMessage());
			} catch (SQLException ex) {
				System.out.println("Sql Exception in DBConnection:- "
						+ ex.getMessage());

			}

		}
		return con;
	}
	public static int executeupdate(String query) throws Exception
	{
		System.out.println("new database update");
		Connection con=DBConnection.getConnection();
		st=con.createStatement();
		int i=0;
		i=st.executeUpdate(query);
		if(i==1)
		{
			con.commit();
		}
		st.close();
		return i;
		
	}
	public static ResultSet executequery(String query) throws Exception
	{
		Connection con=DBConnection.getConnection();
		st=con.createStatement();
		rs=st.executeQuery(query);
		return rs;
		
	}
	public static void main(String [] args) throws SQLException {
		Connection cn=getConnection();
		//Savepoint svpoint=cn.setSavepoint("savepoint");
		   Statement stmt= con.createStatement();
		   stmt.execute("insert into Login(emailid,password,roleid)values ('tejas@gmail.com','sheth',1);");
		   cn.commit();
		  
		   
		System.out.print("Tejas");
	}
}