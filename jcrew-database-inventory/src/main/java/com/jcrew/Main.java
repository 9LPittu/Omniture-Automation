package com.jcrew;

import com.jcrew.util.InventoryDAO;

import java.io.IOException;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        InventoryDAO inventoryDAO = new InventoryDAO();
        //System.out.println(inventoryDAO.addInventoryToBackOrderedItem());
        System.out.println(inventoryDAO.addInventory());
        //System.out.println(inventoryDAO.addInventoryTomAKE iTEMItem());

    }
}
