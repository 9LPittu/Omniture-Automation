import { driver, defaultTimeout } from '../../../helpersMobile';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding single / multiple items from multiple PDP', async () => {
      await selectCategory()
      await selectItemAddToBag()
      await selectCategory()
      await selectItemAddToBag()
      await verifyBag()
   })

   afterAll(async () => {
    await driver.quit()
  })
