import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import {selectCategory} from '../../../mobilepageobjects/marraypageobj';
import { verifyShoppingTongue } from '../../../mobilepageobjects/mpdppage';


const { By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('verify Shopping tongue', async () => {
  await selectCategory();
  await verifyShoppingTongue();
});

afterAll(async () => {
  await driver.quit()
})
