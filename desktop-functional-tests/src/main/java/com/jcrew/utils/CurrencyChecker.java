package com.jcrew.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by nadiapaolagarcia on 4/20/16.
 */
public class CurrencyChecker {

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
}
