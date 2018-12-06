import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { mergeButton } from '../../../pageObjects/ShoppingBagObj';
import { waitSeconds } from '../../../util/commonutils';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')
let subTotalOnReview ;
let shippingOnReview ;
let taxOnReview;
let totalOnReview;
let currentUrl;
let orderNumberLet;

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Express User Checkout and verifying order summary and order history', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await waitSeconds(1)
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password);
  await waitSeconds(2)
  await mergeButton();
  await waitSeconds(2)
   subTotalOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]")).getText();
   shippingOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]")).getText();
   taxOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item clearfix']/span[2]")).getText();
   totalOnReview = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]")).getText();
   currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

    try {
      await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
        //        console.log("inside securitycode")
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
    } catch (err)
    {  }
    orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
    console.log("order Id  > " + orderNumberLet)
    await waitSeconds(1)
  }
})

test('Verify Order Summary', async () => {
   let currentUrl = await driver.getCurrentUrl();
   if (currentUrl.indexOf("https://or.") > -1) {
   let subTotalOnOrderComplete = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]")).getText();
   let shippingOnOrderComplete = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]")).getText();
   let taxOnOrderComplete = await driver.findElement(By.xpath("//ul/li[@class='summary-item clearfix']/span[2]")).getText();
   let totalOnOrderComplete = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]")).getText();

    if (subTotalOnReview == subTotalOnOrderComplete && shippingOnReview == shippingOnOrderComplete && taxOnReview == taxOnOrderComplete && totalOnReview == totalOnOrderComplete) {
      console.log("Order summary verified");
      await waitSeconds(1)
      let url = await driver.getCurrentUrl();
      if (url.includes("factory")) {
        const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
        expect(loggedInUser).toBeTruthy()
        await driver.actions().mouseMove(loggedInUser).perform();
        await waitSeconds(2)
      } else {
        const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
        expect(loggedInUser).toBeTruthy()
        await driver.actions().mouseMove(loggedInUser).perform();
        await waitSeconds(2)
      }
      const orderHistory = await driver.findElement(By.xpath("(//a[text()='Order History'])[1]"))
      expect(orderHistory).toBeTruthy()
      orderHistory.click()
      await waitSeconds(2)
     
      const orderNum = await driver.findElement(By.xpath("//div[@class='order-history--overview-item overview--order-number']"))
      expect(orderNum).toBeTruthy()
      let getOrderNum = await driver.findElement(By.xpath("//div[@class='order-history--overview-item overview--order-number']")).getText()
      console.log("placed order is displaying in order history" + getOrderNum)
      getOrderNum.includes(orderNumberLet)
     
      const showOrderDetails = await driver.findElement(By.xpath("//a[text()='Show Order Details']"))
      expect(showOrderDetails).toBeTruthy()
      showOrderDetails.click()
      await waitSeconds(1)
     
      const hideOrderDetails = await driver.findElement(By.xpath("(//a[text()='Hide Order Details'])[1]"))
      expect(hideOrderDetails).toBeTruthy()

      const shippingSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Shipping Summary']"))
      expect(shippingSummary).toBeTruthy()

      const paymentSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Payment Summary']"))
      expect(paymentSummary).toBeTruthy()

      const orderSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Order Summary']"))
      expect(orderSummary).toBeTruthy()

      await waitSeconds(1)

      hideOrderDetails.click()
      await waitSeconds(1)

      const bayNoteRecomendations = await driver.findElement(By.xpath("//div[@class='c-order-history-recs']/div[@class='c-order-history-recs__products']"))
      expect(bayNoteRecomendations).toBeTruthy()

      console.log("Order history verified")
      await waitSeconds(1)
    }
  }else{

  }
})

afterAll(async () => {
  await driver.quit()
})
