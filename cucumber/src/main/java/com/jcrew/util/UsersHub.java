package com.jcrew.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersHub {
	
	private final StateHolder stateHolder = StateHolder.getInstance();
	private final Logger logger = LoggerFactory.getLogger(UsersHub.class);
	private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();	
	private final DatabaseReader databaseReader = new DatabaseReader(); 
	private static final PropertyReader propertyReader = PropertyReader.getPropertyReader();
    Connection conn;    
    String environment = propertyReader.getProperty("environment").toLowerCase(); 

	private Connection getDBConnection() throws SQLException, ClassNotFoundException{
		if(conn==null){
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@" + dbReader.getProperty("jcms.server.name") + ":" + dbReader.getProperty("jcms.server.port") + "/" + dbReader.getProperty("jcms.server.servicename");
			
			Properties props = new Properties();
			props.setProperty("user", dbReader.getProperty("jcms.server.user"));
			props.setProperty("password", dbReader.getProperty("jcms.server.pwd"));
			props.setProperty("ssl", "true");
			
			conn = databaseReader.createConnection(url, props);
			if(conn!=null){
				logger.debug("DB connection is successful...");
			}
		}
		
		return conn; 		
	}
	
	private ResultSet executeSQLQuery(String sqlQuery) throws SQLException, ClassNotFoundException{
		
		Statement statement = databaseReader.createTheStatement(getDBConnection());
		ResultSet rs = statement.executeQuery(sqlQuery);
		
		if(rs!=null){
			logger.debug("SQL query is executed successfully...");
		}
		
		return rs;
	}
	
	private int getAvailableUsersCount() throws SQLException, ClassNotFoundException{	
				
		String getUsersCountSQLQuery = "select count(*) from JCINT2_CUSTOM.SIDECARQAUSERS where Environment='"  + environment + "' and Allocation = 'N'";		
		ResultSet rs = executeSQLQuery(getUsersCountSQLQuery);
		int numOfRecords = 0;
		if(rs!=null){
			while(rs.next()){
			  numOfRecords = Integer.parseInt(rs.getString(1));
			  break;
			}
		}
			
		logger.info("# of users currently available for '{}' environment: {}", environment.toLowerCase(), numOfRecords);
		
		return numOfRecords;
	}
	
	public void retrieveUserCredentialsFromDBAndStoreInMap() throws SQLException, ClassNotFoundException{
		if(getAvailableUsersCount() > 0){
			String getUserCredentialsSQLQuery = "select username, userpassword from JCINT2_CUSTOM.SIDECARQAUSERS where Environment='"  + environment + "' and Allocation = 'N'";
			ResultSet rs = executeSQLQuery(getUserCredentialsSQLQuery);
			if(rs!=null){
				while(rs.next()){
					stateHolder.put("sidecarusername", rs.getString(1));
					logger.info("Current available username for '{}' environment: {}", environment, rs.getString(1));
					stateHolder.put("sidecaruserpassword", rs.getString(2));
					break;
				}
			}
			
			String updateAllocationFlagSQLQuery = "update JCINT2_CUSTOM.SIDECARQAUSERS set allocation = 'Y' where username='" + stateHolder.get("sidecarusername") + "' and Environment='"  + environment + "'";
			executeSQLQuery(updateAllocationFlagSQLQuery);
		}
		else{
			logger.error("No username records are available for '" + environment + "' environment");
			throw new SQLException("No username records are available for '" + environment + "' environment");
		}
		
		closeDBConnection();
	}
	
	public void releaseUserCredentials() throws SQLException, ClassNotFoundException{
		String currentUserName = (String) stateHolder.get("sidecarusername");
		String updateAllocationFlagSQLQuery = "update JCINT2_CUSTOM.SIDECARQAUSERS set allocation = 'N' where username='" + currentUserName + "' and environment='"  + environment + "'";
		executeSQLQuery(updateAllocationFlagSQLQuery);
		logger.info("User '{}' is released in DB for '{}' environment" , currentUserName, environment);
		closeDBConnection();
	}
	
	private void closeDBConnection(){
		databaseReader.closeConnection(conn);
		logger.info("DB connection is closed...");
	}
}