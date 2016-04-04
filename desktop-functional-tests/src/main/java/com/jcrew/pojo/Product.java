package com.jcrew.pojo;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class Product {
    private String color;
    private String size;
    private String name;
    private String quantity;
    private String price;
    private String currency;

    public Product() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        price = price.toLowerCase();
        price = price.replace("now", "");
        price = price.replace("was", "");
        this.price = price.replaceAll("[^0-9.]", "");
        this.currency = price.replace(this.price, "");
    }

    public String toString(){
        String product = "Product details: \n" +
                "Name : " + name + "\n" +
                "Color : " + color + "\n" +
                "Size : " + size + "\n" +
                "Price : " + currency + price + "\n" +
                "Quantity : "+ quantity + "\n";

        return product;
    }
}
