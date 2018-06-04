import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Non express User Checkout (user without address/credit card details)', async () => {
    let currentUrl = await driver.getCurrentUrl();
     let x = 5020;
     for (x ; x < 6000; x++) {
try {
     let userName = "Perftest"+x
     let email = userName+"@gmail.com"
      await driver.navigate().to(globals.__baseUrl__+"/r/login")
      await driver.sleep(5000)


    
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
    	driver.sleep(2000);
	  await driver.findElement(By.xpath("//span[text()='Casual Shirts']")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
      await driver.sleep(2000)
      await driver.navigate().refresh()
      await driver.sleep(3000)
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(4000)
  	await driver.navigate().refresh()
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(6000)

      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys(email)
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys("nft123")
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()

      await driver.sleep(5000)

} catch (err) {
}
try {
       await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(userName)
         await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
           await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
             await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
               await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
               await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
                 await driver.sleep(3000)
                   await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
                   await driver.findElement(By.xpath("//input[@id='city']")).sendKeys(guestuser.city)
                   await driver.findElement(By.xpath("//select[@id='dropdown-state-province']")).sendKeys(guestuser.state)
                   await driver.sleep(3000)
                   await driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()

     // address confirmation page :
     await driver.sleep(5000)

} catch (err) {
}

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
}
try {
     //shipping & gift pageObjects
     await driver.sleep(6000)
     await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

        //credit card details
        await driver.sleep(5000)
          await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.jcccard)
      //    await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
      //    await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
      //    await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
          await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
      //    await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys(creditcard.emailReceipt)
         // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
         await driver.sleep(4000)

         await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

         await driver.sleep(4000)

		 } catch (err) {
}
try {
         if (new String(currentUrl).valueOf() != ((new String("https://www.jcrew.com/").valueOf()) && new String("https://factory.jcrew.com/").valueOf())) {

         await driver.sleep(3000)
            if (currentUrl.indexOf("factory.jcrew.com") > -1) {
         await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click() // factory
       } else {
         await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click() // jcrew
       }
        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id let > " + orderNumberLet + ">> user email : " + email)

           await driver.sleep(2000)
    //       await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']")).click()
           //await driver.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/div/dd[1]/a")).click()
       await driver.sleep(3000)

       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
  //       await driver.findElement(By.xpath(".//*[text()='Sign Out']")).click()
       } else {
       await driver.findElement(By.xpath("//*[@id='nav__ul']/li[10]")).click()
       await driver.sleep(3000)
           }
         }
 } catch (err) {}
      } // forloop end
   })
