import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIcon, categorymen, moduleexports } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import {productArrayPage} from '../../../pageObjects/arraypage'
import {loginInAfterCheckoutPage} from '../../../pageObjects/loginPageObj'
import {mergeButton} from '../../../pageObjects/ShoppingBagObj'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Quick shop Checkout - Express User', async () => {
      await productArrayPage();
      await driver.sleep(1000)
      await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[4]"))).perform();
      const quickbutton = driver.findElement(By.xpath("//button[@class='c-product-tile__quickshop js-product-tile-quickshop']/div"))
      await driver.wait(until.elementIsVisible(quickbutton), defaultTimeout)
      expect(quickbutton).toBeTruthy()
      await quickbutton.click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
      await driver.sleep(3000)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(6000)
      await loginInAfterCheckoutPage(logindetails.username1,logindetails.password1);
      await mergeButton()
      let currentUrl = await driver.getCurrentUrl();
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
              if (currentUrl.indexOf("factory.jcrew.com") > -1) {
                console.log(">> inside factory")
                await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
              } else {
                await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
              }
        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id  > " + orderNumberLet)
      }
   })
