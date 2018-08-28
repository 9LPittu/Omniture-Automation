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

    test('Product arraypage validation', async () => {
      console.log("Array page is being verified ")
    //  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[@data-department='men']")).click()
        await	driver.sleep(2000);
          await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()

           let currentUrl = await driver.getCurrentUrl();
         if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      //    await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
        } else {
      //	  await driver.findElement(By.xpath("//span[text()='shirts']")).click()
      }
      //await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(2000)
    const arraypage = await driver.findElement(By.xpath("//div[@class='product__list']"))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()

    const producttitle = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--name')])[1]"))
    expect(producttitle).toBeTruthy()

    const productprice = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--price')])[1]"))
    expect(productprice).toBeTruthy()

    await driver.executeScript('window.scrollTo(0, 2000)')
    await driver.sleep(2000)

     })
