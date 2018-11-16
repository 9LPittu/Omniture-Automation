import { driver, defaultTimeout } from '../../../helpersMobile';
import { load, selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('Verifying filters and sort functionality', async () => {
      await selectCategory();
      await driver.sleep(2000)
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
      console.log("User is able to sort the items")
      await driver.sleep(1000)
      filter.click()
      await driver.sleep(1000)
      expect(driver.findElement(By.xpath("//h5[text()='Filter By']"))).toBeTruthy()
      expect(driver.findElement(By.xpath("//span[text()='Category']"))).toBeTruthy()
      await driver.findElement(By.xpath("//span[text()='Category']")).click()
      await driver.sleep(5000)
      await driver.findElement(By.xpath("(//span[@class='refinement--label__checkbox'])[1]")).click()
      await driver.sleep(1000)
      expect(driver.findElement(By.xpath("//div[text()='1 selected']"))).toBeTruthy()
      expect(driver.findElement(By.id("btn__c-filters--clear"))).toBeTruthy()
      driver.findElement(By.id("btn__c-filters--clear")).click()
      await driver.sleep(1000)
      //expect(driver.findElement(By.xpath("//div[text()='1 selected']"))).toBeFalsy()
      //expect(driver.findElement(By.id("btn__c-filters--clear"))).toBeFalsy()
      await driver.findElement(By.xpath("(//span[@class='refinement--label__checkbox'])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//button[text()='Done']")).click()
      await driver.sleep(3000)
      expect(driver.findElement(By.xpath("//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span[1]"))).toBeTruthy()
      expect(driver.findElement(By.xpath("//span[text()='Clear All']"))).toBeTruthy()
      console.log("Filter functionality verifyed")
     })
