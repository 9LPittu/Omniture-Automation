import { driver } from '../../helpersMobile';
import { globals } from '../../jestJcrewQaConfig';
import { load } from '../../pageObjects/jcrewmobilepageobj';
import { creditcard } from '../../testdata/jcrewTestData';
import { guestuser } from '../../testdata/jcrewTestData';
import element from '../../util/commonutils';

const each = require('jest-each')
const { By, Key, until } = require('selenium-webdriver')

 let orderNumber

//describe('index', () => {
//  it('title is correct', async () => {
//    await load();
//    expect(await driver.getTitle()).toMatch('J.Crew');
//  });
//});


test('title is correct' + orderNumber, async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

/*
 afterAll(async () => {
   await driver.quit()
 })
*/

/*
 test('Close email capture is present or not', async () => {
  await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).click()
  driver.sleep(4000)
    })

*/
  test('test Guest checkout', async () => {
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
    //  var x = Math.floor((Math.random() * 10) + 1);
      await driver.sleep(4000)

      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()  // close the popups
      await driver.sleep(2000)
    //  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)["+x+"]")).click()
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(3000)
  //    await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.findElement(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("js-header__cart")).click()

  //   await element.isNotPresent(this.driver, By.xpath("//*[@id='button-checkout']"))

  //    await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
   await driver.findElement(By.className("button-anchored-bottom")).click()
      await driver.sleep(6000)
      await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()
      await driver.sleep(5000)
      //ship to store order placement

      await driver.findElement(By.xpath("//input[@value='shipToStore']")).click()
      await driver.sleep(6000)
      await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(zipcode.stsZipCode)
      await driver.sleep(6000)
      await driver.findElement(By.xpath("//a[@id='order-summary__button-continue']")).click()
      await driver.sleep(6000)


       //credit card details
       await driver.sleep(3000)
         await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number)
         await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
         await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
         await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
        // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
        await driver.sleep(2000)
        await driver.findElement(By.xpath("//input[@data-textboxid='firstName']")).sendKeys(guestuser.firstNameSA)
          await driver.findElement(By.xpath("//input[@data-textboxid='lastName']")).sendKeys(guestuser.lastNameSA)
            await driver.findElement(By.xpath("//input[@data-textboxid='address1']")).sendKeys(guestuser.address1)
          //  await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
          //  await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
                await driver.findElement(By.xpath("//input[@data-textboxid='zipcodeAddress']")).sendKeys(guestuser.zipcode)
                  await driver.sleep(3000)
                    await driver.findElement(By.xpath("//input[@data-textboxid='phoneNum']")).sendKeys(guestuser.phoneNumSA)
                    await driver.findElement(By.xpath("//input[@data-textboxid='emailAddress']")).sendKeys(creditcard.emailReceipt)
                    await driver.sleep(1000)
                    await driver.findElement(By.xpath("//a[@id='main__button-continue']")).click()
                    await driver.sleep(5000)
                    if(await driver.findElement(By.xpath("//li[@class='address-entry address-entry-first']/label/input[2]")).isDisplayed()){
                      await driver.findElement(By.xpath("//li[@class='address-entry address-entry-first']/label/input[2]")).click()
                      await driver.findElement(By.xpath("//a[@data-bma='continue_selected_address']")).click()
                    }
                    //   let currentUrl = await driver.getCurrentUrl();
    //   console.log("current URL > " + currentUrl.indexOf("https://or.jcrew.com"))

       //if (currentUrl == "https://or.jcrew.com/checkout2/billing.jsp") {  // Production review checkout
       if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
         await driver.sleep(3000)
         await driver.findElement(By.xpath("//a[@class='item-link-submit button-general button-submit-bg off-monetateLPO']")).click()
        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
                orderNumber =   orderNumberLet
           console.log("order Id  > " + orderNumberLet)
              console.log("order Id  > " + orderNumber)

      }
   })
