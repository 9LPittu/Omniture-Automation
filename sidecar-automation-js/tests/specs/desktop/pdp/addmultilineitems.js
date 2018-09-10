import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {addMultiLineItems,verifyAndClickOnBag} from '../../../pageObjects/arraypage'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding single / multiple items from multiple PDP', async () => {
      await addMultiLineItems()
      await driver.sleep(1000)
      await verifyAndClickOnBag()
   })

   afterAll(async () => {
    await driver.quit()
  })
