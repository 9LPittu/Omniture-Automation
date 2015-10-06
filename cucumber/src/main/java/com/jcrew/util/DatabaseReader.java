package com.jcrew.util;
import java.sql.*;
import java.util.Properties;

public class DatabaseReader {
	
	
	public static ResultSet GetData(String strQuery){ 
		
		try{  
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			
			final PropertyReader propertyReader = PropertyReader.getPropertyReader();		
			//step2 create  the connection object  
			//Connection con=DriverManager.getConnection(  
			//"jdbc:oracle:thin:@jdc1-scan-01.jcrew.com:1521/jcud1","qatester","qat3st");  
			 
			//step3 create the statement object  
			 
			String url = "jdbc:oracle:thin:@" + propertyReader.getProperty("db.server.name") + ":1521/" + propertyReader.getProperty("db.server.servicename");
			Properties props = new Properties();
						
			props.setProperty("user",propertyReader.getProperty("db.server.user"));
			props.setProperty("password",propertyReader.getProperty("db.server.pwd"));
			props.setProperty("ssl","true");
			Connection con = DriverManager.getConnection(url, props);
			
			
			Statement stmt=con.createStatement(); 
			
			//step4 execute query  
			ResultSet rs=stmt.executeQuery(strQuery);  
			//while(rs.next())  
			//System.out.println(rs.getString(1));  
			 
			//step5 close the connection object  
			 
			return rs;
		//	con.close(); 
			}catch(Exception e){ System.out.println(e);}  
				return null;
			}  
		
			
	}

