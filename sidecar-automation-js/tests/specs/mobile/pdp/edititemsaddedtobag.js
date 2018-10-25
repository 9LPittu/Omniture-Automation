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

  test('Edit item added to bag', async () => {
      await selectCategory()
      await selectItemAddToBag()
      await verifyBag()
      let colrNameBeforeEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
      await driver.findElement(By.xpath("//a[@class='item-edit']")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 500)')
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-unavailable'))])[2]")).click()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//button[contains(text(),'Update Bag')]")).click()
      await driver.sleep(1000)
      let colorNameAfterEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
      await driver.sleep(1000)
      if(!(colrNameBeforeEdit==colorNameAfterEdit)){
        console.log ("Color displayed in chip box after edited the item color >> ")
      }
   })

   afterAll(async () => {
    await driver.quit()
  })
