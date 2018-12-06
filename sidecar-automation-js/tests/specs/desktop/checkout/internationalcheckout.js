import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { waitSeconds } from '../../../util/commonutils';

const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')
let sleeptime = 2000;

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

each([
  ['Canada'],
  //   ['United Kingdom'],
  //   ['India']
]).test('%s - International Checkout', async contextchooser => {
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 20000)')
  await waitSeconds(3)
  //const footer = await driver.findElement(By.id('global__footer'))
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[@class='footer__country-context__link']")));
  await waitSeconds(3)
  const footer = await driver.findElement(By.id('global__footer'))
  const location = await footer.findElement(By.xpath("//a[@class='footer__country-context__link']"))
  await expect(location).toBeTruthy()
  await location.click()
  await driver.getCurrentUrl(url => {
    expect(url).toMatch('r/context-chooser')
  })
  await closeIconInPAP()
  await waitSeconds(2)
  await driver.findElement(By.xpath("//span[text()='" + contextchooser + "']")).click()
  await waitSeconds(3)
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(3)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await waitSeconds(2)
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password)
  await waitSeconds(2)
  try {
    await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[2]")).then(mergebutton => {
      mergebutton.click()
      driver.sleep(sleeptime)
      driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
    })
  } catch (err) { }

  try {
    await driver.findElement(By.xpath("//*[@id='order-summary']/a[1]")).then(continuesummary => {
      continuesummary.click()
      driver.sleep(sleeptime)
      driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
      driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()

    })
  } catch (err) { }
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {

    try {
      await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
        console.log("inside securitycode")
        securitycode.sendKeys(creditcard.pin)
      })
    } catch (err) {

    }
    await waitSeconds(2)

    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      console.log(">> inside factory")
      await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
    } else {
      await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
    }
    await waitSeconds(2)
    try {
      const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
      expect(bizrate).toBeTruthy()
      bizrate.click()
      await waitSeconds(2)
    } catch (err) {

    }
    let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
    console.log("order Id  > " + orderNumberLet)
    driver.navigate().to(globals.__baseUrl__);
  }
})

afterAll(async () => {
  await driver.quit()
})
