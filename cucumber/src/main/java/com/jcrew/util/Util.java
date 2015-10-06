package com.jcrew.util;


import com.jcrew.pojo.Product;

import java.util.List;

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
}
