import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard} from '../../../testdata/jcrewTestData';
import {productArrayPage,addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage'
import {loginInAfterCheckoutPage} from '../../../pageObjects/loginPageObj'
import {mergeButton} from '../../../pageObjects/ShoppingBagObj'

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Express User Checkout and verifying order summary and order history', async () => {
    await productArrayPage();
    await addProductToBag();
    await verifyAndClickOnBag();
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
    await driver.sleep(1000)
    await loginInAfterCheckoutPage(logindetails.username1,logindetails.password1);
    await driver.sleep(5000)
    await mergeButton();
    await driver.sleep(2000)
let subTotalOnReview  = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]")).getText();
let shippingOnReview  = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]")).getText();
let taxOnReview  = await driver.findElement(By.xpath("//ul/li[@class='summary-item clearfix']/span[2]")).getText();
let totalOnReview  = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]")).getText();
let currentUrl = await driver.getCurrentUrl();
if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout

 try {
         await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
    //        console.log("inside securitycode")
            securitycode.sendKeys(creditcard.pin)
           })

      } catch (err )
      { }
       await driver.sleep(3000)
          if (currentUrl.indexOf("factory.jcrew.com") > -1) {
            console.log(">> inside factory")
            await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
          } else {
            await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
          }
          await driver.sleep(4000)
          const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
          expect(bizrate).toBeTruthy()
          bizrate.click()
          await driver.sleep(2000)
          let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()
         console.log("order Id  > " + orderNumberLet)
         await driver.sleep(1000)

         let subTotalOnOrderComplete  = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]")).getText();
         let shippingOnOrderComplete  = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]")).getText();
         let taxOnOrderComplete  = await driver.findElement(By.xpath("//ul/li[@class='summary-item clearfix']/span[2]")).getText();
         let totalOnOrderComplete = await driver.findElement(By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]")).getText();

         if(subTotalOnReview==subTotalOnOrderComplete&&shippingOnReview==shippingOnOrderComplete&&taxOnReview==taxOnOrderComplete&&totalOnReview==totalOnOrderComplete){
           console.log("Order summary verified");


         await driver.sleep(1000)
         await driver.actions().mouseMove(await driver.findElement(By.id("c-header__userpanelrecognized"))).perform();
         //class js-signout__link --button
         await driver.sleep(2000)

         const orderHistory = await driver.findElement(By.xpath("(//a[text()='Order History'])[1]"))
         expect(orderHistory).toBeTruthy()
         orderHistory.click()
         await driver.sleep(5000)

         const recentOrder = await driver.findElement(By.xpath("//div[@class='order-history--order recent-order scoh2']"))
         expect(recentOrder).toBeTruthy()

         const orderNum = await driver.findElement(By.xpath("//div[@class='order-history--overview-item overview--order-number']"))
         expect(orderNum).toBeTruthy()

       let getOrderNum =   await driver.findElement(By.xpath("//div[@class='order-history--overview-item overview--order-number']")).getText()

           console.log("placed order is displaying in order history"+getOrderNum)
           getOrderNum.includes(orderNumberLet)

       const showOrderDetails = await driver.findElement(By.xpath("//a[text()='Show Order Details']"))
       expect(showOrderDetails).toBeTruthy()
       showOrderDetails.click()

       await driver.sleep(1000)

       const hideOrderDetails = await driver.findElement(By.xpath("//a[text()='Hide Order Details']"))
       expect(hideOrderDetails).toBeTruthy()

       const shippingSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Shipping Summary']"))
       expect(shippingSummary).toBeTruthy()

       const paymentSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Payment Summary']"))
       expect(paymentSummary).toBeTruthy()

       const orderSummary = await driver.findElement(By.xpath("//div[@class='order-history--details-summary']/div[text()='Order Summary']"))
       expect(orderSummary).toBeTruthy()

       await driver.sleep(1000)

       hideOrderDetails.click()

       await driver.sleep(1000)

       const bayNoteRecomendations = await driver.findElement(By.xpath("//div[@class='c-order-history-recs']/div[@class='c-order-history-recs__products']"))
       expect(bayNoteRecomendations).toBeTruthy()

       console.log("Order history verified")
       await driver.sleep(1000)

         }

      }
   })

   
   afterAll(async () => {
    await driver.quit()
  })
