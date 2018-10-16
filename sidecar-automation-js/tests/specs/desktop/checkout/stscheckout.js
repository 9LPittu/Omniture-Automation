import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj'
import { mergeButton } from '../../../pageObjects/ShoppingBagObj'


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('ship to store Checkout', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await driver.sleep(1000);
  await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await driver.sleep(1000)
  await loginInAfterCheckoutPage(logindetails.username1, logindetails.password1);
  await driver.sleep(5000)
  await mergeButton();
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//li/a[contains(text(),'Shipping Address')]")).click()
  await driver.sleep(5000)
  await driver.findElement(By.xpath("//input[@id='shipToStore']")).click()
  await driver.sleep(5000)
  await driver.findElement(By.xpath("//input[@class='textbox-zip']")).sendKeys(zipCode.zipcode)
  await driver.sleep(5000)
  const samedayDelivery = await driver.findElement(By.xpath("(//div[@class='address-container'])[1]"))
  expect(samedayDelivery).toBeTruthy()
  console.log("same day pick up is available")
  await driver.findElement(By.xpath("//a[@id='order-summary__button-continue']")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//a[@id='order-summary__button-continue']")).click()
  await driver.sleep(3000)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("factory")) {
    console.log("Ship to store functionality is not available in Factory")
  } else if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

    try {
      await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
        //        console.log("inside securitycode")
    //    console.log(securitycode.isDisplayed());
        if (securitycode.isDisplayed()) {
          securitycode.sendKeys(creditcard.pin)
        }
      })

    } catch (err) { }
    await driver.sleep(3000)
    if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
      if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        console.log(">> inside factory")
        await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
      } else {
        await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
      }
      await driver.sleep(4000)
      try {
      //await driver.sleep(sleeptime)
      const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
      expect(bizrate).toBeTruthy()
      bizrate.click()
      await driver.sleep(2000)
      } catch (err) {}
      let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
      console.log("order Id  > " + orderNumberLet)
    }
  }
})


afterAll(async () => {
  await driver.quit()
})
