import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {continueOnShippingMethod} from '../../../pageObjects/shippingpageobj';
import {paymentMethod} from '../../../pageObjects/BillingObj';
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
  console.log('after clearing bagItem')
  await driver.sleep(10000);
  //await goToShoppingBag();
  await addProductTobag();
  console.log('after add product to bag')
  await driver.findElement(By.id("js-header__cart")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()


  console.log('after product selection')
  //await verifyShipToMultiAddress();
//  await clickOnCheckout();
  console.log('After checkout')
  await driver.findElement(By.css("#nav-shipping")).click();
  await driver.sleep(5000);
  console.log('After checkout')
  await clickOnContinue();
  console.log("after continue")

});

test('Goto Billng page and check verify credit/debit card or paypal process', async () => {

      await continueOnShippingMethod();
      console.log('---')
      await paymentMethod('Credit/Debit_Card');
      console.log('After payment Method')

});

export const addProductTobag = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//div[@class='c-product__photos']")).click()

  await driver.sleep(2000)
  //await driver.navigate().refresh()
  await driver.sleep(3000)
  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
  await driver.sleep(3000)
  await driver.executeScript('window.scrollTo(0, 300)')
  await driver.sleep(3000)
  await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(3000)
}
