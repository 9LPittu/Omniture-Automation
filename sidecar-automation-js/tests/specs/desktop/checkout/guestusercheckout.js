import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import {productArrayPage,addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('test Guest checkout', async () => {
    //await createNewAccount();
    await productArrayPage();
    await addProductToBag();
    await verifyAndClickOnBag();

      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()
      await driver.sleep(2000)
      // address detaiils
      await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(guestuser.firstNameSA)
        await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
          await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
            await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
              await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
              await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
                await driver.sleep(3000)
                  await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
                  await driver.sleep(3000)
                  await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
try {
// address confirmation page :
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
 } catch (err) {}
//shipping & gift pageObjects


       //credit card details
       await driver.sleep(3000)
         await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number)
         await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
         await driver.sleep(3000)
         await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
         await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
         var x = Math.floor((Math.random() * 1000000) + 1);
          let userName = "AutomationTest"+x
          let email = "AutoTest"+x+"@gmail.com"
         await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys(email)
        // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
        await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

        await driver.sleep(5000)
        let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
         await driver.sleep(3000)
         let getUrl = await driver.getCurrentUrl();
         if (getUrl.includes("factory.jcrew.com")) {
           console.log(">> inside factory")
           await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
         } else {
           await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
         }

        await driver.sleep(4000)
        try {
        const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
        expect(bizrate).toBeTruthy()
        bizrate.click()
        await driver.sleep(2000)
        } catch (err) { }
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
        console.log("order Id let > " + orderNumberLet)
        expect(await driver.findElement(By.xpath("//h2[text()='Register Now']"))).toBeTruthy()
        await driver.findElement(By.id("password")).sendKeys("123tgb")
        await driver.findElement(By.id("passwordConf")).sendKeys("123tgb")
        await driver.findElement(By.id("register-submit")).click()
        await driver.sleep(2000)
        expect(await driver.findElement(By.xpath("//h3[@class='register-msg confirmation']"))).toBeTruthy()
        await driver.sleep(2000)
        //await createNewAccount();
        await productArrayPage();
        await addProductToBag();
        await verifyAndClickOnBag();
          await driver.sleep(1000)
          await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
          await driver.sleep(1000)
          let currentUrl = await driver.getCurrentUrl();
          if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

           try {
                   await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
              //        console.log("inside securitycode")
                      securitycode.sendKeys(creditcard.pin)
                     })

                } catch (err )
                { }
                 await driver.sleep(3000)
                 let getUrl = await driver.getCurrentUrl();
                    if (getUrl.indexOf("factory.jcrew.com") > -1) {
                      console.log(">> inside factory")
                      await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
                    } else {
                      await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
                    }
                    await driver.sleep(3000)
                    try{
                    const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
                    expect(bizrate).toBeTruthy()
                    bizrate.click()
                    await driver.sleep(2000)
                  }catch(err){}
                    let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
                   console.log("order Id  > " + orderNumberLet)
                   await driver.sleep(1000)
                 }
      }
   })
