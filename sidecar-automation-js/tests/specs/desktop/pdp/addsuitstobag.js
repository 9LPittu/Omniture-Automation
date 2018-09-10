import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { selectSuitsFromCategory,verifyAndClickOnBag } from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple Suit items to Bag', async () => {
  		driver.sleep(2000);
      await selectSuitsFromCategory()
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")));
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")).click()
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='product__price']")));
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      productaddtobag.click()
      await verifyAndClickOnBag()
   })

   afterAll(async () => {
    await driver.quit()
  })
