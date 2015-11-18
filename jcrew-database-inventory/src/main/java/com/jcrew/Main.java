package com.jcrew;

import com.jcrew.util.InventoryDAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        InventoryDAO inventoryDAO = new InventoryDAO();

        String[] steeltestdata = new String[15];
        final Properties propertyReader = new Properties();
        propertyReader.load(new FileReader("steelQAtestdata.properties"));
        steeltestdata[0] = propertyReader.getProperty("backorderitem");
        steeltestdata[1] = propertyReader.getProperty("particularcolorsizesoldoutitem");
        steeltestdata[2] = propertyReader.getProperty("soldoutitem");
        steeltestdata[3] = propertyReader.getProperty("notavailabletoshipitem");
        steeltestdata[4] = propertyReader.getProperty("preorderitem");
        steeltestdata[5] = propertyReader.getProperty("VPSoutofstockitem");
        steeltestdata[6] = propertyReader.getProperty("finalsaleitem");
        steeltestdata[7] = propertyReader.getProperty("itemwithonlyonevariation");
        steeltestdata[8] = propertyReader.getProperty("itemwithonlyonesalesku");
        steeltestdata[9] = propertyReader.getProperty("itemwithmorethanonesku");
        steeltestdata[10] = propertyReader.getProperty("itemwithmultiplecolorsfullsku");
        steeltestdata[11] = propertyReader.getProperty("itemwithmultiplecolorssalesku");
        String[] bronzetestdata = {};

        if(args[0].equals("steel"))
                            inventoryDAO.addInventory(steeltestdata);
        else {
            if(args[0].equals("bronze"))
                inventoryDAO.addInventory(bronzetestdata);

        }

    }
}
