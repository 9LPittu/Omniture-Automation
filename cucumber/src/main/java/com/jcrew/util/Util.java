package com.jcrew.util;


import com.jcrew.pojo.Product;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Util {

    private static StateHolder stateHolder = StateHolder.getInstance();

    public static int randomIndex(int size) {
        return (int) (Math.random() * (size - 1));
    }

    public static Product getCurrentProduct() {
        final List<Product> productList = (List<Product>) stateHolder.get("productList");
        int currentProduct = productList.size() - 1;
        return productList.get(currentProduct);
    }
    
    public String getCurrencySymbol(){
    	
    	String currencySymbol = null;
    	WebElement countryName = new DriverFactory().getDriver().findElement(By.className("footer__country-context__country"));
    	
    	String country = countryName.getText().trim().toUpperCase();
    	
    	switch(country){
    	case "UNITED STATES":
    		currencySymbol = "$";    		
    	}
    	
    	return currencySymbol;
    }
}