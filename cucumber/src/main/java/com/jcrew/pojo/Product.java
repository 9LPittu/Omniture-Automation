package com.jcrew.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Product {
    private String productName;
    private String productCode;
    private String priceList;
    private String variations;
    private String colorsCount;
    private String priceWas;
    private String priceSale;
    private String selectedVariation;
    private String selectedColor;
    private String selectedSize;
    private String quantity;
    private String currency;
    private boolean isBackOrder = false;
    private boolean isCrewCut = false;
    private boolean isSoldOut = false;
    private boolean hasMonogram = false;
    private String monogramLetters;
    private String monogramStyle;    
    
    private final Logger logger = LoggerFactory.getLogger(Product.class);


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }
    
    public void setProductCode(String productCode){
    	this.productCode = productCode;
    }
    
    public String getProductCode(){
    	return productCode;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }

    public String getPriceList() {
        return priceList;
    }
    
    public void setVariations(String variations) {
        this.variations = variations;
    }

    public String getVariations() {
        return variations;
    }

    public void setColorsCount(String colorsCount) {
        this.colorsCount = colorsCount;
    }

    public String getColorsCount() {
        return colorsCount;
    }

    public void setPriceWas(String priceWas) {
        this.priceWas = priceWas;
    }

    public String getPriceWas() {
        return priceWas;
    }

    public void setPriceSale(String priceSale) {
        this.priceSale = priceSale;
    }

    public String getPriceSale() {
        return priceSale;
    }

    public void setSelectedVariation(String selectedVariation) {
        this.selectedVariation = selectedVariation;
    }

    public String getSelectedVariation() {
        return selectedVariation;
    }

    public void setSelectedColor(String selectedColor) {
        this.selectedColor = selectedColor;
    }

    public String getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedSize(String selectedSize) {
        this.selectedSize = selectedSize;
    }

    public String getSelectedSize() {
        return selectedSize;
    }

    public void setIsBackOrder(boolean isBackOrder) {
        this.isBackOrder = isBackOrder;
    }

    public boolean isBackorder() {
        return this.isBackOrder;
    }

    public void setIsCrewCut(boolean isCrewCut) {
        this.isCrewCut = isCrewCut;
    }

    public boolean isCrewCut() {
        return this.isCrewCut;
    }
    
    public void setQuantity(String quantity){
    	this.quantity = quantity;
    }
    
    public String getQuantity(){
    	return quantity;
    }
    
    public void setSoldOut(boolean soldOut) {
        isSoldOut = soldOut;
    }  
    
    public String getCurrency() {
        return currency;
    }
    
    public boolean hasMonogram() {
        return hasMonogram;
    }

    public void setMonogram(boolean hasMonogram) {
        this.hasMonogram = hasMonogram;
    }

    public String getMonogramLetters() {
        return monogramLetters;
    }

    public void setMonogramLetters(String monogramLetters) {
        this.monogramLetters = monogramLetters;
    }

    public String getMonogramStyle() {
        return monogramStyle;
    }

    public void setMonogramStyle(String monogramStyle) {
        this.monogramStyle = monogramStyle;
    }

    public String toString() {
        String product = "Product details: \n" +
                "Name : " + productName + "\n" +
                "Color : " + selectedColor + "\n" +
                "Size : " + selectedSize + "\n" +
                "Price : " + currency + priceList + "\n" +
                "Quantity : " + quantity + "\n";

        return product;
    }

    public boolean equals(Product product) {
        boolean result;

        String debug = "\nProduct A\t| Product B\t| Result\n";

        result = (this.productName.equalsIgnoreCase(product.productName));
        debug += this.productName + "\t| " + product.productName + "\t| " + result + "\n";

        if (!(this.isSoldOut || product.isSoldOut)) {
            result &= (this.selectedColor.equalsIgnoreCase(product.selectedColor));
            debug += this.selectedColor + "\t| " + product.selectedColor + "\t| " + result + "\n";
            result &= (this.selectedSize.equalsIgnoreCase(product.selectedSize));
            debug += this.selectedSize + "\t| " + product.selectedSize + "\t| " + result + "\n";
            result &= (this.priceList.equalsIgnoreCase(product.priceList));
            debug += this.priceList + "\t| " + product.priceList + "\t| " + result + "\n";
            result &= (this.currency.equalsIgnoreCase(product.currency));
            debug += this.currency + "\t| " + product.currency + "\t| " + result + "\n";
            result &= (this.quantity.equalsIgnoreCase(product.quantity));
            debug += this.quantity + "\t| " + product.quantity + "\t| " + result + "\n";
        }

        logger.debug(debug);

        return result;
    }

    public boolean equals(Product product, boolean ignoreQuantity) {
        boolean result;

        if (ignoreQuantity) {
            String debug = "\nProduct A\t| Product B\t| Result\n";

            result = (this.productName.equalsIgnoreCase(product.productName));
            debug += this.productName + "\t| " + product.productName + "\t| " + result + "\n";

            if (!(this.isSoldOut || product.isSoldOut)) {
                result &= (this.selectedColor.equalsIgnoreCase(product.selectedColor));
                debug += this.selectedColor + "\t| " + product.selectedColor + "\t| " + result + "\n";
                result &= (this.selectedSize.equalsIgnoreCase(product.selectedSize));
                debug += this.selectedSize + "\t| " + product.selectedSize + "\t| " + result + "\n";
                result &= (this.priceList.equalsIgnoreCase(product.priceList));
                debug += this.priceList + "\t| " + product.priceList + "\t| " + result + "\n";
                result &= (this.currency.equalsIgnoreCase(product.currency));
                debug += this.currency + "\t| " + product.currency + "\t| " + result + "\n";
            }

            logger.debug(debug);

        } else {
            result = equals(product);
        }

        return result;
    }
}
