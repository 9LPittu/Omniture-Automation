package com.jcrew.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InventoryDAO {


    public static final String UPDATE_BACKORDER_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 35, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = 'B0517WN29172'";

    public ResultSet GetData(String strQuery) {

        try {
            //step1 load the driver class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            final Properties propertyReader = new Properties();
            //step2 create  the connection object
            //Connection con=DriverManager.getConnection(
            //"jdbc:oracle:thin:@jdc1-scan-01.jcrew.com:1521/jcud1","qatester","qat3st");

            //step3 create the statement object

            String url = "jdbc:oracle:thin:@" + propertyReader.getProperty("db.server.name") + ":1521/" + propertyReader.getProperty("db.server.servicename");
            Properties props = new Properties();

            props.setProperty("user", propertyReader.getProperty("db.server.user"));
            props.setProperty("password", propertyReader.getProperty("db.server.pwd"));
            props.setProperty("ssl", "true");
            Connection con = createConnection(url, props);


            Statement stmt = con.createStatement();

            //step4 execute query
            ResultSet rs = stmt.executeQuery(strQuery);
            //while(rs.next())
            //System.out.println(rs.getString(1));

            //step5 close the connection object

            return rs;
            //	con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public int addInventoryToBackOrderedItem() throws SQLException, ClassNotFoundException, IOException {
        String strQuery = UPDATE_BACKORDER_ITEM;
        Connection conn = getConnectionToDatabase();
        Statement statement = createTheStatement(conn);
        int rowsUpdated = executeQueryToAddInventory(statement, strQuery);
         closeConnection(conn);
        return rowsUpdated;
    }


    public Connection getConnectionToDatabase() throws ClassNotFoundException, SQLException, IOException {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            final Properties propertyReader = new Properties();
            propertyReader.load(new FileReader("database.properties"));

            String url = "jdbc:oracle:thin:@" + propertyReader.getProperty("db.server.name") + ":1521/" + propertyReader.getProperty("db.server.servicename");
            Properties props = new Properties();

            props.setProperty("user", propertyReader.getProperty("db.server.user"));
            props.setProperty("password", propertyReader.getProperty("db.server.pwd"));
            props.setProperty("ssl", "true");

            Connection con = createConnection(url, props);
            //Statement stmt = con.createStatement();
            return con;
    }

    Connection createConnection(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    public Statement createTheStatement(Connection conn) {
        try {
            Statement statement = conn.createStatement();
            return statement;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public int executeQueryToAddInventory(Statement statement,String strQuery) {
        try {
           return statement.executeUpdate(strQuery);
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public void closeConnection(Connection conn) {
        try {
            conn.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }

}

