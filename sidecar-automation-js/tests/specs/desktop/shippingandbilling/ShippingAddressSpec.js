import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
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
  console.log('cleared the bag items')
  driver.sleep(10000);
  await goToShoppingBag();
  console.log('selected the required product')
  await verifyShipToMultiAddress();
  console.log('verified multi shipping button')
  await clickOnCheckout();
  console.log('clicked on checkout')

});

test('Verify Adding/Editing/Removing different addresses', async () => {
  await addEditRemoveAddress();
  console.log('verified add/Edit and remove functionalities')
});
