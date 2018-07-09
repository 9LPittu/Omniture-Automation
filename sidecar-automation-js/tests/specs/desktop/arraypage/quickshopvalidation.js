import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('quickshop validation', async () => {

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))).perform();
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[contains(@class,'btn--quickshop')])")).click()
      //await driver.navigate().refresh()
      await driver.sleep(3000)
      const productsize= await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"))
      expect(productsize).toBeTruthy()
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      expect(productaddtobag).toBeTruthy()
      const productaddtowishlist= await driver.findElement(By.id("btn__wishlist"))
      expect(productaddtowishlist).toBeTruthy()

   })
