import { driver, defaultTimeout } from '../../../helpersMobile';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { loginFromHomePage,clearBagItems } from '../../../mobilepageobjects/mloginpageobj';
import { verifyShoppingBagPage,clickOnCheckOutNow,verifySecureCheckoutPage,placeOrder } from '../../../mobilepageobjects/singlePageCheckout';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Express User Checkout', async () => {
    await driver.sleep(3000)
    await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
    driver.sleep(2000);
    await clearBagItems()
    driver.sleep(12000);
    await selectCategory()
    await selectItemAddToBag()
    await verifyBag()
    await verifyShoppingBagPage()
    await clickOnCheckOutNow()
    await verifySecureCheckoutPage()
    await placeOrder()
   })
