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

    test('Verifying filters and sort functionality', async () => {

      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(2000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await	driver.sleep(1000);
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(1000)
      const filter = await driver.findElement(By.xpath("//button[text()='Filter']"))
      expect(filter).toBeTruthy()
      const sortBy = await driver.findElement(By.xpath("//span[text()='Sort By']"))
      expect(sortBy).toBeTruthy()
      console.log("Verifying sort by functionality")
      sortBy.click()
      await driver.sleep(1000)
      const lowToHigh = await driver.findElement(By.xpath("//li[text()='Price: Low to High']"))
      lowToHigh.click()
      await driver.sleep(1000)
      expect(driver.findElement(By.xpath("//span[text()='Price: Low to High']"))).toBeTruthy()
      filter.click()
      await driver.sleep(1000)
      expect(driver.findElement(By.xpath("//h5[text()='Filter By']"))).toBeTruthy()
      await driver.findElement
     })
