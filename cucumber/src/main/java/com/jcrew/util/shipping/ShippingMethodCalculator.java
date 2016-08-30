package com.jcrew.util.shipping;

import java.util.List;


import com.jcrew.pojo.ShippingMethod;
import com.jcrew.util.StateHolder;
import com.jcrew.util.TestDataReader;
import com.jcrew.util.DatabaseReader;
import java.io.IOException;
import java.sql.SQLException;

import java.util.*;

/**
 * Created by nadiapaolagarcia on 8/17/16.
 */
public class ShippingMethodCalculator {

    private final StateHolder stateHolder = StateHolder.getInstance();
    TestDataReader dataReader = TestDataReader.getTestDataReader();


    public List<String> getExpectedShipMethods() throws IOException, ClassNotFoundException, SQLException {
        List<String> conditionalShipMethods = new ArrayList<String>();
        List<String> consolidatedShipMethods = new ArrayList<String>();

        DatabaseReader dbReader = new DatabaseReader();
        String addressType = (String) stateHolder.get("addresstype");

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

    public String getExpectedDefaultShippingMethod() throws IOException, ClassNotFoundException, SQLException {
        double subtotal = Double.parseDouble((String) stateHolder.get("subtotal"));
        List<String> expectedShipMethods = getExpectedShipMethods();

        String addressType = (String) stateHolder.get("addresstype");
        String defaultShipMethod = dataReader.getData(addressType + ".default.shipping.method");

        for (String shipMethod:expectedShipMethods) {
            String freeShippingThreshold = dataReader.getData(shipMethod + ".FreeShippingThreshold");
            try {
                Double dblShippingThreshold = Double.parseDouble(freeShippingThreshold);
                if (subtotal >= dblShippingThreshold) {
                    defaultShipMethod = shipMethod;
                    break;
                }

            } catch (NumberFormatException numberException) {
            }
        }
        return defaultShipMethod;
    }

}