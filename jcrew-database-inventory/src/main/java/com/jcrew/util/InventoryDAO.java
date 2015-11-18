package com.jcrew.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InventoryDAO {

    public static final String UPDATE_BACKORDER_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = 'C8972GY66892'";

    public int addInventoryToBackOrderedItem() throws SQLException, ClassNotFoundException, IOException {
        Connection conn = getConnectionToDatabase();
        Statement statement = createTheStatement(conn);
        int rowsUpdated = executeQueryToAddInventory(statement, UPDATE_BACKORDER_ITEM);
        closeConnection(conn);
        return rowsUpdated;
    }

    public void addInventory(Properties propertyReader) throws SQLException, ClassNotFoundException, IOException {

        String strBackOrderQuery = updateBackOrderItem(propertyReader.getProperty("backorderitem"));
        String strparticularSoldOutQuery = updateParticularColorSizeSoldOutItem(propertyReader.getProperty("particularcolorsizesoldoutitem"));
        String strSoldOutQuery = updateSoldOutItem(propertyReader.getProperty("soldoutitem"));
        String strShipWarningMessageQuery = updateNotAvailableToShip(propertyReader.getProperty("notavailabletoshipitem"));
        String strPreOrderQuery = updatePreOrderItem(propertyReader.getProperty("preorderitem"));
        String strVPSOutOfStockQuery = updateVPSOutOfStockItem(propertyReader.getProperty("VPSoutofstockitem"));
        String strFinalSaleQuery = updateFinalSaleItem(propertyReader.getProperty("finalsaleitem"));
        String strItemWithOnlyOneVariationQuery = updateItemWithOnlyOneVariation(propertyReader.getProperty("itemwithonlyonevariation"));
        String strItemWithOnlyOneSaleSkuQuery = updateItemWithOnlyOneSaleSku(propertyReader.getProperty("itemwithonlyonesalesku"));
        String strItemWithMoreThanOneSku = updateItemWithMoreThanOneSku(propertyReader.getProperty("itemwithmorethanonesku"));
        String strItemWithMultipleColorsFullSku = updateItemWithMultipleColorsFullSku(propertyReader.getProperty("itemwithmultiplecolorsfullsku"));
        String strItemWithMultipleColorsSaleSku = updateItemWithMultipleColorsSaleSku(propertyReader.getProperty("itemwithmultiplecolorssalesku"));
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

    public String updatePreOrderItem(String preOrderItem)  {
        String preOrderItemQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty  = 10, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' product =  '"+preOrderItem+"'";
        return preOrderItemQuery;
    }

    public String updateVPSOutOfStockItem(String VPSItem) {
        String VPSItemQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+VPSItem+"'";
        return VPSItemQuery;
    }

    public String updateFinalSaleItem(String finalSaleItem) {
        String finalSaleItemQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '" + finalSaleItem + "'";
        return finalSaleItemQuery;
    }

    public String updateItemWithOnlyOneVariation(String itemWithOnlyOneVariation) {
        String itemWithOnlyOneVariationQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithOnlyOneVariation+"'";
        return itemWithOnlyOneVariationQuery;
    }

    public String updateItemWithOnlyOneSaleSku(String itemWithOnlyOneSaleSku) {
        String itemWithOnlyOneSaleSkuQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product ='"+itemWithOnlyOneSaleSku+"'";
        return itemWithOnlyOneSaleSkuQuery;
    }

    public String  updateItemWithMoreThanOneSku(String itemWithMoreThanOneSku) {
        String itemWithMoreThanOneSkuQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMoreThanOneSku+"'";
        return itemWithMoreThanOneSkuQuery;
    }

    public String updateItemWithMultipleColorsFullSku(String itemWithMultipleColorsFullSku)  {

        String itemWithMultipleColorsFullSkuQuery = "update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMultipleColorsFullSku+"'";
        return itemWithMultipleColorsFullSkuQuery;
    }

    public String updateItemWithMultipleColorsSaleSku(String itemWithMultipleColorsSaleSku) {
        String itemWithMultipleColorsSaleSkuQuery = "update JCBRNQA_STORE.jc_web_inventory  set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMultipleColorsSaleSku+"'";
        return itemWithMultipleColorsSaleSkuQuery;
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