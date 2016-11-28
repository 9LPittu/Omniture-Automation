package com.jcrew.page;

import com.jcrew.utils.Util;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ArraySearchRefinement extends Array{


    @FindBy(id = "page__search")
    private WebElement pageSearch;
    @FindBy(id = "c-search__filter")
    private WebElement searchFilter;

    public ArraySearchRefinement(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    
    
}    


