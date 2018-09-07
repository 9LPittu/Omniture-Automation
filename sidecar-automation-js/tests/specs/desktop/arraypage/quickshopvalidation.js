import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {productArrayPage} from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('quickshop validation', async () => {
      console.log("verifying Quick shop")
      await productArrayPage()
      await driver.sleep(1000)
      await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))).perform();
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[contains(@class,'btn--quickshop')])")).click()
      await driver.sleep(6000)
      const productsize= await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"))
      expect(productsize).toBeTruthy()
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      expect(productaddtobag).toBeTruthy()
      const productaddtowishlist= await driver.findElement(By.id("btn__wishlist"))
      expect(productaddtowishlist).toBeTruthy()
      console.log("User is able to navigate to quick shop from arraypage")
   })

   
   afterAll(async () => {
    await driver.quit()
  })
