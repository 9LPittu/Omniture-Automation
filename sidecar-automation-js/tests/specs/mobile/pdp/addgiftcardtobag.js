import { driver, defaultTimeout } from '../../../helpersMobile';
import { load, goToGiftCardsPage } from '../../../mobilepageobjects/mhomepageobj';
import { addGiftCardToBag } from '../../../mobilepageobjects/mpdppage';



const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding gift card items to bag', async () => {
      await goToGiftCardsPage();
      await addGiftCardToBag("classicGiftCard");
   })

   afterAll(async () => {
    await driver.quit()
  })
