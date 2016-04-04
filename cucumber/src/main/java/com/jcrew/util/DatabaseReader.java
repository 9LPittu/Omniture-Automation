package com.jcrew.util;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.When;

public class DatabaseReader {
	
	private final StateHolder stateHolder = StateHolder.getInstance();
	public static Map<String,String> dbResultsMap = new HashMap<>();
	public static Map<String,Boolean> dbFeedResultsMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);
	
	public Connection getConnectionToDatabase(Properties databaseReader) throws ClassNotFoundException, SQLException, IOException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        
        String url = "jdbc:oracle:thin:@" + databaseReader.getProperty("db.server.name") + ":1521/" + databaseReader.getProperty("db.server.servicename");
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
	    
	    @When("^user executes ([^\"]*) sql query in DB$")
	    public void executeSQLQueriesAndStoreInMap(String dbquery) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
	    	try{
	    	if(!dbResultsMap.isEmpty()){
	    		dbResultsMap.clear();
	    	}
	    	
	    	String database = System.getProperty("database", "jcdpdatabase");
	    	String databaseFile = database + ".properties";
	    	
	    	Properties dbProperties = new Properties();
	    	dbProperties.load(new FileReader(databaseFile));
	    	
	    	Connection conn = getConnectionToDatabase(dbProperties);
	    	if(conn!=null){
	    		logger.info("DB connection is successful...");
	    	}
	    	
	    	Statement stmt = createTheStatement(conn);
	    	ResultSet rs =stmt.executeQuery(dbProperties.getProperty("db." + dbquery));
	    	//ResultSet rs = executeQuery(stmt,dbProperties.getProperty("db." + dbquery));
	    	
	    	int cntr = 1;
	    	if(rs != null ){
	    		logger.info("SQL query is executed successfully...");
	    		while(rs.next()){
	    			dbResultsMap.put("megaUser" + cntr, rs.getString(1));
	    			dbResultsMap.put("url" + cntr, rs.getString(2));
	    			dbResultsMap.put("megaProduct" + cntr, rs.getString(3));				
	    			logger.info(rs.getString(1) +  "           "  + rs.getString(2) +  "           "  +  rs.getString(3));
	    			cntr++;
	    		}
	    		if(dbResultsMap.isEmpty()){
	    			stateHolder.put("EmptyResults",true);
	    			
	    		}
	    		else{
	    			stateHolder.put("EmptyResults",false);
	    			stateHolder.put("dbresults", dbResultsMap);
	    		}
	    	}	
	    	else{
	    		stateHolder.put("EmptyResults",true);
	    		}
	    			
	    /*	if(dbResultsMap.isEmpty()){
	    		stateHolder.put("EmptyResults",true);
	    		//logger.error("No results are retrieved when '{}' SQL query is run. Please check the SQL query once.", dbquery);
	    		//throw new SQLException("No results are retrieved when SQL query is run. Please check the SQL query once.");
	    		
	    	}
	    	else{
	    		stateHolder.put("EmptyResults",false);
	    		stateHolder.put("dbresults", dbResultsMap);
	    	}
	    */	
	    	conn.close();
	    	}
	    	
	    	catch(Exception e){
	    		stateHolder.put("EmptyResults",true);
	    		logger.error("No results are retrieved when '{}' SQL query is run. Please check the SQL query once." + e.getMessage(), dbquery );
	    		throw new SQLException("No results are retrieved when SQL query is run. Please check the SQL query once." + e.getMessage());
	    		
	    	}
	    }
	    
	    
	    
	    @When("^user executes feed validation ([^\"]*) sql in DB$")
	    public void executeFeedSQLQueriesAndValidatePutInMap(String dbquery) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
	    	
	   	    	
	    	String database = System.getProperty("database", "jcdpdatabase");
	    	String databaseFile = database + ".properties";
	    	
	    	Properties dbProperties = new Properties();
	    	dbProperties.load(new FileReader(databaseFile));
	    	
	    	try{	
	    		Connection conn = getConnectionToDatabase(dbProperties);	
	    		if(conn!=null){
	    			logger.info("DB connection is successful...");
	    		}
	    	   	Statement stmt = createTheStatement(conn);
	    		ResultSet rs = stmt.executeQuery(dbProperties.getProperty("db." + dbquery)); 
	    	   	//ResultSet rs = executeQuery(stmt,dbProperties.getProperty("db." + dbquery));
	    	   	if(rs != null && rs.next()){
	    	   		stateHolder.put("EmptyResult", false);
	    	   	}
	    	   	else {
	    		
	    	   		stateHolder.put("EmptyResult", true);
	    	   	}
	    	   		conn.close();
	    	 	}
	    	
	    	 	catch(Exception e){
	    		 
	    	 		stateHolder.put("EmptyResult", false);
	    	 		logger.error("Error occured while execute database query" + e.getMessage());
	    	 		assertTrue("Error occured while execute database query" + e.getMessage(),false );
	    	 	}
	        	
	    }
}