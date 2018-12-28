import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { addSuitItemsToBag } from '../../../mobilepageobjects/mpdppage';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding single / multiple Suit items to Bag', async () => {
      addSuitItemsToBag();
   })

   afterAll(async () => {
    await driver.quit()
  })
