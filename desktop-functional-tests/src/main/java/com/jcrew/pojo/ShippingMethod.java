package com.jcrew.pojo;

/**
 * Created by nadiapaolagarcia on 5/6/16.
 */
public class ShippingMethod {
    private String method;
    private String price;
    private String text;
    private boolean thursday;

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

    public boolean equals(ShippingMethod compare) {
        boolean result = compare.method.equals(method);
        result &= compare.price.equals(price);
        result &= compare.text.equals(text);

        return result;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }
}