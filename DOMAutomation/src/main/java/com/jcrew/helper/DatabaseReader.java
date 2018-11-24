package com.jcrew.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcrew.cucumber.util.E2EPropertyReader;

import java.sql.*;
import java.util.Properties;

public class DatabaseReader {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseReader.class);
    protected static E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
    private static Connection getConnectionToDatabase() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        /*String url = "jdbc:oracle:thin:@" + dbReader.getProperty(dbEnvironment + ".server.name") + ":" +
                dbReader.getProperty(dbEnvironment + ".server.port") + "/" +
                dbReader.getProperty(dbEnvironment + ".server.servicename");*/
        String url ="jdbc:oracle:thin:@jx03-scan.jcrew.com:1521/entsq";
        Properties props = new Properties();
        if(e2ePropertyReader.getProperty("env").equalsIgnoreCase("Steel")) {
        props.setProperty("user", "ESDOMQ3");
        props.setProperty("password", "ESDOMQ3");
        props.setProperty("ssl", "true");
        }else if(e2ePropertyReader.getProperty("env").equalsIgnoreCase("Platinum")) {
            props.setProperty("user", "ESDOMQ5");
            props.setProperty("password", "ESDOMQ4");
            props.setProperty("ssl", "true");
            }
        return createConnection(url, props);
    }

    public static Connection createConnection(String url, Properties props) {
        Connection conn = null;

        try {
            System.out.println("************************ DB URL:::::::"+url);

            conn = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Statement createTheStatement(Connection conn) {
        try {
            return conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet executeQuery(Statement statement, String strQuery) {
        try {
            ResultSet rs = statement.executeQuery(strQuery);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getShippingId(String orderId) {
        String shipId= null;
        try {
            //Establish DB connection
            Connection conn = getConnectionToDatabase();
            if (conn != null) {
                logger.info("DB connection is successful...");
                Statement stmt = createTheStatement(conn);

                //Retrieve Min and Max Days
                ResultSet resultSet = executeQuery(stmt, "select TC_ORDER_ID, TC_SHIPMENT_ID from LPN where TC_ORDER_ID ='"+orderId+"'");
                resultSet.next();
                shipId = resultSet.getString("TC_SHIPMENT_ID");
                closeConnection(conn);
                return shipId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String loadToCustomPeople() {
        String shipId= null;
        try {
            //Establish DB connection
            Connection conn = getConnectionToDatabase();
            if (conn != null) {
                logger.info("DB connection is successful...");
                Statement stmt = createTheStatement(conn);
                //Retrieve Min and Max Days
                ResultSet resultSet = executeQuery(stmt, "exec JC_ATP_STS_LOAD_SHIP_ORDER");
                resultSet.next();
                closeConnection(conn);
                return shipId;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String trackShipmentOrder(String orderId) throws Exception {
        String trackStatus= null;
       // try {
            //Establish DB connection
            Connection conn = getConnectionToDatabase();
            if (conn != null) {
                logger.info("DB connection is successful...");
                Statement stmt = createTheStatement(conn);

                //Retrieve Min and Max Days
                ResultSet resultSet = executeQuery(stmt, "select * from JC_SHIPPED_ORDER_TRACKING where ext_purchase_orders_id = '"+orderId+"'");
                resultSet.next();
                trackStatus = resultSet.getString("SHIP_TO_STR_DLVR_READ");
                closeConnection(conn);
            }
                return trackStatus;
          /*  }

        } catch (Exception e) {
            e.printStackTrace();*/
    //    }
       // return null;
    }

    /*public static void main(String[] args) {
        System.out.println(getShippingId("1400029406"));
    }*/
}