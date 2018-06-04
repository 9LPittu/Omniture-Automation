import { driver, defaultTimeout } from '../../../helpers';
//import { driver, defaultTimeout } from '../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//const baseUrl = process.env.BASEURL
//const isHeadless = false || process.env.HEADLESS
//const chromeOptions = isHeadless ? new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox") : new chrome.Options()

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

/*
 test('Close email capture is present or not', async () => {

   let close_email_capture = await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']"))

   await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).then(function(webElement) {
         close_email_capture.click()
     }, function(err) {
         if (err.state && err.state === 'no such element') {
             console.log('Element not found')
         } else {
             driver.promise.rejected(err)
         }
     })

    })
*/

/*
    test('Twitter is working', async () => {
      await driver.executeScript('window.scrollTo(0, 20000)')
        await driver.sleep(2000)
      const footer = await driver.findElement(By.tagName('footer'))
      const contact = footer.findElement(By.id('c-footer__twitter'))
      expect(contact).toBeTruthy()
      await driver.actions({ bridge: true }).move({origin: contact}).click().perform()
      await driver.getCurrentUrl( url => {
        expect(url.match('https://twitter.com/jcrew_help')).toBeTruthy()
      })
      await driver.navigate().back()
    })
    */



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
