import { driver, defaultTimeout } from '../helpers';

const { Builder, By, Key, until } = require('selenium-webdriver');



     export const gotoSearchBar() = async ()=>{
      await driver.findElement(By.xpath("//*[@class='icon-header icon-header-search icon-search']")).click();
     }
     export const inputProductId() = async ()=>{
       await driver.findElement(By.xpath("//*[@id='inputSearchDesktop']")).sendKeys("H9469");
     }
     export const clickOnSearch() = async ()=>{
       await   driver.findElement(By.xpath('//*[@class=\'primary-nav__text primary-nav__text--search primary-nav__text--move-right\']')).click();

     }

export const clickUsingXpath() = async (xpath)=>{
  await driver.findElement(By.xpath(xpath)).click();
}

export const sendKeyUsingXpath() = async (xpath,text)=>{
  await driver.findElement(By.xpath(xpath)).sendKeys(text);
}
