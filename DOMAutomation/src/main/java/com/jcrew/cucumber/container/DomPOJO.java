package com.jcrew.cucumber.container;

import java.util.HashMap;
import java.util.Map;

public class DomPOJO {
    private static String doNum = "";
    private static String orderId = "";
    private static String itemId = "";
    private static String orderType = "";
    private static Map <String, String> storeDos = new HashMap<>();
    private static Map <String, String> ldcDos =new HashMap<>();
    private static Map <String, String> itemsInOrder =new HashMap<>();
    private static Map <String, String> destinationFacilities =new HashMap<>();
    private static Map <String, String> newDo =new HashMap<>();

    public static Map<String, String> getLdcDos() {
        return ldcDos;
    }

   /* public static void setLdcDos(Map<String, String> ldcDos) {
        DomPOJO.ldcDos = ldcDos;
    }*/

    public static Map<String, String> getStoreDos() {
        return storeDos;
    }
    
    public static Map<String, String> getDestinationFacilities() {
        return destinationFacilities;
    }
    
    public static Map<String, String> getNewDo() {
        return newDo;
    }

    
   /* public static void setStoreDos(Map<String, String> storeDos) {
        DomPOJO.storeDos = storeDos;
    }*/

    public static String getDoNum() {
        return doNum;
    }

    public static String getOrderId() {
       return DomPOJO.orderId;
    }

    public static void setOrderId(String orderId) {
        DomPOJO.orderId = orderId;
    }

    public static void setDoNum(String doNum) {
        DomPOJO.doNum = doNum;
    }

    public static String getItemId() {
        return itemId;
    }

    public static void setItemId(String itemId) {
        DomPOJO.itemId = itemId;
    }

    public static Map<String, String> getItemsInOrder() {
        return itemsInOrder;
    }

    public static String getOrderType() {
        return orderType;
    }

    public static void setOrderType(String orderType) {
        DomPOJO.orderType = orderType;
    }

    public static void setItemsInOrder(Map<String, String> itemsInOrder) {
        DomPOJO.itemsInOrder = itemsInOrder;
    }

    public static void cleanPojo(){
        doNum="";
        orderId= "";
        itemId="";
        getStoreDos().clear();
        getLdcDos().clear();
        itemsInOrder.clear();
    }
}
