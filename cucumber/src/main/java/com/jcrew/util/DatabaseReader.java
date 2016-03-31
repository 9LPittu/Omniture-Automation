package com.jcrew.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseReader {
	
	public Connection getConnectionToDatabase(Properties databaseReader) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        String url = "jdbc:oracle:thin:@" + databaseReader.getProperty("db.server.name") + ":" + databaseReader.getProperty("db.server.port") + "/" + databaseReader.getProperty("db.server.servicename");
        Properties props = new Properties();

        props.setProperty("user", databaseReader.getProperty("db.server.user"));
        props.setProperty("password", databaseReader.getProperty("db.server.pwd"));
        props.setProperty("ssl", "true");

        return createConnection(url, props);
}

		Connection createConnection(String url, Properties props) throws SQLException {
		    return DriverManager.getConnection(url, props);
		}

		public Statement createTheStatement(Connection conn) {
		    try {
		        return conn.createStatement();
		    } catch (Exception e) {
		    	e.printStackTrace();		        
		    }
		    return null;
		}
		
		public ResultSet executeQuery(Statement statement,String strQuery) {
	        try {
	        	ResultSet rs = statement.executeQuery(strQuery); 
	            return rs;
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	        return null;
	    }

	    public void closeConnection(Connection conn) {
	        try {
	            conn.close();
	        }catch (Exception e) {
	        	e.printStackTrace();
	        }
	    }
	    
	    public String executeSQLQueryAgainstDB(String sqlQueryPropertyName) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
	    	
	    	String dbConfigFile = null;	    	
	    	
	    	switch(Util.getEnvironmentName()){
		    	case "production":
		    		dbConfigFile = System.getProperty("user.dir") + "//dbconfig//productiondatabase.properties";
		    		break;		    	
		    	case "goldqa":
		    		dbConfigFile = System.getProperty("user.dir") + "//dbconfig//goldqadatabase.properties";
		    		break;
	    	}
	    	
	    	//read the SQL query
	    	Properties dbProperties = new Properties();
	    	dbProperties.load(new FileReader(dbConfigFile));	    	
	    	String sqlQuery =dbProperties.getProperty(sqlQueryPropertyName);	    	
	    	
	    	//establish DB connection and execute the SQL query
	    	Connection conn = getConnectionToDatabase(dbProperties);
	    	Statement stmt = createTheStatement(conn);
	    	ResultSet rs = executeQuery(stmt,sqlQuery);
	    	
	    	//capture the first row data from result set
	    	String item = "";
	    	if(rs!=null){
	    		while(rs.next()){
	    			item = rs.getString(1);
	    			break;
	    		}
	    	}
	    	else{
	    		throw new SQLException("No records are returned after " + sqlQueryPropertyName + " is run. Check the SQL query once.");
	    	}
	    	
	    	//close the connection to DB
	    	closeConnection(conn);
	    	
	    	if(item.isEmpty()){
	    		throw new SQLException("No records are returned after " + sqlQueryPropertyName + " is run. Check the SQL query once.");
	    	}
	    	
	    	return item;
	    }
}