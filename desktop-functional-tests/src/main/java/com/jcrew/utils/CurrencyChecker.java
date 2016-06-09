package com.jcrew.utils;

import com.jcrew.pojo.Country;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nadiapaolagarcia on 4/20/16.
 */
public class CurrencyChecker {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyChecker.class);

    public static boolean listPrice(String currency, String price) {
        Pattern pattern = Pattern.compile(currency + "\\p{Space}*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);

        return matcher.matches();
    }

    public static boolean wasPrice(String currency, String price) {
        price = price.toLowerCase();
        currency = currency.toLowerCase();

        Pattern pattern = Pattern.compile("was\\p{Space}*" + currency + "\\p{Space}*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);

        return matcher.matches();
    }

    public static boolean nowPrice(String currency, String price) {
        price = price.toLowerCase();
        currency = currency.toLowerCase();

        Pattern pattern = Pattern.compile("now\\p{Space}*" + currency + "\\p{Space}*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);

        return matcher.matches();
    }

    public static boolean selectColorPrice(String currency, String price) {
        price = price.toLowerCase();
        currency = currency.toLowerCase();

        Pattern pattern = Pattern.compile("select colors\\p{Space}*" + currency + "\\p{Space}*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);

        return matcher.matches();
    }

    public static boolean selectColorRangePrice(String currency, String price) {
        price = price.toLowerCase();
        currency = currency.toLowerCase();

        Pattern pattern = Pattern.compile("select colors\\p{Space}*" + currency + "\\p{Space}*\\d+\\.\\d{2}–"
                + currency + "\\p{Space}*\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);

        return matcher.matches();
    }

    public static boolean anyPriceType(String currency, String price) {
        boolean result = listPrice(currency, price);
        result |= wasPrice(currency, price);
        result |= nowPrice(currency, price);
        result |= selectColorPrice(currency, price);
        result |= selectColorRangePrice(currency, price);

        return result;
    }

    public static boolean anyPriceSaleType(String currency, String price) {
        boolean result = nowPrice(currency, price);
        result |= selectColorPrice(currency, price);
        result |= selectColorRangePrice(currency, price);

        return result;
    }

    public static boolean validatePrices(List<WebElement> prices, Country c) {
        boolean result = true;

        if (prices.isEmpty()) {
            logger.debug("Item Price  count not found for product details page");
            result = true;
        } else {
            for (WebElement price : prices) {
                String priceText = price.getText();

                if(priceText.contains("X")) {
                    int index = priceText.indexOf("X");
                    priceText= priceText.substring(0,index-1);
                }

                if (!priceText.isEmpty()) {
                    result &= isValid(priceText, c);
                }
            }
        }

        return result;
    }

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

    private static String cleanPrice(String price) {
        price = price.toLowerCase();
        price = price.replace("was", "");
        price = price.replace("now", "");
        price = price.replace("select colors", "");

        return price.toUpperCase();
    }


}
