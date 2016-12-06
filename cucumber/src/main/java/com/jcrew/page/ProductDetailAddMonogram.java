package com.jcrew.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jcrew.util.StateHolder;

import java.util.List;

/**
 * Created by nadiapaolagarcia on 9/12/16.
 */
public class ProductDetailAddMonogram extends ProductDetailPage {
	public final StateHolder stateHolder = StateHolder.getInstance();
    @FindBy(id = "c-product__monogram")
    WebElement product__monogram;

    public final String TEXT_A = "p-monogram--info__text-a";
    public final String TEXT_B = "p-monogram--info__text-b";
    public final String TEXT_C = "p-monogram--info__text-c";

    public ProductDetailAddMonogram(WebDriver driver) {
        super(driver);
    }

    public boolean isMonogram() {
        return product__monogram.isDisplayed();
    }

    public void addMonogram() {
        WebElement addMonogramButton = product__monogram.findElement(By.className("js-p-monogram--add__button"));
        stateHolder.put("monogram", getProduct());
        addMonogramButton.click();
    }

    public void infoMonogram() {
        WebElement infoMonogramButton = product__monogram.findElement(By.className("js-p-monogram--add__info"));

        infoMonogramButton.click();
    }

    public boolean hasMonogram() {
        List<WebElement> monogramEdit = product__monogram.findElements(By.className("p-monogram--edit__container"));

        return monogramEdit.size() == 1;
    }

    public String getInfoText(String infoTextType) {
        WebElement infoSection = product__monogram.findElement(By.className("p-monogram--info"));
        WebElement infoText = infoSection.findElement(By.className(infoTextType));

        return infoText.getText();
    }

}

