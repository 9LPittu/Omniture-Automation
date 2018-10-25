import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import {clickOnCheckOutNow,guestUserCheckout,placeMyOrder} from '../../../mobilepageobjects/singlePageCheckout';

const each = require('jest-each')
const { By, Key, until } = require('selenium-webdriver')

 let orderNumber

test('title is correct' + orderNumber, async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

/*
 afterAll(async () => {
   await driver.quit()
 })
*/

  test('test Guest checkout', async () => {
    await selectCategory()
    await selectItemAddToBag()
    await verifyBag()
    await clickOnCheckOutNow()
    await guestUserCheckout()
    await placeMyOrder()
  })
