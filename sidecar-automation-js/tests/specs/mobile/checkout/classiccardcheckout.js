import { driver } from '../../../helpersMobile';
import { load,addGiftCardToBag } from '../../../mobilepageobjects/mhomepageobj';
import { loginFromHomePage,clearBagItems } from '../../../mobilepageobjects/mloginpageobj';
import { verifyShoppingBagPage,clickOnCheckOutNow,verifySecureCheckoutPage,placeOrder } from '../../../mobilepageobjects/singlePageCheckout';
import { jcrew_gold_express} from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Classic Card Checkout - Express User', async () => {

         await loginFromHomePage(jcrew_gold_express.username,jcrew_gold_express.password)
  		   driver.sleep(2000);
         await clearBagItems()
         driver.sleep(12000);
         await addGiftCardToBag("classicGiftCard")
         driver.sleep(8000);
         await verifyShoppingBagPage()
         await clickOnCheckOutNow()
         await verifySecureCheckoutPage()
         await placeOrder()

   })
