package com.jcrew.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InventoryDAO {


    public static final String UPDATE_BACKORDER_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = 'C8972GY66892'";
    public static final String UPDATE_PARTICULAR_COLOR_SIZE_SOLDOUT_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 1, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = 'E3029ST14778'";
    public static final String UPDATE_SOLDOUT_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'e4308%'";
    public static final String UPDATE_NOT_AVAILABLE_TO_SHIP_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty = 20, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant = 'B0517WN29172'";
    public static final String UPDATE_PRE_ORDER_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty  = 10, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant like 'C9220%'";
    public static final String UPDATE_OUT_OF_STOCK_VPS_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like '18040%'";
    public static final String UPDATE_FINAL_SALE_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'B9447%'";
    public static final String UPDATE_ITEM_WITH_ONLY_ONE_VARIATION = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = ''";
    public static final String UPDATE_ITEM_WITH_ONLY_ONE_SALE_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = ''";
    public static final String UPDATE_ITEM_WITH_MORE_THAN_ONE_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = ''";
    public static final String UPDATE_ITEM_WITH_MULTIPLE_COLORS_FULL_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = ''";
    public static final String UPDATE_ITEM_WITH_MULTIPLE_COLORS_SALE_SKU = "update JCBRNQA_STORE.jc_web_inventory  set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = ''";
    public static final String UPDATE_ITEM_TEST_DATA = "update JCBRNQA_STORE.jc_web_inventory  set sellable_oh_qty = 30, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant LIKE  'E0786EF2854s'";

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

    public int addInventory() throws SQLException, ClassNotFoundException, IOException {
       // String strQuery = UPDATE_BACKORDER_ITEM;
       // String strQuery = UPDATE_PARTICULAR_COLOR_SIZE_SOLDOUT_ITEM;
       // String strQuery = UPDATE_SOLDOUT_ITEM;
       // String strQuery = UPDATE_NOT_AVAILABLE_TO_SHIP_ITEM;
       // String strQuery = UPDATE_PRE_ORDER_ITEM;
        //String strQuery = UPDATE_OUT_OF_STOCK_VPS_ITEM;
       // String strQuery = UPDATE_FINAL_SALE_ITEM;
        //String strQuery = UPDATE_ITEM_WITH_ONLY_ONE_VARIATION;
        String strQuery = UPDATE_ITEM_TEST_DATA;
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

