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
    
    private String countryCode;
    private String countryName;
    private String region;
    private String companyName;
    private String address1;
    private String address2;
    private String zipcode;
    private String city;
    private String state;

    public Country(String environment, String country) {
        PropertyReader properties = PropertyReader.getPropertyReader();

        this.country = country;
        this.currency = properties.getProperty(country + ".currency");
        this.contexturl = Boolean.parseBoolean(properties.getProperty(country + ".contexturl"));

        this.countryName = properties.getProperty(country + ".fullname");
        this.region = properties.getProperty(country + ".region");
        this.companyName = properties.getProperty(country + ".companyname");
        this.address1 = properties.getProperty(country + ".address1");
        this.address2 = properties.getProperty(country + ".address2");
        this.zipcode = properties.getProperty(country + ".zipcode");
        this.city = properties.getProperty(country + ".city");
        this.state = properties.getProperty(country + ".state");

        if("us".equalsIgnoreCase(country)){
            this.homeurl = environment;
        } else {
            this.homeurl = environment + country + "/";
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

    public String getCountryName() {
        return countryName;
    }
    
    public String getRegion() {
        return region;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public String getAddress1() {
        return address1;
    }
    
    public String getAddress2() {
        return address2;
    }
    
    public String getZipCode() {
        return zipcode;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
}