import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { loginFromHomePage,loginInAfterCheckoutPage} from '../../../pageObjects/loginPageObj';
import { verifyAndClickOnBag} from '../../../pageObjects/arraypage';



const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Classic Card Checkout - Express User', async () => {

      await loginFromHomePage(logindetails.username1,logindetails.password1)
      await driver.sleep(2000)
      await driver.navigate().to(globals.__baseUrl__+"/CleanPersistentCart.jsp")
      await driver.sleep(11000)
      let currentUrl = await driver.getCurrentUrl();

      if(currentUrl.includes("factory")){
      const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
      expect(loggedInUser).toBeTruthy()
      await driver.actions().mouseMove(loggedInUser).perform();
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//a[@class='js-signout__link']"))
      expect(signOut).toBeTruthy()
      signOut.click()
    }else{
      const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
      expect(loggedInUser).toBeTruthy()
      await driver.actions().mouseMove(loggedInUser).perform();
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
      expect(signOut).toBeTruthy()
      signOut.click()
    }
          await driver.sleep(3000)
          await driver.executeScript('window.scrollTo(0, 20000)')
          await driver.sleep(2000)
          await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")));
          await driver.sleep(2000)
          try {
            await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
             await driver.sleep(5000)
          } catch(err) {}

          driver.sleep(2000);

          try {
            await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
            // console.log("inside merge page")
             privacyPolicyClose.click()
             driver.sleep(3000)
           })
           } catch (err)
          { }
      await driver.findElement(By.xpath("//*[@id='classicGiftCard']/img")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='amount25']")).click()
      await driver.findElement(By.xpath("//*[@id='senderName']")).sendKeys("test")
      await driver.findElement(By.xpath("//*[@id='RecipientName']")).sendKeys("recipient test")
      await driver.findElement(By.xpath("//*[@id='text1Message']")).sendKeys("line 1")
      await driver.findElement(By.xpath("//*[@id='text2Message']")).sendKeys("line 2")
      //*[@id="submitClassic"]
      await driver.findElement(By.id("submitClassic")).click()
      await driver.sleep(3000)
      await verifyAndClickOnBag()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(2000)
      await loginInAfterCheckoutPage(logindetails.username1,logindetails.password1)
      await driver.sleep(2000)

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
