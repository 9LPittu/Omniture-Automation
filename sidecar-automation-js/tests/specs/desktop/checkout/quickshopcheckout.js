import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIcon, categorymen, moduleexports } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Quick shop Checkout - Express User', async () => {
    await driver.actions().mouseMove(await driver.wait(until.elementLocated(categorymen), defaultTimeout)).perform();
        driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }

 //   var x = Math.floor((Math.random() * 5) + 1);
      await driver.sleep(3000)
      await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[4]"))).perform();
      const quickbutton = driver.findElement(By.xpath("//button[@class='c-product-tile__quickshop js-product-tile-quickshop']/div"))
      await driver.wait(until.elementIsVisible(quickbutton), defaultTimeout)
      expect(quickbutton).toBeTruthy()
      await quickbutton.click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(6000)

      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys(logindetails.username)
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(logindetails.password)
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()

      await driver.sleep(3000)

      try {
        await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[1]")).then(mergebutton => {
         console.log("inside merge page")
         mergebutton.click()
         driver.sleep(3000)
         driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      })
      } catch (err)
      { }
     if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
         await driver.sleep(3000)
         try {
                 await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
                    console.log("inside securitycode")
                    securitycode.sendKeys(creditcard.pin)
                   })

              } catch (err )
              {

              }
         await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id  > " + orderNumberLet)
      }
   })
