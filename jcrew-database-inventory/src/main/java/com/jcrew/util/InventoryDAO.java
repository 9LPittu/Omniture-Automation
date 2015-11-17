package com.jcrew.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InventoryDAO {

    public static final String UPDATE_BACKORDER_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = 'C8972GY66892'";
    public static final String UPDATE_PARTICULAR_COLOR_SIZE_SOLDOUT_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 1, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = 'E3029ST14778'";
    public static final String UPDATE_SOLDOUT_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'E4308%'";
    public static final String UPDATE_NOT_AVAILABLE_TO_SHIP_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty = 20, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant = 'B0517WN29172'";
    public static final String UPDATE_PRE_ORDER_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty  = 10, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant like 'C9220%'";
    public static final String UPDATE_OUT_OF_STOCK_VPS_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like '18040%'";
    public static final String UPDATE_FINAL_SALE_ITEM = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'B9447%'";
    public static final String UPDATE_ITEM_WITH_ONLY_ONE_VARIATION = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'B8752%'";
    public static final String UPDATE_ITEM_WITH_ONLY_ONE_SALE_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'B7701%'";
    public static final String UPDATE_ITEM_WITH_MORE_THAN_ONE_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'E3835%'";
    public static final String UPDATE_ITEM_WITH_MULTIPLE_COLORS_FULL_SKU = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'C3409%'";
    public static final String UPDATE_ITEM_WITH_MULTIPLE_COLORS_SALE_SKU = "update JCBRNQA_STORE.jc_web_inventory  set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant like 'E3896%'";
    public static final String UPDATE_ITEM_TEST_DATA = "update JCBRNQA_STORE.jc_web_inventory  set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = 'E0786EF2854M'";

    public ResultSet GetData(String strQuery) {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            final Properties propertyReader = new Properties();
            String url = "jdbc:oracle:thin:@" + propertyReader.getProperty("db.server.name") + ":1521/" + propertyReader.getProperty("db.server.servicename");
            Properties props = new Properties();
            Connection con = createConnection(url, props);
            Statement stmt = con.createStatement();

            props.setProperty("user", propertyReader.getProperty("db.server.user"));
            props.setProperty("password", propertyReader.getProperty("db.server.pwd"));
            props.setProperty("ssl", "true");

            return stmt.executeQuery(strQuery);
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

    public void addInventory() throws SQLException, ClassNotFoundException, IOException {
        String strBackorderQuery = UPDATE_BACKORDER_ITEM;
        String strparticularSoldOutQuery = UPDATE_PARTICULAR_COLOR_SIZE_SOLDOUT_ITEM;
        String strSoldOutQuery = UPDATE_SOLDOUT_ITEM;
        String strShipWarningMessageQuery = UPDATE_NOT_AVAILABLE_TO_SHIP_ITEM;
        String strPreOrderQuery = UPDATE_PRE_ORDER_ITEM;
        String strVPSOutOfStockQuery = UPDATE_OUT_OF_STOCK_VPS_ITEM;
        String strFinalSaleQuery = UPDATE_FINAL_SALE_ITEM;
        String strItemWithOnlyOneVariationQuery = UPDATE_ITEM_WITH_ONLY_ONE_VARIATION;
        String strItemWithOnlyOneSaleSkuQuery = UPDATE_ITEM_WITH_ONLY_ONE_SALE_SKU;
        String strTestDataQuery = UPDATE_ITEM_TEST_DATA;
        String strItemWithMoreThanOneSku = UPDATE_ITEM_WITH_MORE_THAN_ONE_SKU;
        String strItemWithMultipleColorsFullSku = UPDATE_ITEM_WITH_MULTIPLE_COLORS_FULL_SKU;
        String strItemWithMultipleColorsSaleSku = UPDATE_ITEM_WITH_MULTIPLE_COLORS_SALE_SKU;
        Connection conn = getConnectionToDatabase();
        Statement statement = createTheStatement(conn);

        System.out.println("back order item: "+executeQueryToAddInventory(statement, strBackorderQuery));
        System.out.println("particular Sold out item: "+executeQueryToAddInventory(statement, strparticularSoldOutQuery));
        System.out.println("Sold out : "+executeQueryToAddInventory(statement, strSoldOutQuery));
        System.out.println("Ship warning: "+executeQueryToAddInventory(statement, strShipWarningMessageQuery));
        System.out.println("Pre order: "+executeQueryToAddInventory(statement, strPreOrderQuery));
        System.out.println("VPS Out of Stock: "+executeQueryToAddInventory(statement, strVPSOutOfStockQuery));
        System.out.println("Final Sale : "+executeQueryToAddInventory(statement, strFinalSaleQuery));
        System.out.println("Item with only one variation: "+executeQueryToAddInventory(statement,strItemWithOnlyOneVariationQuery));
        System.out.println("One sale sku: "+executeQueryToAddInventory(statement,strItemWithOnlyOneSaleSkuQuery));
        System.out.println("Test Data : "+executeQueryToAddInventory(statement,strTestDataQuery));
        System.out.println("Item with more than one sku: "+executeQueryToAddInventory(statement,strItemWithMoreThanOneSku));
        System.out.println("Item with multiple colors full sku: "+executeQueryToAddInventory(statement,strItemWithMultipleColorsFullSku));
        System.out.println("Item with multiple colors Sale sku: "+executeQueryToAddInventory(statement,strItemWithMultipleColorsSaleSku));

        closeConnection(conn);
    }

    public void addInventory(String[] testdata) throws SQLException, ClassNotFoundException, IOException {
        String strBackOrderQuery = updateBackOrderItem(testdata[0]);
        String strparticularSoldOutQuery = updateParticularColorSizeSoldOutItem(testdata[1]);
        String strSoldOutQuery = updateSoldOutItem(testdata[2]);
        String strShipWarningMessageQuery = updateNotAvailableToShip(testdata[3]);
        String strPreOrderQuery = UPDATE_PRE_ORDER_ITEM;
        String strVPSOutOfStockQuery = UPDATE_OUT_OF_STOCK_VPS_ITEM;
        String strFinalSaleQuery = UPDATE_FINAL_SALE_ITEM;
        String strItemWithOnlyOneVariationQuery = UPDATE_ITEM_WITH_ONLY_ONE_VARIATION;
        String strItemWithOnlyOneSaleSkuQuery = UPDATE_ITEM_WITH_ONLY_ONE_SALE_SKU;
        String strTestDataQuery = UPDATE_ITEM_TEST_DATA;
        String strItemWithMoreThanOneSku = UPDATE_ITEM_WITH_MORE_THAN_ONE_SKU;
        String strItemWithMultipleColorsFullSku = UPDATE_ITEM_WITH_MULTIPLE_COLORS_FULL_SKU;
        String strItemWithMultipleColorsSaleSku = UPDATE_ITEM_WITH_MULTIPLE_COLORS_SALE_SKU;
        Connection conn = getConnectionToDatabase();
        Statement statement = createTheStatement(conn);

        System.out.println("back order item: "+executeQueryToAddInventory(statement, strBackOrderQuery));
        System.out.println("particular Sold out item: "+executeQueryToAddInventory(statement, strparticularSoldOutQuery));
        System.out.println("Sold out : "+executeQueryToAddInventory(statement, strSoldOutQuery));
        System.out.println("Ship warning: "+executeQueryToAddInventory(statement, strShipWarningMessageQuery));
        System.out.println("Pre order: "+executeQueryToAddInventory(statement, strPreOrderQuery));
        System.out.println("VPS Out of Stock: "+executeQueryToAddInventory(statement, strVPSOutOfStockQuery));
        System.out.println("Final Sale : "+executeQueryToAddInventory(statement, strFinalSaleQuery));
        System.out.println("Item with only one variation: "+executeQueryToAddInventory(statement,strItemWithOnlyOneVariationQuery));
        System.out.println("One sale sku: "+executeQueryToAddInventory(statement,strItemWithOnlyOneSaleSkuQuery));
        System.out.println("Test Data : "+executeQueryToAddInventory(statement,strTestDataQuery));
        System.out.println("Item with more than one sku: "+executeQueryToAddInventory(statement,strItemWithMoreThanOneSku));
        System.out.println("Item with multiple colors full sku: "+executeQueryToAddInventory(statement,strItemWithMultipleColorsFullSku));
        System.out.println("Item with multiple colors Sale sku: "+executeQueryToAddInventory(statement,strItemWithMultipleColorsSaleSku));

        closeConnection(conn);
    }

    public String updateBackOrderItem(String backOrderItem) {
         String strBackOrderQuery = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = '"+backOrderItem+"'";
         return strBackOrderQuery;
    }

    public String  updateParticularColorSizeSoldOutItem(String particularSoldOutItem) {
        String strParticularSoldOutItemQuery = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 1, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = '"+particularSoldOutItem+"'";
        return strParticularSoldOutItemQuery;
    }

    public String updateSoldOutItem(String soldOutItem) {
        String strSoldOutItemQuery ="Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product ='"+soldOutItem+"'";
        return strSoldOutItemQuery;
    }

    public String updateNotAvailableToShip(String notAvailableToShipItem) {
        String strNotAvailableToShipQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty = 20, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant = '"+notAvailableToShipItem+"'";
        return strNotAvailableToShipQuery;
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

            return createConnection(url, props);
    }

    Connection createConnection(String url, Properties props) throws SQLException {
        return DriverManager.getConnection(url, props);
    }

    public Statement createTheStatement(Connection conn) {
        try {
            return conn.createStatement();
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
