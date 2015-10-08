package com.jcrew.util;


import com.jcrew.pojo.Product;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Util {

    private static StateHolder stateHolder = StateHolder.getInstance();

    public static int DEFAULT_TIMEOUT = 180;

    public static int randomIndex(int size) {
        return (int) (Math.random() * (size - 1));
    }

    public static Product getCurrentProduct() {
        final List<Product> productList = (List<Product>) stateHolder.get("productList");
        int currentProduct = productList.size() - 1;
        return productList.get(currentProduct);
    }

    public static WebDriverWait createWebDriverWait(WebDriver driver) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }
}
