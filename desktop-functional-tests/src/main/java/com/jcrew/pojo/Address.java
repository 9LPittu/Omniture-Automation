package com.jcrew.pojo;

import com.jcrew.utils.PropertyReader;

/**
 * Created by nadiapaolagarcia on 4/19/16.
 */
public class Address {
    String line1;
    String line2;
    String city;
    String state;
    String zipcode;
    String phone;

    public Address(String country) {
        PropertyReader properties = PropertyReader.getPropertyReader();
        country = country.toLowerCase();

        this.line1 = properties.getProperty(country + ".address.line1");
        this.line2 = properties.getProperty(country + ".address.line2");
        this.city = properties.getProperty(country + ".address.city");
        this.state = properties.getProperty(country + ".address.state");
        this.zipcode = properties.getProperty(country + ".address.zipcode");
        this.phone = properties.getProperty(country + ".address.phone");
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
}
