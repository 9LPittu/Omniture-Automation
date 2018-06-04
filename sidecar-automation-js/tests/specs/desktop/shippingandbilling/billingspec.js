import { driver, defaultTimeout } from '../../../helpers';
import { loginDetails } from '../../../testdata/SanityP0';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import {continueOnShippingMethod} from '../../../pageObjects/ShippingMethodObj';
import {paymentMethod} from '../../../pageObjects/BillingObj';


const { Builder, By, Key, until } = require('selenium-webdriver');


test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();

});
test('Login with given username and password', async () => {
  await loginFromHomePage();



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

test('Goto Billng page and check verify credit/debit card process', async () => {
   await continueOnShippingMethod();
   await paymentMethod();

});
