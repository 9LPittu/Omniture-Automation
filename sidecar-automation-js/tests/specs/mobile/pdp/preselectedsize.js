import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { verifyPreSelectedSize } from '../../../mobilepageobjects/mpdppage';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Verifying preselectedsize', async () => {
    await verifyPreSelectedSize();   
   })

   afterAll(async () => {
    await driver.quit()
  })
