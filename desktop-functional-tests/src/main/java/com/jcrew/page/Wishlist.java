package com.jcrew.page;

import com.jcrew.page.header.HeaderWrap;
import com.jcrew.pojo.Country;
import com.jcrew.utils.Util;
import org.apache.commons.lang.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

/**
 * Created by ngarcia on 3/27/17.
 */
public class Wishlist extends PageObject {

    @FindBy(id = "wishlistName")
    private WebElement wishlistName;
    @FindBy(id = "userWishlist")
    private WebElement userWishlist;

    public Wishlist(WebDriver driver) {
        super(driver);
        Util.waitForPageFullyLoaded(driver);
        wait.until(ExpectedConditions.visibilityOf(wishlistName));
        HeaderWrap header = new HeaderWrap(driver);
    }

    public boolean isWishList() {
        Country country = stateHolder.get("context");
        boolean isURLPattern = Util.countryContextURLCompliance(driver, country, "/wishlist/");
        return wishlistName.isDisplayed();
    }


    public boolean verifyProductInWishlist(String expectedProductName) {
        String Prodname;

        Util.createWebDriverWait(driver).until(ExpectedConditions.visibilityOf(userWishlist));
        List<WebElement> productsInWishlist = userWishlist.findElements(By.className("item-data"));

        for (WebElement wishElement : productsInWishlist) {
            Prodname = wishElement.getAttribute("data-itemtitle").toLowerCase();
            Prodname = StringEscapeUtils.unescapeHtml(Prodname);
            Prodname = Prodname.replace("pre-order ", "");
            if (expectedProductName.equalsIgnoreCase(Prodname)) {
                return true;
            }
        }

        return false;
    }

    public void delete_current_products() {
        Util.waitForPageFullyLoaded(driver);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript(
                "var params = $.parseJSON(globalObj.wishlist.itemsArrayJson).header;" +
                        "params.deleteItems = params.sortOrder;" +
                        "$.ajax({ url: \"/userwishlist/ajax/wishlist_ajax_update.jsp\",\n" +
                        "         dataType: \"json\"," +
                        "         data: params" +
                        "});"
        );
    }
}
