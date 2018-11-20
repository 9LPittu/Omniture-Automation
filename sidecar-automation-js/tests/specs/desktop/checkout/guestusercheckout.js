import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'
import { clickCheckoutBtn, checkoutAsGuest, enterCreditCardDetails } from '../../../pageObjects/checkoutObj'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('test Guest checkout', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await driver.sleep(2000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await clickCheckoutBtn()
  await checkoutAsGuest()
 
  //credit card details
  await enterCreditCardDetails()

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
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
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

      } catch (err) { }
      await driver.sleep(3000)
      let getUrl = await driver.getCurrentUrl();
      if (getUrl.indexOf("factory.jcrew.com") > -1) {
        console.log(">> inside factory")
        await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
      } else {
        await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
      }
      await driver.sleep(3000)
      try {
        const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
        expect(bizrate).toBeTruthy()
        bizrate.click()
        await driver.sleep(2000)
      } catch (err) { }
      let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
      console.log("order Id  > " + orderNumberLet)
      await driver.sleep(1000)
    }
  }
})
afterAll(async () => {
  await driver.quit()
})
