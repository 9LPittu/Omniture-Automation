import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {productArrayPage,addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('verifying baynoterecommendations in shopping bag page', async () => {
      await productArrayPage()
      await addProductToBag()
      await verifyAndClickOnBag()
      await driver.executeScript('window.scrollTo(0, 200)')
      await driver.sleep(4000)
      expect(await driver.findElement(By.xpath("//h1[contains(text(),'Like the above?')]"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("(//a[@data-qs-location='Shopping Bag - Recommendations']/img)[1]"))).toBeTruthy()
      console.log("baynoterecommendations are displaying in shopping bag page");
   })

   
   afterAll(async () => {
    await driver.quit()
  })
