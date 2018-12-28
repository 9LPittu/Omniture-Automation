import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { addSaleItemsToBag } from '../../../mobilepageobjects/mpdppage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding single / multiple items from Sale category', async () => {
     await addSaleItemsToBag();  
  })

   afterAll(async () => {
    await driver.quit()
  })
