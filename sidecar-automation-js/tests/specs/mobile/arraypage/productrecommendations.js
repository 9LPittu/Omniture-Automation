import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('product recommendations validation', async () => {

      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(1000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await	driver.sleep(1000);
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(3000)
       })
       } catch (err)
      { }
      //await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(3000)
      await driver.executeScript('window.scrollTo(0, 1000)')
      await driver.sleep(10000)
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
