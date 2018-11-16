import { driver, defaultTimeout } from '../../../helpersMobile';
import { load, selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('product recommendations validation', async () => {
      await selectCategory();
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//span[@class='tile__detail tile__detail--name'])[1]")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 1500)')
      await driver.sleep(1000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Customers Also Love']")));
      await driver.sleep(1000)
      const customersalsolike = await driver.findElement(By.xpath("//h3[text()='Customers Also Love']"))
      expect(customersalsolike).toBeTruthy()
      await driver.sleep(1000)
      let currentUrl = await driver.getCurrentUrl();
      await driver.sleep(1000)
      if (currentUrl.includes("factory.jcrew.com")) {
        const productrecommendationsfactory = await driver.findElement(By.xpath("(//li[@class='c-product-recommendations-tile--grid'])[1]"))
        expect(productrecommendationsfactory).toBeTruthy()
      }else{
      const productrecommendations = await driver.findElement(By.xpath("(//li[@class='c-product-recommendations-tile'])[1]"))
      expect(productrecommendations).toBeTruthy()
      }
      console.log("product recommendations are displayed")
     })

     afterAll(async () => {
      await driver.quit()
    })
