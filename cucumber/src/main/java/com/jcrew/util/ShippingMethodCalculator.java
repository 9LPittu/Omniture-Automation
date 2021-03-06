package com.jcrew.util;

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
    private boolean monogram;

    public ShippingMethodCalculator() {

        addressType = (String) (stateHolder.get("atpAddressType"));
        try {
            restrictedAddress = !addressType.equalsIgnoreCase("regular");
        } catch (NullPointerException nullException) {
            restrictedAddress=false;
        }

        List<Product> productsInBag = (List<Product>) stateHolder.get("toBag");
        crewCut=true;
        mixedItems = false;
        Product p = productsInBag.get(0);
        restrictedItem = p.isBackorder();
        crewCut = crewCut && p.isCrewCut();
        monogram = p.hasMonogram();

        for (int i = 1; i < productsInBag.size(); i++) {
            p = productsInBag.get(i);
            mixedItems = mixedItems || (p.isBackorder() != restrictedItem);
            restrictedItem = p.isBackorder();
            crewCut = crewCut && p.isCrewCut();
            monogram |= p.hasMonogram();
        }
        environment = propertyReader.getProperty("environment");
        toggle = dataReader.getBoolean(environment + ".atp.toggle");
        logger.debug("This bag has backordered products: {} - This bag has mixed products: {}", restrictedItem, mixedItems);
    }



    public List<ShippingMethod> getExpectedList() {
        List<ShippingMethod> expectedMethods = new ArrayList<>();
        List<String> shipMethods = getExpectedShipMethods();

        if ((restrictedItem || mixedItems || monogram) && shipMethods.contains("saturday"))
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
            String text = "";
            Date startDate=null;
            Date endDate=null;
            if (isATP) {
                name = dataReader.getData(method + ".atp.name");
                List<Date> expectedDate = getATPDateRange(method);
                startDate = expectedDate.get(0);
                endDate = expectedDate.get(1);
            } else {
                name = dataReader.getData(method + ".nonatp.name");
            }
            String price = getPrice(method);
            if (!addressType.equalsIgnoreCase("pobox") && !addressType.equalsIgnoreCase("hawaii")){
            	 text = dataReader.getData(method + ".text");
            }
            
            expectedMethods.add(new ShippingMethod(name, price, text, startDate, endDate ));

        }
        return expectedMethods;
    }

    public String getPrice(String method) {
        String price = dataReader.getData(method + ".price");

        double subtotal = Double.parseDouble((String) stateHolder.get("subtotal"));

        String promotionalShippingDetails = dataReader.getData(environment + "." + method + ".promotional.shipping.charge");
        if (promotionalShippingDetails.contains(";")) {
            String promotionalShippingCharge = promotionalShippingDetails.split(";")[0];
            String promotionalShippingThreshold = promotionalShippingDetails.split(";")[1];

            try {
                Double dblShippingThreshold = Double.parseDouble(promotionalShippingThreshold);
                if (subtotal >= dblShippingThreshold)
                    price = promotionalShippingCharge;
            } catch (NumberFormatException numberException) {
                logger.info("no promotional shipping charge for the shipping method {}",method);
            }
        }


        String freeCrewCutMethods[] = dataReader.getDataArray(addressType + ".crewcut.freemethods");
        List<String> listFreeCrewCuttMethods = Arrays.asList(freeCrewCutMethods);

        if(crewCut && listFreeCrewCuttMethods.contains(method) ) {
            return "free";
        } else {
            return price;
        }

    }

    public List<Date> getATPDateRange(String method) {
        String carrier = dataReader.getData(method + ".carrier.name");
        String carrierCode = dataReader.getData(method + ".carrier.code");

        DatabaseReader dbReader = new DatabaseReader();
        List <Date> startAndEndDates = dbReader.getATPStartAndEndDate(carrier,carrierCode);
        return startAndEndDates;
    }

    
}