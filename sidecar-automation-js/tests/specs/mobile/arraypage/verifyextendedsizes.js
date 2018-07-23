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

    test('extended sizes validation', async () => {

      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(2000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await	driver.sleep(1000);
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 100)')
      await driver.sleep(1000)
      const extendedSizes = await driver.findElement(By.xpath("(//span[@class='product__variation--name'])[2]"))
      expect(extendedSizes).toBeTruthy()
      console.log("Extended sizes are available for product selected")
  
     })
