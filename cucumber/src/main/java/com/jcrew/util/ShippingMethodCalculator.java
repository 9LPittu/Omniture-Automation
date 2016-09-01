package com.jcrew.util;

import java.util.List;


import com.jcrew.pojo.ShippingMethod;
import com.jcrew.pojo.Product;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.DatabaseReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by nadiapaolagarcia on 8/17/16.
 */
public class ShippingMethodCalculator {

    StateHolder stateHolder = StateHolder.getInstance();
    TestDataReader dataReader = TestDataReader.getTestDataReader();
    private Logger logger = LoggerFactory.getLogger(ShippingMethodCalculator.class);
    final PropertyReader propertyReader = PropertyReader.getPropertyReader();

    private boolean restrictedAddress;
    private boolean restrictedItem;
    private boolean mixedItems;
    private boolean toggle;
    private boolean crewCut;
    private String addressType;
    private String environment;

    public ShippingMethodCalculator() {
        addressType = (String) (stateHolder.get("atpAddressType"));
        restrictedAddress = !addressType.equalsIgnoreCase("regular");

        List<Product> productsInBag = (List<Product>) stateHolder.get("productList");
        crewCut=true;
        mixedItems = false;
        Product p = productsInBag.get(0);
        restrictedItem = p.isBackorder();
        crewCut = crewCut && p.isCrewCut();

        for (int i = 1; i < productsInBag.size(); i++) {
            p = productsInBag.get(i);
            mixedItems = mixedItems || (p.isBackorder() != restrictedItem);
            restrictedItem = p.isBackorder();
        }
        environment = propertyReader.getProperty("environment");
        toggle = dataReader.getBoolean(environment + ".atp.toggle");
        logger.debug("This bag has backordered products: {} - This bag has mixed products: {}", restrictedItem, mixedItems);
    }



    public List<ShippingMethod> getExpectedList() {
        List<ShippingMethod> expectedMethods = new ArrayList<>();
        List<String> shipMethods = getExpectedShipMethods();

        if ((restrictedItem || mixedItems) && shipMethods.contains("saturday"))
            shipMethods.remove("saturday");


        if ((!toggle) || restrictedAddress || (restrictedItem && !mixedItems)) {
            boolean isATP = false;
            expectedMethods = getExpectedShipMethods(shipMethods,isATP);

        } else {
            boolean isATP = true;
            expectedMethods = getExpectedShipMethods(shipMethods,isATP);
        }

        return expectedMethods;
    }


    public List<String> getExpectedShipMethods() {
        List<String> conditionalShipMethods = new ArrayList<String>();
        List<String> consolidatedShipMethods = new ArrayList<String>();

        DatabaseReader dbReader = new DatabaseReader();
        String addressType = (String) stateHolder.get("atpAddressType");

        String basicShipMethods[] = dataReader.getDataArray(addressType + ".shipping.methods");
        List<String> basicShipMethodsList = Arrays.asList(basicShipMethods);

        boolean overnightShipping = dataReader.getBoolean(addressType + ".overnight");
        boolean saturdayShipping = dataReader.getBoolean(addressType + ".saturday");

        if (overnightShipping || saturdayShipping) {
            conditionalShipMethods = dbReader.getConditionalShippingMethods(overnightShipping, saturdayShipping);
            consolidatedShipMethods.addAll(basicShipMethodsList);
            consolidatedShipMethods.addAll(conditionalShipMethods);
            Collections.reverse(consolidatedShipMethods);
            return consolidatedShipMethods;
        } else {
            Collections.reverse(basicShipMethodsList);
            return basicShipMethodsList;
        }
    }

    public List<ShippingMethod> getExpectedShipMethods(List<String> methods,boolean isATP) {
        List<ShippingMethod> expectedMethods = new ArrayList<>();

        for (String method : methods) {
            String name;
            if (isATP) {
                name = dataReader.getData(method + ".atp.name");
            } else {
                name = dataReader.getData(method + ".nonatp.name");
            }
            String price = getPrice(method);
            String text = dataReader.getData(method + ".text");
            expectedMethods.add(new ShippingMethod(name, price, text));
        }
        return expectedMethods;
    }

    public String getPrice(String method) {
        boolean implicitFreeShipping = false;
        String price = dataReader.getData(method + ".price");

        String freeCrewCutMethods[] = dataReader.getDataArray(addressType + ".crewcut.freemethods");
        List<String> listFreeCrewCuttMethods = Arrays.asList(freeCrewCutMethods);

        double subtotal = Double.parseDouble((String) stateHolder.get("subtotal"));
        String freeShippingThreshold = dataReader.getData(environment + "." + method + ".FreeShippingThreshold");

        try {
            Double dblShippingThreshold = Double.parseDouble(freeShippingThreshold);
            if (subtotal >= dblShippingThreshold)
                implicitFreeShipping = true;

        } catch (NumberFormatException numberException) {
            implicitFreeShipping = false;
        }

        if((crewCut && listFreeCrewCuttMethods.contains(method)) || implicitFreeShipping ) {
            return "free";
        } else {
            return price;
        }

    }

}