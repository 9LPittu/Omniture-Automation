import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//describe('index', () => {
//  it('title is correct', async () => {
//    await load();
//    expect(await driver.getTitle()).toMatch('J.Crew');
//  });
//});


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('test Guest checkout', async () => {
    await driver.navigate().refresh()
    driver.sleep(2000);
    try {
      await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
      // console.log("inside merge page")
       privacyPolicyClose.click()
       driver.sleep(3000)
     })
     } catch (err)
    { }
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()

      await driver.sleep(2000)
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
      await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()
      await driver.sleep(5000)
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
         await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
         await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
         await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys(creditcard.emailReceipt)
        // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
        await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

        await driver.sleep(2000)

       if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
         await driver.sleep(3000)

         if (currentUrl.indexOf("factory.jcrew.com") > -1) {
           console.log(">> inside factory")
           await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
         } else {
           await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
         }

        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id let > " + orderNumberLet)
      }
   })
