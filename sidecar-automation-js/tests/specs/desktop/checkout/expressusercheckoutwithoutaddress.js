import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import { createNewAccount } from '../../../pageObjects/loginPageObj'
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Non express User Checkout (user without address/credit card details)', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await createNewAccount();
  await productArrayPage();
  await closeIconInPAP();
  await addProductToBag();
  await verifyAndClickOnBag();
  await driver.sleep(1000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(guestuser.firstNameSA)
  await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
  await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
  await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
  await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
  await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()

  // address confirmation page :
  await driver.sleep(5000)
  try {

    await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).then(function (webElement) {
      webElement.click();
    }, function (err) {
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
  await driver.sleep(3000)
  await driver.findElement(By.id("includesGifts")).click()
  await driver.sleep(1000)
  expect(await driver.findElement(By.xpath("//div[@class='gift-receipt-tooltip clearfix radio-checked']"))).toBeTruthy()
  await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

  //credit card details
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number)
  await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
  await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys(creditcard.expirationMonth)
  await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys(creditcard.expirationYear)
  await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard)
  await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

  await driver.sleep(2000)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    await driver.sleep(3000)

    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
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

    await driver.sleep(2000)

  }

})


afterAll(async () => {
  await driver.quit()
})
