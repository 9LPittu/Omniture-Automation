import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding single / multiple items from Sale category', async () => {

    //  await driver.navigate().refresh()
      driver.sleep(2000);
      try {
        await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
        // console.log("inside merge page")
         privacyPolicyClose.click()
         driver.sleep(3000)
       })
       } catch (err)
      { }
    //  await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[text()='sale']"))).perform();
  	//	driver.sleep(2000);
    driver.sleep(2000);
       let currentUrl = await driver.getCurrentUrl();
     if (currentUrl.indexOf("factory.jcrew.com") > -1) {
       await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[text()='Clearance']"))).perform();
       driver.sleep(2000);
       await driver.findElement(By.xpath("//span[text()='women']")).click()
       driver.sleep(2000);
    } else {
    await driver.findElement(By.xpath("//span[text()='sale']")).click()
    driver.sleep(2000);
    await driver.findElement(By.xpath("(//div[@class='c-sale-recommendation-item'])[1]")).click()
  }
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[@class='product-tile--info'])[2]")).click()
      driver.sleep(6000)
      const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
      productsize.click()
      driver.sleep(3000)
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      productaddtobag.click()
      await driver.sleep(3000)
      productaddtobag.click()
      await driver.sleep(3000)
      let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log ("Bag Size >> " + bagSize)
      expect(bagSize).toBeTruthy()
   })
