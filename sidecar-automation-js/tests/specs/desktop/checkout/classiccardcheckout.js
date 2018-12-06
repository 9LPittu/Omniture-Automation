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

test('Classic Card Checkout - Express User', async () => {
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
    const signOut = await driver.findElement(By.xpath("(//a[contains(.,'Sign Out')])[1]"))
    expect(signOut).toBeTruthy()
    signOut.click()
  } else {
    const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await waitSeconds(2)
    const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
    expect(signOut).toBeTruthy()
    signOut.click()
  }
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 20000)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")));
  await waitSeconds(2)
  try {
    await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
    await waitSeconds(2)
  } catch (err) { }

  await waitSeconds(2)

  try {
    await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
      privacyPolicyClose.click()
      driver.sleep(2)
    })
  } catch (err) { }
  await driver.findElement(By.xpath("//*[@id='classicGiftCard']/img")).click()
  await waitSeconds(3)
  await driver.findElement(By.xpath("//*[@id='amount25']")).click()
  await driver.findElement(By.xpath("//*[@id='senderName']")).sendKeys("test")
  await driver.findElement(By.xpath("//*[@id='RecipientName']")).sendKeys("recipient test")
  await driver.findElement(By.xpath("//*[@id='text1Message']")).sendKeys("line 1")
  await driver.findElement(By.xpath("//*[@id='text2Message']")).sendKeys("line 2")
  await driver.findElement(By.id("submitClassic")).click()
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
    let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
    console.log("order Id  > " + orderNumberLet)
  }
})

afterAll(async () => {
  await driver.quit()
})
