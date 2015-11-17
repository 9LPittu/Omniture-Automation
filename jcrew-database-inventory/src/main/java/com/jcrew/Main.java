package com.jcrew;

import com.jcrew.util.InventoryDAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        //System.out.println(inventoryDAO.addInventoryToBackOrderedItem());
        String environment = args[0];

        int env;
       //String[] steeltestdata = {"C8972GY66892","E3029ST14778"};
        String[] steeltestdata = new String[15];
        final Properties propertyReader = new Properties();
        propertyReader.load(new FileReader("steelQAtestdata.properties"));
        steeltestdata[0] = propertyReader.getProperty("backorderitem");
        steeltestdata[1] = propertyReader.getProperty("particularcolorsizesoldoutitem");
        steeltestdata[2] = propertyReader.getProperty("soldoutitem");
        steeltestdata[3] = propertyReader.getProperty("notavailabletoshipitem");

        String[] bronzetestdata = {};
       // inventoryDAO.addInventory();
        //if(environment == "steel") env = 0;
         // else env = 1;
        switch(args[0]) {
            case "steel":
                inventoryDAO.addInventory(steeltestdata);
                break;
            case "bronze":
                inventoryDAO.addInventory(bronzetestdata);
                break;
        }

    }
}
