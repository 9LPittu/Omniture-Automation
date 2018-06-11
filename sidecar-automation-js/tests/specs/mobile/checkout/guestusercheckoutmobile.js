import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { By, Key, until } = require('selenium-webdriver')

 let orderNumber

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
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
      await driver.sleep(3000)
  //    await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
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
      // address detaiils
      await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(guestuser.firstNameSA)
        await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
          await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
          await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
          await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
              await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
                await driver.sleep(3000)
                  await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
                  await driver.sleep(3000)
                  await driver.findElement(By.xpath("//*[@id='main__button-continue-old']")).click()

// address confirmation page :
await driver.sleep(5000)
await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).click()

//shipping & gift pageObjects
await driver.sleep(5000)
await driver.findElement(By.xpath("//*[@id='main__button-continue-old']")).click()

       //credit card details
       await driver.sleep(3000)
         await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number)
         await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
         await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
         await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
         await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys(creditcard.emailReceipt)
        // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
        await driver.findElement(By.xpath("//*[@id='billing-options-submit']")).click()

        await driver.sleep(2000)

        if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
          await driver.sleep(3000)

          if (currentUrl.indexOf("factory.jcrew.com") > -1) {
            console.log(">> inside factory")
            await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
          } else {
            await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
            //await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
          }

         await driver.sleep(4000)
         let orderNumber = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

            console.log("order Id  > " + orderNumber)

      }
   })
