import { driver, defaultTimeout } from '../../../helpersMobile';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Edit item added to bag', async () => {
        if(!(colrNameBeforeEdit==colorNameAfterEdit)){
        console.log ("Color displayed in chip box after edited the item color >> ")
      }
   })

   afterAll(async () => {
    await driver.quit()
  })
