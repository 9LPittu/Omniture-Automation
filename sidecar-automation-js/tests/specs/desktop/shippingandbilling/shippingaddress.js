import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/shoppingbagObj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});
test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
    console.log('user login succesfully')
  }else if((url.indexOf("or.jcrew.com") > -1 )){

  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("or.factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_gold.username,factory_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("https://factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_prod.username,factory_prod.password)
  console.log('user login succesfully')
  }
});


test('Clear the bag items if any products were avilable and Add one product', async () => {
  await clearBagItems();
  console.log('cleared the bag items')
  driver.sleep(10000);
  await addProductTobag();
  await driver.findElement(By.id("js-header__cart")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()

  console.log('selected the required product')
  // await verifyShipToMultiAddress();
  // console.log('verified multi shipping button')
  // await clickOnCheckout();
  console.log('clicked on checkout')

});

test('Verify Adding/Editing/Removing different addresses', async () => {
  await addEditRemoveAddress();
  console.log('verified add/Edit and remove functionalities')
});

export const addProductTobag = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()

  await driver.sleep(2000)
  await driver.navigate().refresh()
  await driver.sleep(3000)
  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
  await driver.sleep(3000)
  await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(3000)
}
