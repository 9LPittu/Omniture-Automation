
import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Non express User Checkout (user without address/credit card details)', async () => {
  //   let x = 1462;
//     for (x ; x < 2000; x++) {

    var x = Math.floor((Math.random() * 1000000) + 1);
     let userName = "AutomationTest"+x
     let email = "AutomationTest"+x+"@gmail.com"

        await driver.findElement(By.xpath("//*[@id='c-header__userpanel']/a/span[2]")).click()
        await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
        await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
        await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
        await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
        await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()

        await driver.sleep(10000)
       console.log ("User created >> " + userName)
    	driver.sleep(2000);
      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//a[@data-department='men']")).click()
        await	driver.sleep(2000);
          await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()


      try {
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
    } catch (err) {}
      await driver.sleep(2000)
    //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
      await driver.sleep(2000)
      try{
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
    }catch (err){}
      await driver.sleep(2000)
    //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
      //await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      //await driver.sleep(2000)
      await driver.executeScript('window.scrollTo(0, 600)')
      //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")));
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
    await driver.sleep(3000)
    await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
   await driver.sleep(3000)
      await driver.findElement(By.className("button-anchored-bottom")).click()
      await driver.sleep(6000)

       await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(userName)
         await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
           await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
             await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
               await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
               await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
                 await driver.sleep(2000)
                   await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
                   await driver.sleep(1000)
                   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")));
                   await driver.sleep(1000)
                   await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click()

     // address confirmation page :
     await driver.sleep(5000)

//let shippingVerification = await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate1']/div[2]/a"))
//console.log("shipping page is displayed or not > " + shippingVerification.isDis );

try {

  await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).then(function(webElement) {
        webElement.click();
    }, function(err) {
        if (err.state && err.state === 'no such element') {
            console.log('Element not found');
        } else {
        //    driver.promise.rejected(err);
        console.log('Element not found inside err');
        }
    });

  } catch (err) {
  //  throw err
  }

     //shipping & gift pageObjects
     //await driver.sleep(3000)
     await driver.sleep(3000)
     await driver.findElement(By.id("includesGifts")).click()
     await driver.sleep(1000)
     expect(await driver.findElement(By.xpath("//div[@class='gift-receipt-tooltip clearfix radio-checked']"))).toBeTruthy()
     await driver.sleep(1000)
     await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")));
     await driver.sleep(1000)
     await driver.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")))
     //await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

        //credit card details
        await driver.sleep(90000)
          await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number)
          await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
          await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
          await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
         await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys(creditcard.emailReceipt)
         // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
         await driver.sleep(1000)
         await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='billing-options-submit']")));
         await driver.sleep(1000)
         await driver.executeScript("arguments[0].click();",driver.findElement(By.xpath("//a[@id='billing-options-submit']")))

         await driver.sleep(2000)


         if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

         await driver.sleep(3000)

         if (currentUrl.indexOf("factory.jcrew.com") > -1) {
           console.log(">> inside factory")
           await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
         } else {
           //await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
              await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
         }

        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id let > " + orderNumberLet + ">> user email : " + email)

           await driver.sleep(2000)
           }
    //  } // forloop end
   })
