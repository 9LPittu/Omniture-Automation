import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple items from multiple PDP', async () => {

      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//a[@data-department='men']")).click()
      	await	driver.sleep(2000);
        await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
        await driver.sleep(2000)
        try {
          await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
          closeIcon.click()
          driver.sleep(3000)
         })
         } catch (err)
        { }
        //await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
        //await driver.sleep(2000)
        await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
        await driver.sleep(2000)
        //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")));
        await driver.executeScript('window.scrollTo(0, 500)')
        await driver.sleep(2000)
        await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
        await driver.sleep(2000)
        await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("btn__add-to-bag-wide")));
        await driver.sleep(2000)
        await driver.findElement(By.id("btn__add-to-bag-wide")).click()
        await driver.sleep(2000)
        await driver.executeScript('window.scrollTo(0, -10000)')
        await driver.sleep(2000)
        await driver.findElement(By.xpath("//span[text()='menu']")).click()
        await driver.sleep(2000)
        await driver.findElement(By.xpath("//a[@data-department='women']")).click()
        	await	driver.sleep(2000);
            await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
            await driver.sleep(2000)
            try {
              await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
              closeIcon.click()
              driver.sleep(3000)
             })
             } catch (err)
            { }
          await driver.sleep(2000)
          await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
          await driver.sleep(2000)
          await driver.executeScript('window.scrollTo(0, 500)')
          await driver.sleep(2000)
          await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
          await driver.sleep(2000)
          await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("btn__add-to-bag-wide")));
          await driver.sleep(2000)
          await driver.findElement(By.id("btn__add-to-bag-wide")).click()
          await driver.sleep(2000)
          await driver.executeScript('window.scrollTo(0, -10000)')
          await driver.sleep(3000)

      let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log ("Bag Size >> " + bagSize)
      expect(bagSize).toBeTruthy()


   })
