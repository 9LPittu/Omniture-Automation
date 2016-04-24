package com.jcrew.pojo;

import com.jcrew.util.PropertyReader;
import com.jcrew.util.TestDataReader;

/**
 * Created by nadiapaolagarcia on 4/6/16.
 */
public class Country {

    private String country;
    private String homeurl;
    private boolean contexturl;
    private String countryCode;
    private String countryName;
    private String currency;
    private String region;
    private String companyName;
    private String address1;
    private String address2;
    private String zipcode;
    private String city;
    private String state;


    public Country(String environment, String country) {
        PropertyReader properties = PropertyReader.getPropertyReader();
        TestDataReader testData = TestDataReader.getTestDataReader();

        this.countryName = testData.getData(countryCode + ".fullname");
        this.currency = testData.getData(countryCode + ".currency");
        this.region = testData.getData(countryCode + ".region");
        this.companyName = testData.getData(countryCode + ".companyname");
        this.address1 = testData.getData(countryCode + ".address1");
        this.address2 = testData.getData(countryCode + ".address2");
        this.zipcode = testData.getData(countryCode + ".zipcode");
        this.city = testData.getData(countryCode + ".city");
        this.state = testData.getData(countryCode + ".state");

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



    public String getCountryCode() {
        return countryCode;
    }
    
    public String getCountryName() {
        return countryName;
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