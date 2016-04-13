package com.jcrew.pojo;

import com.jcrew.util.TestDataReader;

public class Country {

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
    
    public Country(String countryCode) {
    	
        TestDataReader testData = TestDataReader.getTestDataReader();

        this.countryCode = countryCode;
        this.countryName = testData.getData(countryCode + ".fullname");
        this.currency = testData.getData(countryCode + ".currency");
        this.region = testData.getData(countryCode + ".region");
        this.companyName = testData.getData(countryCode + ".companyname");
        this.address1 = testData.getData(countryCode + ".address1");
        this.address2 = testData.getData(countryCode + ".address2");
        this.zipcode = testData.getData(countryCode + ".zipcode");
        this.city = testData.getData(countryCode + ".city");
        this.state = testData.getData(countryCode + ".state");
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