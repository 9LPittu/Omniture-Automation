package com.jcrew.pojo;

import com.jcrew.util.PropertyReader;

/**
 * Created by nadiapaolagarcia on 4/6/16.
 */
public class Country {

    private String country;
    private String currency;
    private boolean contexturl;

    public Country(String country) {
        PropertyReader properties = PropertyReader.getPropertyReader();

        this.country = country;
        this.currency = properties.getProperty(country + ".currency");
        this.contexturl = Boolean.parseBoolean(properties.getProperty(country + ".contexturl"));
    }

    public String getCountry() {
        return country;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isContexturl() {
        return contexturl;
    }

    public String toString() {
        return country;
    }

}
