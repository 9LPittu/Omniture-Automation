package com.jcrew.pojo;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.TestDataReader;

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
    	TestDataReader dataReader = TestDataReader.getTestDataReader();

        this.country = country;
        this.currency = dataReader.getData("currency");
        this.contexturl = Boolean.parseBoolean(dataReader.getData("contexturl"));

        this.countryName = dataReader.getData("name");
        this.region = dataReader.getData("region");
        this.address1 = dataReader.getData("address.line1");
        this.address2 = dataReader.getData("address.line2");
        this.zipcode = dataReader.getData("address.zipcode");
        this.city = dataReader.getData("address.city");
        this.state = dataReader.getData("address.state");

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