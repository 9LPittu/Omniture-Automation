package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
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

}
