package com.jcrew.util;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.java.en.When;

public class DatabaseReader {

	private final StateHolder stateHolder = StateHolder.getInstance();
	public static Map<String,String> dbResultsMap = new HashMap<>();
	public static Map<String,Boolean> dbFeedResultsMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);
	private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
	private final String dbEnvironment = dbReader.getProperty("dbEnvironment");

	public Connection getConnectionToDatabase() throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@" + dbReader.getProperty(dbEnvironment+".server.name") + ":" + dbReader.getProperty(dbEnvironment+".server.port") + "/" + dbReader.getProperty(dbEnvironment+".server.servicename");
		Properties props = new Properties();

		props.setProperty("user", dbReader.getProperty(dbEnvironment+".server.user"));
		props.setProperty("password", dbReader.getProperty(dbEnvironment+".server.pwd"));
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
			ResultSet rs =stmt.executeQuery(dbReader.getProperty(dbEnvironment+ "." + dbquery));

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
			ResultSet rs = stmt.executeQuery(dbReader.getProperty(dbEnvironment+"." + dbquery));
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
		ResultSet rs = stmt.executeQuery(dbReader.getProperty(dbEnvironment+"." + dbquery));

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

	public List<String> getConditionalShippingMethods(boolean overnightShipping, boolean saturdayShipping) {

		try {
			List<String> shipMethods = new ArrayList<String>();
			String dbquery = "";

			if (overnightShipping && saturdayShipping)
				dbquery = dbReader.getProperty("conditionalShippingMethod.overnight") + " UNION ALL " + dbReader.getProperty("conditionalShippingMethod.saturday");
			else if (overnightShipping)
				dbquery = dbReader.getProperty("conditionalShippingMethod.overnight");
			else if (saturdayShipping)
				dbquery = dbReader.getProperty("conditionalShippingMethod.saturday");

			dbquery = dbquery.replaceAll("schema", dbReader.getProperty("schema"));

			//Establish DB connection and execute query
			Connection conn = getConnectionToDatabase();
			if (conn != null) {
				logger.info("DB connection is successful...");
			}
			Statement stmt = createTheStatement(conn);
			ResultSet rs = stmt.executeQuery(dbquery);

			//Retrieve shipping methods
			if (rs != null) {
				while (rs.next()) {
					shipMethods.add(rs.getString(1));
				}
			}

			closeConnection(conn);
			return shipMethods;
		} catch (Exception e) {
			logger.error("Unable to run query for retrieving day and time specific shipping methods from database");
			return null;
		}
	}


	public List<Date> getATPStartAndEndDate(String carrier, String carrierCode){
		try {
			String schema = dbReader.getProperty("schema");
			String dbQuery = dbReader.getProperty("atp.dates.query");
			dbQuery = dbQuery.replaceAll("schema",schema);
			dbQuery = dbQuery.replaceAll("carriercode",carrierCode);
			dbQuery = dbQuery.replaceAll("carriername",carrier);

			List<Date> dateRange = new ArrayList<Date>();
			Date startDate;
			Date endDate;

			//Establish DB connection
			Connection conn = getConnectionToDatabase();
			if (conn != null) {
				logger.info("DB connection is successful...");
			}
			Statement stmt = createTheStatement(conn);

			//Retrieve Min and Max Days
			ResultSet rsDateRange = stmt.executeQuery(dbQuery);
			if (rsDateRange != null) {
				while (rsDateRange.next()) {
					dateRange.add(rsDateRange.getDate(1));
				}
			}
			closeConnection(conn);
			return dateRange;
		} catch (Exception e) {
			logger.error("Unable to run query for retrieving Start and End date the shipping method {}", carrierCode);
			return null;
		}

	}
}