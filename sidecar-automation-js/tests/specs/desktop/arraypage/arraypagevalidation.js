import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//const baseUrl = process.env.BASEURL
//const isHeadless = false || process.env.HEADLESS
//const chromeOptions = isHeadless ? new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox") : new chrome.Options()

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Product arraypage validation', async () => {
      console.log("Array page is being verified ")
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
    const arraypage = await driver.findElement(By.className('c-product__list'))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()

    const producttitle = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--name')])[1]"))
    expect(producttitle).toBeTruthy()

    const productprice = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--price--list')])[1]"))
    expect(productprice).toBeTruthy()

    await driver.executeScript('window.scrollTo(0, 2000)')
    await driver.sleep(2000)

     })
