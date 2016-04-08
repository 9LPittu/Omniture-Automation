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
import com.jcrew.util.DatabasePropertyReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.When;

public class DatabaseReader {

	private final StateHolder stateHolder = StateHolder.getInstance();
	public static Map<String,String> dbResultsMap = new HashMap<>();
	public static Map<String,Boolean> dbFeedResultsMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);
	private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();

	public Connection getConnectionToDatabase() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@" + dbReader.getProperty("db.server.name") + ":1521/" + dbReader.getProperty("db.server.servicename");
		Properties props = new Properties();

		props.setProperty("user", dbReader.getProperty("db.server.user"));
		props.setProperty("password", dbReader.getProperty("db.server.pwd"));
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

			Connection conn = getConnectionToDatabase();
			if(conn!=null){
				logger.info("DB connection is successful...");
			}

			Statement stmt = createTheStatement(conn);
			ResultSet rs =stmt.executeQuery(dbReader.getProperty("db." + dbquery));

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
			closeConnection(conn);
		}

		catch(Exception e){
			stateHolder.put("EmptyResults",true);
			logger.error("No results are retrieved when '{}' SQL query is run. Please check the SQL query once." + e.getMessage(), dbquery );
			throw new SQLException("No results are retrieved when SQL query is run. Please check the SQL query once." + e.getMessage());

		}
	}



	@When("^user executes feed validation ([^\"]*) sql in DB$")
	public void executeFeedSQLQueriesAndValidatePutInMap(String dbquery) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{
		try{
			Connection conn = getConnectionToDatabase();
			if(conn!=null){
				logger.info("DB connection is successful...");
			}
			Statement stmt = createTheStatement(conn);
			ResultSet rs = stmt.executeQuery(dbReader.getProperty("db." + dbquery));
			if(rs != null && rs.next()){
				stateHolder.put("EmptyResult", false);
			}
			else {
				stateHolder.put("EmptyResult", true);
			}
			closeConnection(conn);
		}

		catch(Exception e){
			stateHolder.put("EmptyResult", false);
			logger.error("Error occured while execute database query" + e.getMessage());
			assertTrue("Error occured while execute database query" + e.getMessage(),false );
		}

	}

	public String executeSQLQueryAgainstDB(String dbquery) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException{

		Connection conn = getConnectionToDatabase();
		if(conn!=null){
			logger.info("DB connection is successful...");
		}
		Statement stmt = createTheStatement(conn);
		ResultSet rs = stmt.executeQuery(dbReader.getProperty("db." + dbquery));

		//capture item name from result set
		String item = "";
		if(rs!=null){
			rs.next();
			item = rs.getString(1);
		}
		else{
			throw new SQLException("No records are returned after " + dbquery + " is run. Check the SQL query once.");
		}

		closeConnection(conn);

		if(item.isEmpty()){
			throw new SQLException("No records are returned after " + dbquery + " is run. Check the SQL query once.");
		}

		return item;
	}
}