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

  test('Ship to store Order placement', async () => {
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
    await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
    await driver.sleep(2000)
  //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
    await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
    await driver.sleep(3000)

      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(6000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)

    //  let element = driver.findElement(By.id("js-header__cart"));
    //  ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    //  await driver.sleep(3000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
    await driver.sleep(3000)
    await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
    //await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//div[@class='button-actions']/a[@id='button-checkout']")).click()
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
   driver.findElement(By.xpath("//div[@class='button-actions']/a[@id='button-checkout']")).click()
   })
 } catch (err)
{ }
await driver.sleep(5000)
await driver.findElement(By.xpath("//li/a[contains(text(),'Shipping Address')]")).click()
await driver.sleep(5000)
await driver.findElement(By.xpath("//div[@class='c-switch-ship-btn form-radio-set to-store']/input[2]")).click()
await driver.sleep(5000)
await driver.findElement(By.xpath("//input[@class='textbox-zip']")).sendKeys(zipCode.zipcode)
await driver.sleep(5000)
const samedayDelivery = await driver.findElement(By.xpath("(//div[@class='address-container'])[1]"))
expect(samedayDelivery).toBeTruthy()
console.log("same day pick up is available")
await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click()
await driver.sleep(3000)
await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
await driver.sleep(4000)
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
