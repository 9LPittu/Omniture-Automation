import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser, logindetails } from '../../../testdata/jcrewTestData';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Express User Checkout', async () => {
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//a[@data-department='women']")).click()
    	await	driver.sleep(2000);
      //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//a[text()='t-shirts & polos'])[2]")))
      //await	driver.sleep(2000);
      await driver.findElement(By.xpath("(//a[text()='new arrivals'])[1]")).click()

         let currentUrl = await driver.getCurrentUrl();
      await driver.sleep(4000)
      try{
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      }catch (err){}
      await driver.sleep(2000)
    //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
      await driver.sleep(3000)
      await driver.executeScript('window.scrollTo(0, 600)')
      //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")));
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(1000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
     await driver.sleep(3000)
        await driver.findElement(By.className("button-anchored-bottom")).click()
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
   driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
   })
 } catch (err)
{ }

if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

 try {
         await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
    //        console.log("inside securitycode")
            securitycode.sendKeys(creditcard.pin)
           })

      } catch (err )
      { }
       await driver.sleep(3000)

                 if (currentUrl.indexOf("factory.jcrew.com") > -1) {
                   console.log(">> inside factory")
                   await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
                 } else {
            //       await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
                     await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
                 }

                await driver.sleep(4000)
                let orderNumber = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

                   console.log("order Id  > " + orderNumber)
      }
   })