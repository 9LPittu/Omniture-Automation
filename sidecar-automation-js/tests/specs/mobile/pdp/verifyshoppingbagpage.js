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


  test('Verify shopping Bag page', async () => {

      await selectCategory()
      await selectItemAddToBag()
      await verifyBag()

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
