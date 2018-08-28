import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('select a product by hovering on menu', async ()=>{
  await selectProduct();
  await driver.sleep(5000);

});


test('verify sort and filter functionality is working properly or not', async () => {
await driver.findElement(By.css("#c-filters__header-item--toggle")).click();
await driver.sleep(3000)
await driver.findElement(By.css(".sort-dropdown__header")).click();
await driver.sleep(3000)
});

export const selectProduct = async () => {
  let currentUrl = await driver.getCurrentUrl();
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='shirts']")).click()
  }
  await driver.sleep(3000)
}
