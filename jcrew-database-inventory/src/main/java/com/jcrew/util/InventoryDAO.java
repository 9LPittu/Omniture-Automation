package com.jcrew.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class InventoryDAO {

    public static final String UPDATE_BACKORDER_ITEM = "Update JCBRNQA_STORE.jc_web_inventory set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = 'C8972GY66892'";


    public void addInventory(Properties propertyReader, Properties databaseReader) throws SQLException, ClassNotFoundException, IOException {

        String tablename = propertyReader.getProperty("tablename");
        String strBackOrderQuery = updateBackOrderItem(tablename,propertyReader.getProperty("backorderitem"));
        String strparticularSoldOutQuery = updateParticularColorSizeSoldOutItem(tablename,propertyReader.getProperty("particularcolorsizesoldoutitem"));
        String strSoldOutQuery = updateSoldOutItem(tablename,propertyReader.getProperty("soldoutitem"));
        String strShipWarningMessageQuery = updateNotAvailableToShip(tablename,propertyReader.getProperty("notavailabletoshipitem"));
        String strPreOrderQuery = updatePreOrderItem(tablename,propertyReader.getProperty("preorderitem"));
        String strVPSOutOfStockQuery = updateVPSOutOfStockItem(tablename,propertyReader.getProperty("VPSoutofstockitem"));
        String strFinalSaleQuery = updateFinalSaleItem(tablename,propertyReader.getProperty("finalsaleitem"));
        String strItemWithOnlyOneVariationQuery = updateItemWithOnlyOneVariation(tablename,propertyReader.getProperty("itemwithonlyonevariation"));
        String strItemWithOnlyOneSaleSkuQuery = updateItemWithOnlyOneSaleSku(tablename,propertyReader.getProperty("itemwithonlyonesalesku"));
        String strItemWithMoreThanOneSku = updateItemWithMoreThanOneSku(tablename,propertyReader.getProperty("itemwithmorethanonesku"));
        String strItemWithMultipleColorsFullSku = updateItemWithMultipleColorsFullSku(tablename,propertyReader.getProperty("itemwithmultiplecolorsfullsku"));
        String strItemWithMultipleColorsSaleSku = updateItemWithMultipleColorsSaleSku(tablename,propertyReader.getProperty("itemwithmultiplecolorssalesku"));
        Connection conn = getConnectionToDatabase(databaseReader);
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

    public String updateBackOrderItem(String tablename,String backOrderItem) {
         String strBackOrderQuery = "Update "+tablename+" set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 50, sellable_oo_qty = 10 , BR_FLAG = 'Y' where variant = '"+backOrderItem+"'";
         return strBackOrderQuery;
    }

    public String  updateParticularColorSizeSoldOutItem(String tablename,String particularSoldOutItem) {
        String strParticularSoldOutItemQuery = "Update "+tablename+" set sellable_oh_qty = 1, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where variant = '"+particularSoldOutItem+"'";
        return strParticularSoldOutItemQuery;
    }

    public String updateSoldOutItem(String tablename,String soldOutItem) {
        String strSoldOutItemQuery ="Update "+tablename+" set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product ='"+soldOutItem+"'";
        return strSoldOutItemQuery;
    }

    public String updateNotAvailableToShip(String tablename, String notAvailableToShipItem) {
        String strNotAvailableToShipQuery = "update "+tablename+" set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty = 20, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' where variant = '"+notAvailableToShipItem+"'";
        return strNotAvailableToShipQuery;
    }

    public String updatePreOrderItem(String tablename,String preOrderItem)  {
        String preOrderItemQuery = "update "+tablename+" set sellable_oh_qty = 0, sellable_oh_rtl = 0 , sellable_br_qty = 0, sellable_oo_qty  = 10, br_flag = 'Y', po_number = '123456', po_date = '15-Dec-15' product =  '"+preOrderItem+"'";
        return preOrderItemQuery;
    }

    public String updateVPSOutOfStockItem(String tablename,String VPSItem) {
        String VPSItemQuery = "update "+tablename+" set sellable_oh_qty = 0, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+VPSItem+"'";
        return VPSItemQuery;
    }

    public String updateFinalSaleItem(String tablename,String finalSaleItem) {
        String finalSaleItemQuery = "update "+tablename+" set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '" + finalSaleItem + "'";
        return finalSaleItemQuery;
    }

    public String updateItemWithOnlyOneVariation(String tablename,String itemWithOnlyOneVariation) {
        String itemWithOnlyOneVariationQuery = "update "+tablename+" set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithOnlyOneVariation+"'";
        return itemWithOnlyOneVariationQuery;
    }

    public String updateItemWithOnlyOneSaleSku(String tablename,String itemWithOnlyOneSaleSku) {
        String itemWithOnlyOneSaleSkuQuery = "update "+tablename+" set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product ='"+itemWithOnlyOneSaleSku+"'";
        return itemWithOnlyOneSaleSkuQuery;
    }

    public String  updateItemWithMoreThanOneSku(String tablename,String itemWithMoreThanOneSku) {
        String itemWithMoreThanOneSkuQuery = "update "+tablename+" set sellable_oh_qty = 20, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMoreThanOneSku+"'";
        return itemWithMoreThanOneSkuQuery;
    }

    public String updateItemWithMultipleColorsFullSku(String tablename,String itemWithMultipleColorsFullSku)  {

        String itemWithMultipleColorsFullSkuQuery = "update "+tablename+" set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMultipleColorsFullSku+"'";
        return itemWithMultipleColorsFullSkuQuery;
    }

    public String updateItemWithMultipleColorsSaleSku(String tablename,String itemWithMultipleColorsSaleSku) {
        String itemWithMultipleColorsSaleSkuQuery = "update "+tablename+"  set sellable_oh_qty = 50, sellable_oh_rtl = 0, sellable_br_qty = 0, sellable_oo_qty = 0 where product = '"+itemWithMultipleColorsSaleSku+"'";
        return itemWithMultipleColorsSaleSkuQuery;
    }

    public Connection getConnectionToDatabase(Properties databaseReader) throws ClassNotFoundException, SQLException, IOException {
            Class.forName("oracle.jdbc.driver.OracleDriver");



            String url = "jdbc:oracle:thin:@" + databaseReader.getProperty("db.server.name") + ":1521/" + databaseReader.getProperty("db.server.servicename");
            Properties props = new Properties();

            props.setProperty("user", databaseReader.getProperty("db.server.user"));
            props.setProperty("password", databaseReader.getProperty("db.server.pwd"));
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