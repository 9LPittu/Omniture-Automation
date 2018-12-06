import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { loginFromHomePage, loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { waitSeconds } from '../../../util/commonutils';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('eGiftCard Checkout - Express User', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await loginFromHomePage(logindetails.username, logindetails.password)
  await waitSeconds(2)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await waitSeconds(2)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("factory")) {
    const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const signOut = await driver.findElement(By.xpath("(//a[contains(text(),'Sign Out')])[1]"))
    expect(signOut).toBeTruthy()
    await signOut.click()
  } else {
    const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
    expect(signOut).toBeTruthy()
    signOut.click()
  }
  await waitSeconds(3)

  //    let currentUrl = await driver.getCurrentUrl();
  await driver.executeScript('window.scrollTo(0, 20000)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")));
  await waitSeconds(2)
  try {
    await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
    await waitSeconds(5)
  } catch (err) { }
  await waitSeconds(3)
  await waitSeconds(2)
  try {
    await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
      privacyPolicyClose.click()
      driver.sleep(3000)
    })
  } catch (err) { }
  await driver.findElement(By.xpath("//*[@id='eGiftCard']/img")).click()
  await waitSeconds(3)
  await driver.findElement(By.xpath("(//*[@id='amount25'])[2]")).click()
  await waitSeconds(3)
  var date = new Date()
  await driver.findElement(By.xpath("//*[@id='senderNameEgc']")).sendKeys("test")
  await driver.findElement(By.xpath("//*[@id='RecipientNameEgc']")).sendKeys("recipient test")
  await driver.findElement(By.xpath("//*[@id='emailAddressEgc']")).sendKeys("automationca@gmail.com")
  await driver.findElement(By.xpath("//*[@id='date']")).sendKeys((date.getMonth() + 1) + '/' + date.getDate() + '/' + date.getFullYear())
  await driver.findElement(By.xpath("//*[@id='textAreaMessage']")).sendKeys("test message")
  await driver.findElement(By.id("submitEgift")).click()
  await waitSeconds(3)
  await verifyAndClickOnBag()
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await waitSeconds(2)
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password)
  await waitSeconds(2)

  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    try {
      await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
      securitycode.sendKeys(creditcard.pin)
      })
    } catch (err) { }
    await waitSeconds(3)
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
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
    } catch (err) {

    }
    let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
    console.log("order Id  > " + orderNumberLet)
  }
})


afterAll(async () => {
  await driver.quit()
})
