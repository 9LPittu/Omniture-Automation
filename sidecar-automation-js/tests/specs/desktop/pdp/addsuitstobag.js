import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple Suit items to Bag', async () => {

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Thompson Suits & Blazers']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='suits & tuxedos']")).click()
    }
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")));
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")).click()
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='product__price']")));
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      //productsize.click()
      await driver.sleep(3000)
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
    //  await driver.executeScript("arguments[0].scrollIntoView(true);",productaddtobag)
    //  await driver.sleep(3000)
      productaddtobag.click()
      await driver.sleep(3000)
      let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log ("Bag Size >> " + bagSize)
      expect(bagSize).toBeTruthy()
   })

   afterAll(async () => {
    await driver.quit()
  })