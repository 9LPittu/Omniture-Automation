
import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { createNewAccount } from '../../../mobilepageobjects/mloginpageobj';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';
import { clickOnCheckOutNow,addShippingAddress,addPaymentMethod,verifySecureCheckoutPage,placeOrder } from '../../../mobilepageobjects/singlePageCheckout';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Non express User Checkout (user without address/credit card details)', async () => {
    await createNewAccount()
    await selectCategory()
    await selectItemAddToBag()
    await verifyBag()
    await clickOnCheckOutNow()
    await addShippingAddress()
    await addPaymentMethod()
    await verifySecureCheckoutPage()
    await placeOrder()
   })
