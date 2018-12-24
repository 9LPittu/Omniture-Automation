import { driver } from '../../../helpersMobile';
import { load, selectAndVerifyStoreLocator } from '../../../mobilepageobjects/mhomepageobj';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Store Locator link should navigate to store search page', async () => {
    await selectAndVerifyStoreLocator();
})

afterAll(async () => {
  await driver.quit()
})
