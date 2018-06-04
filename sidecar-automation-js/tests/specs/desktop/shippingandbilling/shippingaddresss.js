import { driver, defaultTimeout } from '../../../helpers';
import { loginDetails } from '../../../testdata/SanityP0';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';

const { Builder, By, Key, until } = require('selenium-webdriver');


test('navigate to home page', async () => {
  await driver.manage().window().maximize();
   await load();

});

test('Login with given username and password', async () => {
  await loginFromHomePage();

});

test('Clear the bag items if any products were avilable and Add one product', async () => {
  await clearBagItems();
  driver.sleep(10000);
  await goToShoppingBag();
  await verifyShipToMultiAddress();
  await clickOnCheckout();

});

test('Verify Adding/Editing/Removing different addresses', async () => {
  await addEditRemoveAddress();
});
