package com.jcrew.pojo;

import com.jcrew.util.PropertyReader;

/**
 * Created by nadiapaolagarcia on 4/6/16.
 */
public class Country {

    private String country;
    private String currency;
    private String homeurl;
    private boolean contexturl;

    public Country(String environment, String country) {
        PropertyReader properties = PropertyReader.getPropertyReader();

        this.country = country;
        this.currency = properties.getProperty(country + ".currency");
        this.contexturl = Boolean.parseBoolean(properties.getProperty(country + ".contexturl"));

        if("us".equalsIgnoreCase(country)){
            this.homeurl = environment;
        } else {
            this.homeurl = environment + "/" + country + "/";
        }
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

    public String getHomeurl() {
        return homeurl;
    }
}
