import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('verifying shoppingbag page', async () => {

      driver.sleep(2000);
      try {
        await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
        // console.log("inside merge page")
         privacyPolicyClose.click()
         driver.sleep(3000)
       })
       } catch (err)
      { }
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='women']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Dresses']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='sweaters']")).click()
    }
      await driver.sleep(5000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      await driver.sleep(8000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(1000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(1000)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(1000)

      expect(await driver.findElement(By.id("zipcode"))).toBeTruthy()

      console.log("Zip code text box is displaying")

      expect(await driver.findElement(By.xpath("//a[@class='button-general button-paypal']"))).toBeTruthy()

      console.log("Paypal payment method is displaying")

      expect(await driver.findElement(By.id("summary-promo-header"))).toBeTruthy()
      await driver.findElement(By.id("summary-promo-header")).click()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("//input[@data-textboxid='promoCode']"))).toBeTruthy()

      console.log("Promo code text box is displaying")

      await driver.sleep(1000)
      expect(await driver.findElement(By.id("summary-gift-card-header"))).toBeTruthy()
      await driver.findElement(By.id("summary-gift-card-header")).click()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("//input[@data-textboxid='giftCard']"))).toBeTruthy()

      console.log("Gift card payment method is displaying")

      expect(await driver.findElement(By.xpath("//a[text()='What is your return policy?']"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("//a[text()='When can I expect my order?']"))).toBeTruthy()

      console.log("links are displaying")

  })
