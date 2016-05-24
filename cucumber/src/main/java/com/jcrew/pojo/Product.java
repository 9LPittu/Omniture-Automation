package com.jcrew.pojo;


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

    public boolean equals(Object o){
        if(o instanceof Product){
            Product object = (Product) o;
            String name = object.getProductName();
            String color = object.getSelectedColor();
            String size = object.getSelectedSize();

            boolean equalName = name.equals(this.productName);
            boolean equalColor = color.equals(this.selectedColor);
            boolean equalSize = size.equals(this.selectedSize);

            return equalName & equalColor & equalSize;
        }

        return false;
    }

}
