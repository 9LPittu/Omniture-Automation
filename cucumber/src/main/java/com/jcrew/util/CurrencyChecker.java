package com.jcrew.util;

import com.jcrew.pojo.Country;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.String;

import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.NumberFormat;
/**
 * Created by nadiapaolagarcia on 4/28/16.
 */
public class CurrencyChecker {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyChecker.class);

    private static boolean isValid(String price, Country country) {

        if(price.equalsIgnoreCase("free")) {
            return true;
        }

        String regex = "";

        switch (country.getCountry().toLowerCase()) {
            case "jp":
                //currency, no decimals, no thousands
                regex = "\\s*" + country.getCurrency() + "\\s*\\d*(–"
                        + country.getCurrency() + "\\s*\\d*)?";
                break;
            case "hk":
                //currency, no decimals, thousands
                regex = "\\s*" + country.getCurrency() + "\\s*(\\d*\\,)?\\d{1,3}(–"
                        + country.getCurrency() + "\\s*(\\d*\\,)?\\d{1,3})?";
                break;
            case "au":
            case "sg":
            case "ch":
            case "uk":
            case "ca":
                //currency, decimals, no thousands
                regex = "\\s*" + country.getCurrency() + "\\s*\\d*\\.\\d{2}(–"
                        + country.getCurrency() + "\\s*\\d*\\.\\d{2})?";
                break;
            case "us":
                //currency, decimals, thousands
                regex = "\\s*" + country.getCurrency() + "\\s*(\\d*\\,)?\\d{1,3}\\.\\d{2}(–"
                        + country.getCurrency() + "\\s*(\\d*\\,)?\\d{1,3}\\.\\d{2})?";
                break;
            case "de":
                //currency, decimals with comma, thousands with period
                regex = "\\s*" + country.getCurrency() + "\\s*(\\d*\\.)?\\d{1,3}\\,\\d{2}(–"
                        + country.getCurrency() + "\\s*(\\d*\\.)?\\d{1,3}\\,\\d{2})?";
                break;
        }

        price = cleanPrice(price);
        boolean matches = Pattern.matches(regex, price);

        if (!matches)
            logger.debug("price: {} regex: {}", price, regex);

        return matches;
    }

    public static boolean validatePrices(List<WebElement> prices, Country c) {
        boolean result = true;

        if (prices.isEmpty()) {
            logger.debug("Item Price  count not found for product details page");
            result = true;
        } else {
            for (WebElement price : prices) {
                String priceText = price.getText();
                if(!priceText.isEmpty()){                	
	                String[] pricesArray = priceText.split("\\p{Pd}");
	                for(int i=0;i<pricesArray.length;i++){
	                	if (!pricesArray[i].isEmpty()) {
	                        result &= isValid(pricesArray[i].trim(), c);
	                    }
	                }
                }
            }
        }

        return result;
    }

    private static String cleanPrice(String price) {
        price = price.toLowerCase();
        price = price.replace("was", "");
        price = price.replace("now", "");
        price = price.replace("select colors", "");

        return price.toUpperCase();
    }

    public static String convertToCurrency(String price, Country country) {
        String countryname = country.getCountry().toLowerCase();
        switch (countryname) {
            case "us":
            case "hk":
            case "de":
                String numberValue = "";
                String decimalValue = "";
                String formattedNumber = "";
                if (price.contains(".")) {
                    numberValue = price.split(".")[0];
                    decimalValue = price.split(".")[1];
                } else {
                    numberValue = price;
                }
                Double numberToBeFormatted = Double.parseDouble(numberValue);
                if (countryname == "de") {
                    formattedNumber = NumberFormat.getInstance(Locale.GERMAN).format(numberToBeFormatted);
                    if (decimalValue != "")
                        formattedNumber = formattedNumber + "," + decimalValue;
                } else {
                    formattedNumber = NumberFormat.getInstance(Locale.US).format(numberToBeFormatted);
                    if (decimalValue != "")
                        formattedNumber = formattedNumber + "." + decimalValue;
                }
                return formattedNumber;
            default:
                return price;
        }
    }

}
