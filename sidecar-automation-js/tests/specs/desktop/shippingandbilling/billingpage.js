import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {continueOnShippingMethod} from '../../../pageObjects/shippingpageobj';
import {paymentMethod} from '../../../pageObjects/BillingObj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage';


const { Builder, By, Key, until } = require('selenium-webdriver');


beforeAll(async () => {
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
  await productArrayPage();
  await addProductToBag();
  await verifyAndClickOnBag();
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  console.log('after product selection')
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

afterAll(async () => {
  await driver.quit()
})
