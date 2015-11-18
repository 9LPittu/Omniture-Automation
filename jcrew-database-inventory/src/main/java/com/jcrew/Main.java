package com.jcrew;

import com.jcrew.util.InventoryDAO;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;


public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        final Properties propertyReader = new Properties();
        propertyReader.load(new FileReader(args[0] + "QAtestdata.properties"));

        inventoryDAO.addInventory(propertyReader);
    }
}
