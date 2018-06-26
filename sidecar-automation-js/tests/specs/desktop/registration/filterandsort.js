import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/usercredentials';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('select a product by hovering on menu', async ()=>{
  await selectProduct();
  await driver.sleep(5000);

});

test('verify search functionality is working properly or not', async () => {
     await driver.findElement(By.xpath("//*[@class='icon-header icon-header-search icon-search']")).click();
     await driver.findElement(By.xpath("//*[@id='inputSearchDesktop']")).sendKeys("H9647");
     await driver.findElement(By.xpath("//*[@class='primary-nav__text primary-nav__text--search primary-nav__text--move-right']")).click();
      expect(await driver.findElement(By.xpath("//title"))).toMatch('J.Crew: Clothes, Shoes & Accessories for Women, Men & Kids')
});

test('verify sort and filter functionality is working properly or not', async () => {
await driver.findElement(By.css("#c-filters__header-item--toggle")).click();
await driver.findElement(By.css(".sort-dropdown__header")).click();
});

export const selectProduct = async () => {
  let currentUrl = await driver.getCurrentUrl();
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
  }
  await driver.sleep(3000)
}
