import { driver, defaultTimeout } from '../../../helpersMobile';
import { load} from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory,verifyFilterAndSortFunction } from '../../../mobilepageobjects/marraypageobj';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('Verifying filters and sort functionality', async () => {
      await selectCategory();
      await verifyFilterAndSortFunction();
      console.log("Filter functionality verifyed")
     })

    afterAll(async () => {
      await driver.quit();
    })