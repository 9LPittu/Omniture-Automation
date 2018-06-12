import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import {continueOnShippingMethod} from '../../../pageObjects/ShippingMethodObj';
import {paymentMethod} from '../../../pageObjects/BillingObj';
import { www } from '../../../testdata/Prod';
import { or,Billing } from '../../../testdata/gold';


const { Builder, By, Key, until } = require('selenium-webdriver');


test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});
test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(www.username,www.password)
    console.log('user login succesfully')
  }else{

  await loginFromHomePage(or.username,or.password)
  console.log('user login succesfully')
  }



});
test('Clear the bag items if any products were avilable and Add one product', async () => {
  await clearBagItems();
  console.log('after clearing bagItem')
  await driver.sleep(10000);
  await goToShoppingBag();
  console.log('after product selection')
  //await verifyShipToMultiAddress();
  await clickOnCheckout();
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
      await paymentMethod('Paypal');
      console.log('After payment Method')




});
