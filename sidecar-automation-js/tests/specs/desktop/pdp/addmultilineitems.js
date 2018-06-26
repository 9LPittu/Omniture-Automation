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
  await driver.manage().window().maximize()
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple items from multiple PDP', async () => {

      await driver.navigate().refresh()
      driver.sleep(2000);
      try {
        await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
        // console.log("inside merge page")
         privacyPolicyClose.click()
         driver.sleep(3000)
       })
       } catch (err)
      { }
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(5000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
      await driver.sleep(3000)
      const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
      productsize.click()
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      productaddtobag.click()
      await driver.sleep(3000)
      productaddtobag.click()
      await driver.sleep(3000)
      let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log ("Bag Size >> " + bagSize)
      expect(bagSize).toBeTruthy()

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);

       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Polos']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='Shorts']")).click()
    }
      await driver.sleep(5000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
      await driver.sleep(3000)
      //const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      //const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)

      console.log ("Bag Size >> " + await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText())
      expect(await driver.findElement(By.xpath("//span[@class='js-cart-size']"))).toBeTruthy()
   })
