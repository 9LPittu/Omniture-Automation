package com.jcrew.page;

import com.jcrew.page.PageObject;
import com.jcrew.pojo.Product;
import com.jcrew.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nadiapaolagarcia on 9/7/16.
 */
public class Monogram extends PageObject {

    private WebElement monogram_modal_window;

    private final String STAMP_WRAP_XPATH = ".//div[@class='p-monogram--section']/div[@class='p-monogram--stamp__wrap']";
    private final String CONFIRM_CONTAINER_CLASS = "p-monogram--confirm__container";
    private final String CANCEL_CLASS = "js-p-monogram--cancel";
    private final String CLOSE_CLASS = "js-icon-close";

    public Monogram(WebDriver driver) {
        super(driver);

        reload();
    }

    private void reload() {
        monogram_modal_window = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(".//div[@id='c-product__monogram']/div[contains(@class,'p-monogram--details')]")));
    }

    public boolean isDisplayed() {
        reload();
        return monogram_modal_window.isDisplayed();
    }

    public boolean hasCancelButton() {
        WebElement cancel = monogram_modal_window.findElement(By.className(CANCEL_CLASS));

        return cancel.isDisplayed();
    }

    public void cancel() {
        WebElement cancel = monogram_modal_window.findElement(By.className(CANCEL_CLASS));
        cancel.click();
    }

    public boolean hasCloseButton() {
        WebElement close = monogram_modal_window.findElement(By.className(CLOSE_CLASS));

        return close.isDisplayed();
    }

    public void close() {
        WebElement close = monogram_modal_window.findElement(By.className(CLOSE_CLASS));

        close.click();
    }

    public Product getProduct() {
        WebElement nameelement = monogram_modal_window.findElement(
                By.xpath(".//div[@class='product--name-price']/div[contains(@class,'product-name')]"));
        WebElement priceelement = monogram_modal_window.findElement(By.className("p-monogram__info-price"));
        WebElement itemelement = monogram_modal_window.findElement(By.className("p-monogram__info-item"));
        WebElement sizeElement = monogram_modal_window.findElement(By.className("p-monogram__size"));
        WebElement colorElement = monogram_modal_window.findElement(By.className("p-monogram__color-name"));

        Product monogramProduct = new Product();
        monogramProduct.setProductName(nameelement.getText());
        monogramProduct.setPriceList(priceelement.getText());
        monogramProduct.setProductCode(itemelement.getText());
        monogramProduct.setSelectedSize(sizeElement.getText());
        monogramProduct.setSelectedColor(colorElement.getText());

        return monogramProduct;
    }

    public void selectStampStyle() {
        WebElement styleWrap = monogram_modal_window.findElement(By.xpath(STAMP_WRAP_XPATH));
        wait.until(ExpectedConditions.visibilityOf(styleWrap));
        List<WebElement> styles = styleWrap.findElements(By.xpath(".//div[not(contains(@class,'is-selected'))]"));

        WebElement style = styles.get(Util.randomIndex(styles.size()));
        logger.info("Selected style: {}", style.getText());
        stateHolder.put("monogramStyle", style.getText());

        wait.until(ExpectedConditions.visibilityOf(style));
        wait.until(ExpectedConditions.elementToBeClickable(style));
        style.click();
    }

    public List<String> getStampStyles() {
        WebElement styleWrap = monogram_modal_window.findElement(By.xpath(STAMP_WRAP_XPATH));
        List<WebElement> styles = styleWrap.findElements(By.xpath(".//div[contains(@class,'js-p-monogram--stamp')]"));

        return Util.getText(styles);
    }

    public String getStampConfirmation() {
        reload();
        WebElement confirmContainer = monogram_modal_window.findElement(By.className(CONFIRM_CONTAINER_CLASS));
        WebElement stampValue = confirmContainer.findElement(By.className("p-monogram__stamp-value"));

        return stampValue.getText();
    }

    public String getLettersConfirmation() {
        reload();
        WebElement confirmContainer = monogram_modal_window.findElement(By.className(CONFIRM_CONTAINER_CLASS));
        WebElement first = confirmContainer.findElement(By.className("p-monogram--letter__first"));
        WebElement second = confirmContainer.findElement(By.className("p-monogram--letter__second"));
        WebElement third = confirmContainer.findElement(By.className("p-monogram--letter__third"));

        return first.getText() + second.getText() + third.getText();
    }

    public String getMonogramQuestions() {
        WebElement questions = monogram_modal_window.findElement(By.className("p-monogram--questions"));
        String questionsString = questions.getText();

        return questionsString.replace("\n", "");
    }

    public void selectLetters() {
        List<WebElement> letter_containers = monogram_modal_window.findElements(By.className("p-monogram-letter-container"));
        String letters = "";

        for (WebElement letterContainer : letter_containers) {
            Select selectLetter = new Select(letterContainer.findElement(By.tagName("select")));
            selectLetter.selectByIndex(Util.randomIndex(selectLetter.getOptions().size()));
            letters += selectLetter.getFirstSelectedOption().getText();
        }

        stateHolder.put("monogramLetters", letters.replace("-", ""));
    }
    
    public void selectRandomThreadColor(){
    	List<WebElement> thread_colours = monogram_modal_window.findElements(By.className("js-monogram__thread"));
        String color = "";
        int index=0;
        
        if(!thread_colours.isEmpty()){
        	index=Util.randomIndex(thread_colours.size());
        	WebElement threadcolor = thread_colours.get(index);
        	WebElement colorText = threadcolor.findElement(By.className("p-monogram__colors-name"));
        	color=colorText.getText();
        	threadcolor.click();
        }
        
        stateHolder.put("monogramcolor", color);
        
    }
    public void save() {
        WebElement saveButton = monogram_modal_window.findElement(By.className("js-p-monogram--save"));
        saveButton.click();

        stateHolder.put("hasMonogram", true);
    }

}

