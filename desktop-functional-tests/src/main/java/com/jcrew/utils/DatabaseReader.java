package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Properties;

/**
 * Created by 9msyed on 8/3/2016.
 */
public class DatabaseReader {

    private final StateHolder stateHolder = StateHolder.getInstance();
    public static Map<String, String> dbResultsMap = new HashMap<>();
    public static Map<String, Boolean> dbFeedResultsMap = new HashMap<>();
    private final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);
    private final DatabasePropertyReader dbReader = DatabasePropertyReader.getPropertyReader();
    private final String dbEnvironment = dbReader.getProperty("dbEnvironment");

    public Connection getConnectionToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        String url = "jdbc:oracle:thin:@" + dbReader.getProperty(dbEnvironment + ".server.name") + ":" + dbReader.getProperty(dbEnvironment + ".server.port") + "/" + dbReader.getProperty(dbEnvironment + ".server.servicename");
        Properties props = new Properties();

        props.setProperty("user", dbReader.getProperty(dbEnvironment + ".server.user"));
        props.setProperty("password", dbReader.getProperty(dbEnvironment + ".server.pwd"));
        props.setProperty("ssl", "true");

        return createConnection(url, props);
    }

    Connection createConnection(String url, Properties props) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public Statement createTheStatement(Connection conn) {
        try {
            return conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet executeQuery(Statement statement, String strQuery) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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