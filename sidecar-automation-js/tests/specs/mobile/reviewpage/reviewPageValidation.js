import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser, logindetails } from '../../../testdata/jcrewTestData';
import {  } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Review Page Validation', async () => {
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//a[@data-department='men']")).click()
      await	driver.sleep(2000);
        await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()

         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    //    await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
    //	  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
    await	driver.sleep(5000);
  //  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups

    try {
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
     } catch (err)
    { }
    await driver.sleep(2000)
  //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
    await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
    await driver.sleep(6000)

      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(6000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
      //await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(10000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Have a promo code?']")))
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//h3[text()='Have a promo code?']")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//input[@data-textboxid='promoCode']")).sendKeys("TEST-10P")
      await driver.sleep(3000)
      await driver.findElement(By.id("promoApply")).click()
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.className("button-general button-submit-bg")))
      await driver.sleep(3000)
      await driver.findElement(By.className("button-general button-submit-bg")).click()
      await driver.sleep(6000)
      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys(logindetails.username1)
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(logindetails.password1)
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()
      await driver.sleep(3000)
      try {
        await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[1]")).then(mergebutton => {
        // console.log("inside merge page")
         mergebutton.click()
         driver.sleep(3000)
         //driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
         })
       } catch (err)
      { }
      driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='payment-method first-card same-billing last']")))
      driver.sleep(3000)
      const paymentDetails = await driver.findElement(By.xpath("//div[@class='payment-method first-card same-billing last']"))
      expect(paymentDetails).toBeTruthy()
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='billing-address notranslate']")))
      driver.sleep(3000)
      const billingAddress = await driver.findElement(By.xpath("//div[@class='billing-address notranslate']"))
      expect(billingAddress).toBeTruthy()
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='shipping-address notranslate']")))
      driver.sleep(3000)
      const shippingAddress = await driver.findElement(By.xpath("//div[@class='shipping-address notranslate']"))
      expect(shippingAddress).toBeTruthy()
      //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[@class='module-name']")))
      //driver.sleep(3000)
      //const promoCodeDetails = await driver.findElement(By.xpath("//span[@class='module-name']"))
    //  expect(promoCodeDetails).toBeTruthy()
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//li[@class='summary-item clearfix']")))
      driver.sleep(3000)
      const taxDetails = await driver.findElement(By.xpath("//li[@class='summary-item clearfix']"))
      expect(taxDetails).toBeTruthy()
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@id='gifting-details']/h2/a")))
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//div[@id='gifting-details']/h2/a")).click()
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//input[@id='includesGifts']")))
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//input[@id='includesGifts']")).click()
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")))
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click()
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='billing-options-submit']")))
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
      await driver.sleep(4000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[text()='Gift Receipt']")))
      await driver.sleep(2000)
      const giftOption = await driver.findElement(By.xpath("//span[text()='Gift Receipt']"))
      expect(giftOption).toBeTruthy()

    })
