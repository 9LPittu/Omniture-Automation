import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  //await driver.manage().window().maximize()
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('sts Checkout', async () => {
     //await driver.actions().mouseMove(await driver.wait(until.elementLocated(categorymen), defaultTimeout)).perform();
    /* await driver.navigate().refresh()
     driver.sleep(2000);
     try {
       await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
       // console.log("inside merge page")
        privacyPolicyClose.click()
        driver.sleep(3000)
      })
      } catch (err)
     { }*/
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
         console.log(">>>.. inside factory" + currentUrl.indexOf("factory.jcrew.com"))
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()

      await driver.sleep(10000)
      //await driver.navigate().refresh()
      //await driver.sleep(3000)

      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(6000)

      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys(logindetails.username1)
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(logindetails.password1)
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()
      await driver.sleep(3000)

try {
  await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[2]")).then(mergebutton => {
  // console.log("inside merge page")
   mergebutton.click()
   driver.sleep(3000)
   //driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
   })
 } catch (err)
{ }
await driver.findElement(By.xpath("//li/a[contains(text(),'Shipping Address')]")).click()
await driver.sleep(5000)
await driver.findElement(By.xpath("//input[@id='shipToStore']")).click()
await driver.sleep(5000)
await driver.findElement(By.xpath("//input[@class='textbox-zip']")).sendKeys(zipCode.zipcode)
await driver.sleep(5000)
const samedayDelivery = await driver.findElement(By.xpath("(//div[@class='address-container'])[1]"))
expect(samedayDelivery).toBeTruthy()
console.log("same day pick up is available")
await driver.findElement(By.xpath("//a[@id='order-summary__button-continue']")).click()
await driver.sleep(3000)
await driver.findElement(By.xpath("//a[@id='order-summary__button-continue']")).click()
if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

 try {
         await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
    //        console.log("inside securitycode")
      console.log(securitycode.isDisplayed());
            if(securitycode.isDisplayed()){
              securitycode.sendKeys(creditcard.pin)
            }
           })

      } catch (err )
      { }
       await driver.sleep(3000)
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
