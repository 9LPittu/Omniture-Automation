package com.jcrew.pojo;

import com.jcrew.utils.TestDataReader;

/**
 * Created by nadiapaolagarcia on 4/19/16.
 */
public class Address {
	String firstName;
	String lastName;
    String line1;
    String line2;
    String city;
    String state;
    String zipcode;
    String phone;
    String country;
    
    TestDataReader dataReader = TestDataReader.getTestDataReader();
    
    public Address() {
        load();
    }

    public Address(String prefix) {
    	prefix = prefix.toLowerCase();
        if(prefix.equals("us")) {
            load();
        }else {
            load(prefix);
        }
    }
    
    public Address(String addressLine1, String addressLine2, String city, String state, String zipcode, String phoneNumber){
    	this.line1 = addressLine1;
        this.line2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phoneNumber;
    }
    
    public Address(String firstName, String lastName, String addressLine1, String addressLine2, String city, String state, String zipcode, String phoneNumber,String country){
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.line1 = addressLine1;
        this.line2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.phone = phoneNumber;
        this.country = country;
    }
    
    private void load(String prefix) {
        this.line1 = dataReader.getData(prefix + ".address.line1");
        this.line2 = dataReader.getData(prefix + ".address.line2");
        this.city = dataReader.getData(prefix + ".address.city");
        this.state = dataReader.getData(prefix + ".address.state");
        this.zipcode = dataReader.getData(prefix + ".address.zipcode");
        this.phone = dataReader.getData(prefix + ".address.phone");
     }
    private void load() {
        this.line1 = dataReader.getData("address.line1");
        this.line2 = dataReader.getData("address.line2");
        this.city = dataReader.getData("address.city");
        this.state = dataReader.getData("address.state");
        this.zipcode = dataReader.getData("address.zipcode");
        this.phone = dataReader.getData("address.phone");
    }
    
    public String getFirstName(){
    	return firstName;
    }
    
    public String getLastName(){
    	return lastName;
    }
    
    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getPhone() {
    	return phone;
    }
    
    public String getCountry() {
        return country;
    }
}