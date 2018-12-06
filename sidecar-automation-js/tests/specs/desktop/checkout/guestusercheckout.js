import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { clickCheckoutBtn, checkoutAsGuest, enterCreditCardDetails } from '../../../pageObjects/checkoutObj';
import { waitSeconds } from '../../../util/commonutils';

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
  await waitSeconds(2)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await checkoutAsGuest()

  //credit card details
  await enterCreditCardDetails()

  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    await waitSeconds(3)
    let getUrl = await driver.getCurrentUrl();
    if (getUrl.includes("factory.jcrew.com")) {
      console.log(">> inside factory")
      await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
    } else {
      await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
    }

    await waitSeconds(4)
    try {
      const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
      expect(bizrate).toBeTruthy()
      bizrate.click()
      await waitSeconds(2)
    } catch (err) { }
    let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
    console.log("order Id let > " + orderNumberLet)
    expect(await driver.findElement(By.xpath("//h2[text()='Register Now']"))).toBeTruthy()
    await driver.findElement(By.id("password")).sendKeys("123tgb")
    await driver.findElement(By.id("passwordConf")).sendKeys("123tgb")
    await driver.findElement(By.id("register-submit")).click()
    await waitSeconds(2)
    expect(await driver.findElement(By.xpath("//h3[@class='register-msg confirmation']"))).toBeTruthy()
    await waitSeconds(2)
    //await createNewAccount();
    await productArrayPage();
    await addProductToBag();
    await verifyAndClickOnBag();
    await waitSeconds(1)
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
    await waitSeconds(1)
    await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
    await waitSeconds(1)
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

      try {
        await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
          //        console.log("inside securitycode")
          securitycode.sendKeys(creditcard.pin)
        })

      } catch (err) { }
      await waitSeconds(3)
      let getUrl = await driver.getCurrentUrl();
      if (getUrl.indexOf("factory.jcrew.com") > -1) {
        console.log(">> inside factory")
        await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
      } else {
        await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
      }
      await waitSeconds(3)
      try {
        const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
        expect(bizrate).toBeTruthy()
        bizrate.click()
        await waitSeconds(2)
      } catch (err) { }
      let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
      console.log("order Id  > " + orderNumberLet)
      await waitSeconds(1)
    }
  }
})
afterAll(async () => {
  await driver.quit()
})
