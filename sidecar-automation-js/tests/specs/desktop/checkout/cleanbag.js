import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { loginFromHomePage } from '../../../pageObjects/loginPageObj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Clean Bag', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  let i = 1;
  let userName = "PerfTest" + i
  let email = "Perftest" + i + "@gmail.com"
  let password = "nft123"
  await loginFromHomePage(email, password)
  await driver.sleep(3000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(2000)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await driver.sleep(2000)

  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl == globals.__baseUrl__) {
    console.log("cleared bag")
  }
   /* if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//span[@class='js-cart-size']")).isDisplayed()).not.toBeTruthy();
    console.log("cleared the bag items")
  }else{
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__count']")).isDisplayed()).not.toBeTruthy();
    console.log("cleared the bag items")
  } */
})


afterAll(async () => {
  await driver.quit()
})
