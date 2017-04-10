package com.jcrew.page.product;

import com.jcrew.utils.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Created by ngarcia on 4/4/17.
 */
public class ProductMonogram extends ProductDetailsPersonalization {

    private WebElement monogram_modal;

    public ProductMonogram(WebDriver driver) {
        super(driver);
        monogram_modal = product__monogram.findElement(By.className("p-monogram--details"));
        wait.until(ExpectedConditions.visibilityOf(monogram_modal));
    }

    public void selectOptions() {
        List<WebElement> options = monogram_modal.findElements(
                By.xpath(".//div[@class='modal-content']/div[contains(@class,'p-monogram--section')]"));

        for (WebElement option : options) {
            WebElement firstChild = option.findElement(By.xpath(".//*"));
            String kind = firstChild.getAttribute("class");

            switch (kind) {
                case "p-monogram--top":
                    logger.info("ignoring, this is product information");
                    break;
                case "p-monogram__placement--wrap":
                    selectPlacement();
                    break;
                case "p-monogram--stamp__label":
                    selectLabel();
                    break;
                case "p-monogram--letters":
                    selectLetters();
                    break;

                case "p-monogram__colors":
                    selectThread();
                    break;
                default:
                    if (kind.contains("p-monogram--confirm__label")) {
                        monogram_modal.findElement(By.tagName("button")).click();
                    } else {
                        throw new WebDriverException("Monogram selection " + kind + " not implemented");
                    }
            }
        }


    }

    private void selectThread() {
        List<WebElement> threads = monogram_modal.findElements(By.xpath(".//li[contains(@class,'js-monogram__thread')]"));
        WebElement selected = threads.get(Util.randomIndex(threads.size()));

        stateHolder.put("monogramThread", selected.getText());

        selected.click();
    }

    private void selectLetters() {
        List<WebElement> letter_containers = monogram_modal.findElements(By.className("p-monogram-letter-container"));
        String letters = "";

        for (WebElement letterContainer : letter_containers) {
            Select selectLetter = new Select(letterContainer.findElement(By.tagName("select")));
            selectLetter.selectByIndex(Util.randomIndex(selectLetter.getOptions().size() - 1) + 1);
            letters += selectLetter.getFirstSelectedOption().getText();
        }

        stateHolder.put("monogramLetters", letters);
    }

    private void selectPlacement() {
        List<WebElement> options = monogram_modal.findElements(By.className("js-monogram__location"));
        if (options.size() > 0) {
            WebElement selected = options.get(Util.randomIndex(options.size()));

            stateHolder.put("monogramPlacement", selected.getText());

            selected.click();
        }
    }

    private void selectLabel() {
        List<WebElement> options = monogram_modal.findElements(By.className("js-p-monogram--stamp"));
        WebElement selected = options.get(Util.randomIndex(options.size()));

        stateHolder.put("monogramStyle", selected.getText());

        selected.click();
    }
}
