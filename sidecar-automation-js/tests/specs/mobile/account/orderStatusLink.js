import { driver } from '../../../helpersMobile';
import { load, selectAndVerifyOrderStatus } from '../../../mobilepageobjects/mhomepageobj';



const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

  test('Verify Order status Link', async () => {
    await selectAndVerifyOrderStatus();
  })

  afterAll(async () => {
    await driver.quit()
  })
