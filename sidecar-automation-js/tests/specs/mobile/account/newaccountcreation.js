import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import {createNewAccount} from '../../../mobilepageobjects/mloginpageobj';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})
  test('test creation of new account', async () => {
    await createNewAccount();
   })

   afterAll(async () => {
    await driver.quit()
  })
