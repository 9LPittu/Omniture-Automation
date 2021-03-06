package com.jcrew.pojo;

import com.jcrew.utils.CountryReader;

/**
 * Created by nadiapaolagarcia on 4/11/16.
 */
public class Country {
    private String country;
    private String currency;
    private String homeurl;
    private String name;
    private String region;
    private boolean contexturl;
    
    private String countryName;
    private String companyName;
    private String address1;
    private String address2;
    private String zipcode;
    private String city;
    private String state;

    private CountryReader dataReader;

    public Country(String environment, String country) {
        country = country.toLowerCase();
        dataReader = new CountryReader(country);

        this.country = country;               
        this.currency = dataReader.getData("currency");
        this.contexturl = Boolean.parseBoolean(dataReader.getData("contexturl"));
        this.name = dataReader.getData("name");        
        this.countryName = dataReader.getData("name");
        this.region=dataReader.getData("region"); 
        this.homeurl = environment;

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

    public String getName() {
        return name;
    }

    public String getRegion() { 
    	return region; 
    }
    
    public String getCountryName() {
        return countryName;
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
