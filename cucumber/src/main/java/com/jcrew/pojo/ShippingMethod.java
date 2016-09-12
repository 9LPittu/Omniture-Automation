package com.jcrew.pojo;

import java.util.Date;

public class ShippingMethod {
    private String method;
    private String price;
    private String text;
    private Date startDate;
    private Date endDate;

    public ShippingMethod(String method, String price, String text){
        this.method = method.toLowerCase();
        this.price = price.toLowerCase();
        this.text = text.toLowerCase();
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
        this.method = method.toLowerCase();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price.toLowerCase();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text.toLowerCase();
    }

    public String toString() {
        return method + " - " + price + " - " + text;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ShippingMethod))
            return false;

        ShippingMethod compare = (ShippingMethod) obj;

        boolean result = compare.method.contains(method);
        result &= compare.price.equalsIgnoreCase(price);
        result &= compare.text.equalsIgnoreCase(text);

        return result;
    }

}

