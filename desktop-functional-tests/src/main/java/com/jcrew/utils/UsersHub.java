package com.jcrew.utils;


import com.jcrew.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class UsersHub {
    private static UsersHub usersHub;
    private final Logger logger = LoggerFactory.getLogger(UsersHub.class);
    private final StateHolder stateHolder = StateHolder.getInstance();
    private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
    private final DatabaseReader databaseReader = new DatabaseReader();
    private static final PropertyReader propertyReader = PropertyReader.getPropertyReader();
    String environment = propertyReader.getProperty("environment").toLowerCase();    
    private User user = null;
    Connection conn;

    private UsersHub() {
    }

    public static UsersHub getInstance() {
        if (usersHub == null) {
            usersHub = new UsersHub();
        }
        return usersHub;
    }

    private Connection getDBConnection() {
        if (conn == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            String url = "jdbc:oracle:thin:@" + dbReader.getProperty("jcms.server.name") + ":" + dbReader.getProperty("jcms.server.port") + "/" + dbReader.getProperty("jcms.server.servicename");

            Properties props = new Properties();
            props.setProperty("user", dbReader.getProperty("jcms.server.user"));
            props.setProperty("password", dbReader.getProperty("jcms.server.pwd"));
            props.setProperty("ssl", "true");

            conn = databaseReader.createConnection(url, props);
            if (conn != null) {
                logger.debug("DB connection is successful...");
            }
        }

        return conn;
    }

    private ResultSet executeSQLQuery(String sqlQuery) throws SQLException {

        Statement statement = databaseReader.createTheStatement(getDBConnection());
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(sqlQuery);
        } catch (SQLException e) {
            throw new SQLException("Exception occurred while executing SQL query " + sqlQuery + e.getMessage());
        }

        if (rs != null) {
            logger.debug("SQL query is executed successfully...");
        }

        return rs;
    }

    private int getAvailableUsersCount(String userCategory) throws SQLException {

        ResultSet rs = executeSQLQuery(getQuery("count",userCategory));

        int numOfRecords = 0;
        if (rs != null) {
            try {
                rs.next();
                    numOfRecords = Integer.parseInt(rs.getString(1));
            } catch (Exception e) {
                throw new SQLException("Exception occurred when retrieving records count from DB..." + e.getMessage());
            }
        }
        logger.info("# of users currently available for '{}' environment: {}", environment.toLowerCase(), numOfRecords);

        return numOfRecords;
    }
    
    private int getAvailableUsersCount(String userType, String addressType) throws SQLException{
		
		String getUsersCountSQLQuery = "select count(*) from JCINT2_CUSTOM.SIDECARQAUSERS "
										+ "where brand='jcrew' and Environment='"  + environment 
										+ "' and Allocation = 'N'" + getUserAddressWhereClause(userType, addressType);
		
		ResultSet rs = null;
		rs = executeSQLQuery(getUsersCountSQLQuery);
		
		int numOfRecords = 0;
		if(rs!=null){
			try {
				rs.next();
                numOfRecords = Integer.parseInt(rs.getString(1));
			}
			catch (Exception e) {
				throw new SQLException("Exception occurred when retrieving records count from DB..." + e.getMessage());
			}
		}
			
		logger.info("# of users currently available for '{}' environment: {}", environment.toLowerCase(), numOfRecords);
		
		return numOfRecords;
	}


    public synchronized User getUser(String userCategory) throws SQLException {
    	String scenarioName = stateHolder.get("scenarioName");
    	if (getAvailableUsersCount(userCategory) > 0) {
            ResultSet rs = executeSQLQuery(getQuery("extract",userCategory));

            if (rs != null) {
                try {
                    rs.next();
                    user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                    logger.info("Current available username for scenario: {} is '{}' in the environment: {}", scenarioName, user.getEmail(), environment);
                    String updateAllocationFlagSQLQuery = "update JCINT2_CUSTOM.SIDECARQAUSERS set allocation = 'Y' where brand='jcrew' and username='" + user.getEmail() + "' and Environment='" + environment + "'";
                    executeSQLQuery(updateAllocationFlagSQLQuery);
                }catch (SQLException e){
                    throw new SQLException("Exception occurred when retrieving user credentials from DB..." + e.getMessage());
                }
            }
        } else {
            logger.error("No username records are available in DB for the scenario '{}' ",scenarioName);
            throw new SQLException("No username records are available in DB for the scenario '" + scenarioName + "'");
        }


        closeDBConnection();
        user.setUserCategory(userCategory);
        return user;
    }
    
    public synchronized User getUser(String userType, String addressType) throws SQLException{
    	String scenarioName = stateHolder.get("scenarioName");
    	if(getAvailableUsersCount(userType, addressType) > 0){
			String getUserCredentialsSQLQuery = "select username, userpassword, firstname, lastname, DEFAULT_ADDRESS_COUNTRY from JCINT2_CUSTOM.SIDECARQAUSERS "
										+ "where brand='jcrew' and Environment='"  + environment + "' and Allocation = 'N'"
										+ getUserAddressWhereClause(userType, addressType);
			
			ResultSet rs = executeSQLQuery(getUserCredentialsSQLQuery);
			if(rs!=null){
				try {
					  rs.next();
					  user = new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
					  logger.info("userType - " + userType + ", addressType - " + addressType);
					  logger.info("Current available username for scenario: {} is '{}' in the environment: {}", scenarioName, user.getEmail(), environment);
				}
				catch (SQLException e) {
					throw new SQLException("Exception occurred when retrieving user credentials from DB..." + e.getMessage());					
				}
			}
			
			String updateAllocationFlagSQLQuery = "update JCINT2_CUSTOM.SIDECARQAUSERS set allocation = 'Y' "
								+ "where brand='jcrew' and username='" + user.getEmail() + 
								"' and Environment='"  + environment + "'" + getUserAddressWhereClause(userType, addressType);
			
			executeSQLQuery(updateAllocationFlagSQLQuery);
		}
		else{
			logger.error("No username records are available in DB for the scenario '{}' ",scenarioName);
			throw new SQLException("No username records are available in DB for the scenario '" + scenarioName + "'");
		}
		closeDBConnection();
		
		return user;
	}


    private String getQuery(String queryType,String userCategory) {
        String getUserSQLQuery;
        if (queryType.equalsIgnoreCase("count")) {
            getUserSQLQuery = "select count(*) from JCINT2_CUSTOM.SIDECARQAUSERS where brand='jcrew' and Environment='" + environment + "' and Allocation = 'N'";
        }
        else {
            getUserSQLQuery = "select username, userpassword,firstname,lastname,default_address_country from JCINT2_CUSTOM.SIDECARQAUSERS where brand='jcrew' and Environment='" + environment + "' and Allocation = 'N'";
        }
        switch (userCategory) {
            case User.CAT_LOYALTY:
                getUserSQLQuery = getUserSQLQuery + " and usercategory = '"+User.CAT_LOYALTY+"'";
                break;
            case User.CAT_NO_LOYALTY:
                getUserSQLQuery = getUserSQLQuery + " and usercategory = '"+User.CAT_NO_LOYALTY+"'";
                break;
            default:
                getUserSQLQuery = getUserSQLQuery + " and usercategory is null";
                break;
        }
        return getUserSQLQuery;
    }


    public void releaseUserCredentials() {
    	
    	String currentUserName = null;
    	
    	if(stateHolder.hasKey("userObject")) {
    		logger.info("Retrieiving user details from stateholder...");
        	user =  stateHolder.get("userObject");
        	currentUserName = user.getEmail();
        }
        
        if(currentUserName!=null){
	        String updateAllocationFlagSQLQuery = "update JCINT2_CUSTOM.SIDECARQAUSERS set allocation = 'N' where brand='jcrew' and username='" + currentUserName + "' and environment='" + environment + "'";
	        try {
	            executeSQLQuery(updateAllocationFlagSQLQuery);
	
	            logger.info("User '{}' is released in DB for '{}' environment", currentUserName, environment);
	            
	            String scenarioName = stateHolder.get("scenarioName");
                if(scenarioName != null && scenarioName.contains("Checkout")){
                	logger.debug("Releasing user '{}' for scenario '{}' @ {}", currentUserName, scenarioName, new Date().toString());
                }
                
	            closeDBConnection();
	            user = null;
	        } catch (SQLException e) {
	            logger.error("Failed to release user '{}' in DB!!!", currentUserName);
	        }
        }
    }

    private void closeDBConnection() {
        databaseReader.closeConnection(conn);
        conn = null;
        logger.info("DB connection is closed...");
    }
    
    public String getUserAddressWhereClause(String userType, String addressType){
		String whereClause = "";
		
		if(!userType.isEmpty()){
			whereClause += " and " + "usertype='" + userType + "'";
		}
		
		if(!addressType.isEmpty()){
			whereClause += " and " + "addresstype='" + addressType + "'";
		}
		
		return whereClause;
	}
}
