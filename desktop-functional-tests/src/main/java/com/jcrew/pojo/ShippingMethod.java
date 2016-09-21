package com.jcrew.pojo;

import java.util.Date;

/**
 * Created by nadiapaolagarcia on 5/6/16.
 */
public class ShippingMethod {
    private String method;
    private String price;
    private String text;
    private boolean thursday;
    private Date startDate;
    private Date endDate;

    public ShippingMethod(String method, String price, String text){
        this.method = method;
        this.price = price;
        this.text = text;
        this.thursday = false;
    }

    public ShippingMethod(String method, String price, String text, boolean thursday){
        this.method = method;
        this.price = price;
        this.text = text;
        this.thursday = thursday;
    }
    
    public ShippingMethod(String method, String price, String text, Date startDate, Date endDate){
        this.method = method.toLowerCase();
        this.price = price.toLowerCase();
        this.text = text.toLowerCase();
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return method + " - " + price + " - " + text;
    }

    public boolean equals(Object obj) {
    	if (!(obj instanceof ShippingMethod)){
    		return false;
    	}

    	ShippingMethod compare = (ShippingMethod) obj;
    	
    	boolean result = compare.method.contains(method);
    	result &= compare.price.equalsIgnoreCase(price);
    	result &= compare.text.equalsIgnoreCase(text);
    	
    	return result;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getStartDate() {
    	return startDate;
    }
    
    public void setEndDate(Date endDate) {
         this.endDate = endDate;
    }
    
    public Date getEndDate() {
    	return endDate;
    }
}
