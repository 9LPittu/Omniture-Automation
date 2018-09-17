import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple items from single PDP', async () => {

    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(1000);
        await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
        await driver.sleep(1000)
        try {
          await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
          closeIcon.click()
          driver.sleep(1000)
         })
         } catch (err)
        { }
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 600)')
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(1000)
      //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("btn__add-to-bag-wide")));
      //await driver.sleep(1000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, -10000)')
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

  afterAll(async () => {
    await driver.quit()
  })