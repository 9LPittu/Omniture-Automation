import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage,addProductToBag, verifyAndClickOnBag} from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })
  test('Editing item added to bag', async () => {
      await productArrayPage()
      await closeIconInPAP()
      await addProductToBag()
      await verifyAndClickOnBag()
      await driver.sleep(3000)
      await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
      await driver.sleep(1000)
      let colrNameBeforeEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
      await driver.findElement(By.xpath("//a[@class='item-edit']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-unavailable'))])[2]")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//button[contains(text(),'Update Bag')]")).click()
      await driver.sleep(2000)
     let colorNameAfterEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
      await driver.sleep(2000)
      if(!(colrNameBeforeEdit==colorNameAfterEdit)){
      console.log ("Color displayed in chip box after edited the item color >> ")
    }
   })

   afterAll(async () => {
    await driver.quit()
  })
