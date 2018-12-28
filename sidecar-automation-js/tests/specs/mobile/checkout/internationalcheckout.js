import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { loginFromHomePage, clearBagItems } from '../../../mobilepageobjects/mloginpageobj';
import { waitSeconds } from '../../../util/commonutils';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { addSingleLineItemToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { clickOnCheckOutNow_int, placeOrder_int } from '../../../mobilepageobjects/singlePageCheckout';


const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('International Checkout', async() => {
  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password);
  await clearBagItems();
  await waitSeconds(12);
  await selectCategory();
  await addSingleLineItemToBag();
  await clickOnBagIcon();
  await clickOnCheckOutNow_int();
  await placeOrder_int();
 })

 afterAll(async () => {
  await driver.quit()
})