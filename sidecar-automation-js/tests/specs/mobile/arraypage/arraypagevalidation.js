import { driver, defaultTimeout } from '../../../helpersMobile';
import { selectCategory,validateAyyayPage } from '../../../mobilepageobjects/marraypageobj';



const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Product arraypage validation', async () => {
    console.log("Array page is being verified")
    await selectCategory();
    await validateAyyayPage();
 })

     afterAll(async () => {
      await driver.quit()
    })
