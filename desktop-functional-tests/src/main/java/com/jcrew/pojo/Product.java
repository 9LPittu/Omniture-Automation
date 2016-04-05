package com.jcrew.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by nadiapaolagarcia on 4/1/16.
 */
public class Product {
    private final Logger logger = LoggerFactory.getLogger(Product.class);
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
        this.color = color.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity.trim();
    }

    public String getPrice() {
        return price;
    }

    public float getItemPrice() {
        return Float.parseFloat(price) * Float.parseFloat(quantity);
    }

    public void setPrice(String price) {
        price = price.trim().toLowerCase();
        price = price.replace("now", "");
        price = price.replace("was", "");
        this.price = price.replaceAll("[^0-9.]", "");
        this.currency = price.replace(this.price, "").trim();
    }

    public String toString() {
        String product = "Product details: \n" +
                "Name : " + name + "\n" +
                "Color : " + color + "\n" +
                "Size : " + size + "\n" +
                "Price : " + currency + price + "\n" +
                "Quantity : " + quantity + "\n";

        return product;
    }

    public boolean equals(Product product) {
        boolean result;

        String debug = "\nProduct A\t|\tProduct B\n";
        debug += this.name + "\t|\t" + product.name + "\n";
        debug += this.color + "\t|\t" + product.color + "\n";
        debug += this.size + "\t|\t" + product.size + "\n";
        debug += this.quantity + "\t|\t" + product.quantity + "\n";
        debug += this.price + "\t|\t" + product.price + "\n";
        debug += this.currency + "\t|\t" + product.currency + "\n\n";

        result = (this.name.equalsIgnoreCase(product.name));
        debug += "name: " + result + "\n";
        result &= (this.color.equalsIgnoreCase(product.color));
        debug += "color: " + result + "\n";
        result &= (this.size.equalsIgnoreCase(product.size));
        debug += "size: " + result + "\n";
        result &= (this.quantity.equalsIgnoreCase(product.quantity));
        debug += "quantity: " + result + "\n";
        result &= (this.price.equalsIgnoreCase(product.price));
        debug += "price: " + result + "\n";
        result &= (this.currency.equalsIgnoreCase(product.currency));
        debug += "currency: " + result + "\n";

        logger.debug(debug);

        return result;
    }
}
