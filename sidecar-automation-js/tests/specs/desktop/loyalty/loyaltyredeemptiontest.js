import { driver } from '../../../helpers';
import { load, closeIconInPAP ,loyaltyPointsRedeemValidation } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'
import { loginFromHomePage } from '../../../pageObjects/loginPageObj'
import { mergeButton } from '../../../pageObjects/ShoppingBagObj'

const { By } = require('selenium-webdriver')

let subTotalOnReview;
let shippingOnReview;
let taxOnReview;
let totalOnReview;
let currentUrl;
let orderNumberLet;

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
})

test('verifying loyalty redeemtion', async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {
    await loginFromHomePage(logindetails.loyaltyuser, logindetails.password);
    await productArrayPage();
    await closeIconInPAP()
    await addProductToBag();
    await verifyAndClickOnBag();
    await driver.sleep(1000)
    await loyaltyPointsRedeemValidation(currentUrl)
    //await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
   // await driver.sleep(1000)
    //try {
     // await driver.findElement(By.xpath("//*[@id='btn-redeem-points']")).click()
      //await driver.sleep(2000)
    //} catch (err) { }
    //await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
    //TODO - need to add the redeem assertion logic
    //await driver.sleep(5000)
    //await mergeButton();
    //await driver.sleep(2000)
    //subTotalOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]")).getText();
    //shippingOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]")).getText();
   // taxOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item clearfix']/span[2]")).getText();
   // totalOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]")).getText();
  } else {
    console.log("Assume loylaty is working on Production!")
  }
})

afterAll(async () => {
  await driver.quit()
})
