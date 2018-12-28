import { driver } from '../../../helpersMobile';
import { load,clearBagItems, goToGiftCardsPage } from '../../../mobilepageobjects/mhomepageobj';
import { jcrew_gold} from '../../../testdata/jcrewTestData';
import { waitSeconds } from '../../../util/commonutils';
import { addGiftCardToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { verifyShoppingBagPage, clickOnCheckOutNow, verifySecureCheckout_ExpressUser, placeOrder } from '../../../mobilepageobjects/singlePageCheckout';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Classic Card Checkout - Express User', async () => {
         await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  		   await clearBagItems();
         await waitSeconds(12);
         await goToGiftCardsPage();
         await addGiftCardToBag("classicGiftCard");
         await clickOnBagIcon();
         await verifyShoppingBagPage();
         await clickOnCheckOutNow();
         await verifySecureCheckout_ExpressUser();
         await placeOrder();
   })

   afterAll(async () => {
    await driver.quit()
  })
