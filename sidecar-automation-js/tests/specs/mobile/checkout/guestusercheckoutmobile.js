import { driver } from '../../../helpersMobile';
import { load,selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { addSingleLineItemToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { verifyShoppingBagPage,clickOnCheckOutNow } from '../../../mobilepageobjects/singlePageCheckout';


const each = require('jest-each')
const { By, Key, until } = require('selenium-webdriver')



test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('test Guest checkout', async () => {
    await selectCategory();
    await addSingleLineItemToBag();
    await clickOnBagIcon();
    await verifyShoppingBagPage();
    await clickOnCheckOutNow();
    
    
    
    await guestUserCheckout()
    await placeMyOrder()
  })

  afterAll(async () => {
    await driver.quit()
  })