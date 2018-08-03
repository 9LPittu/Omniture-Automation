import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('extended sizes validation', async () => {

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
    		driver.sleep(2000);
           let currentUrl = await driver.getCurrentUrl();
         if (currentUrl.indexOf("factory.jcrew.com") > -1) {
           console.log(">>>.. inside factory" + currentUrl.indexOf("factory.jcrew.com"))
          await driver.findElement(By.xpath("//span[text()='T-shirts & Henleys']")).click()
        } else {
  		  await driver.findElement(By.xpath("//span[text()='t-shirts & polos']")).click()
      }
      await	driver.sleep(1000);
      //await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      //await driver.sleep(1000)
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
      await driver.sleep(5000)
      const extendedSizes = await driver.findElement(By.xpath("(//li[contains(@class,'js-product__variation') and not(contains(@class,'is-selected'))])[1]"))
      expect(extendedSizes).toBeTruthy()
      extendedSizes.click()
      await driver.sleep(3000)
      await driver.executeScript('window.scrollTo(0, 100)')
      await driver.sleep(1000)
      const sizechart = await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn')])[1]"))
      expect(sizechart).toBeTruthy()
      console.log("Extended sizes are available for product selected")

     })
