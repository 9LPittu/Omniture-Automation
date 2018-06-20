import { driver, defaultTimeout } from '../../../helpers';
import { load, amount200 } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('eGiftCard Checkout - Express User', async () => {
    driver.sleep(2000);
       let currentUrl = await driver.getCurrentUrl();

       await driver.executeScript('window.scrollTo(0, 50000)')
        await driver.sleep(2000)
        try {
          await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
           await driver.sleep(5000)
        } catch(err) {}

  // driver.navigate().to("https://or.jcrew.com/help/gift_card.jsp?sidecar=true")
    await driver.findElement(By.xpath("//*[@id='eGiftCard']/img")).click()
    await driver.sleep(3000)

    await driver.findElement(By.xpath("//*[@id='amount25']")).click()
      await driver.sleep(3000)
  //    await driver.findElement(By.xpath("//*[@id='amount25']")).click()
//      await driver.actions().mouseMove(await driver.wait(until.elementLocated(amount200), defaultTimeout)).perform();
//      await driver.sleep(3000)
//      await driver.findElement(By.xpath("//*[@id='amount200']")).click()

      var date = new Date()
      await driver.findElement(By.xpath("//*[@id='senderNameEgc']")).sendKeys("test")
      await driver.findElement(By.xpath("//*[@id='RecipientNameEgc']")).sendKeys("recipient test")
      await driver.findElement(By.xpath("//*[@id='emailAddressEgc']")).sendKeys("sqatree@gmail.com")
      await driver.findElement(By.xpath("//*[@id='date']")).sendKeys((date.getMonth() + 1) + '/' + date.getDate() + '/' +  date.getFullYear())
      await driver.findElement(By.xpath("//*[@id='textAreaMessage']")).sendKeys("test message")
      //*[@id="submitClassic"]
      await driver.findElement(By.id("submitEgift")).click()
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
            await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
          }
       await driver.sleep(4000)
       let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
      console.log("order Id  > " + orderNumberLet)
      }
   })
