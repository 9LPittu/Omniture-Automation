
import { driver } from '../../../helpersMobile';
import { createNewAccount } from '../../../mobilepageobjects/mloginpageobj';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { addSingleLineItemToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { verifyShoppingBagPage, clickOnCheckOutNow, verifySecureCheckout_NonExpressUser, addShippingAddress, addPaymentMethod, placeOrder } from '../../../mobilepageobjects/singlePageCheckout';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Non express User Checkout (user without address/credit card details)', async () => {
    await createNewAccount();
    await selectCategory();
    await addSingleLineItemToBag();
    await clickOnBagIcon();
    await verifyShoppingBagPage();
    await clickOnCheckOutNow();
    await verifySecureCheckout_NonExpressUser();
    await addShippingAddress();
    await addPaymentMethod();
    await placeOrder();
   })
  
   afterAll(async () => {
    await driver.quit()
  })
