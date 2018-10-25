import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard,guestuser,logindetails } from '../../../testdata/jcrewTestData';
import element from '../../../util/commonutils';
import {loginFromHomePage, clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';

const { By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('Verify User is able to login with valid user credentials', async () => {
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//span[@class='primary-nav__text' and contains(text(), 'sign in')]")).click()
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys(logindetails.username4)
   await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys(logindetails.password4)
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
   console.log("login is completed")
   await driver.sleep(1000)
   await driver.navigate().to(globals.__baseUrl__+"/CleanPersistentCart.jsp")
   await driver.sleep(10000)
  });

test('Go to shoppingBag page and select product', async () => {
  await driver.findElement(By.xpath("//span[text()='menu']")).click()
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@data-department='women']")).click()
    await	driver.sleep(1000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await driver.sleep(1000)
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(1000)
       })
       } catch (err)
      { }
    await driver.sleep(1000)
    await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[3]")).click()
    await driver.sleep(1000)
  await driver.executeScript('window.scrollTo(0, 700)')
  await driver.sleep(1000)
  await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click();
  await driver.sleep(1000)

});

test('Add product to bag', async () => {
  await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click();
  await driver.sleep(3000)
  await driver.executeScript('window.scrollTo(0, -700)')
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//span[@class='icon-header icon-header-bag icon-bag']")).click();
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h2[text()='Summary']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();
  await driver.sleep(1000)
});

// In Shipping page, Verify Shipping methods

/*test('selecting a Shipping methods', async () => {

  // Economy
  await driver.findElement(By.xpath("//input[@id='method4']")).click();
  await driver.findElement(By.xpath("//input[@id='method4']")).isDisplayed()
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();

});*/

test('selecting a Paypal method', async () => {

  await driver.findElement(By.xpath("//a[text()='Billing']")).click()
  await driver.sleep(1000)
          await driver.findElement(By.xpath("//input[@id='paypalPayment']")).isDisplayed();
          console.log("Paypal window is not opening in mobile mode")
          /*await driver.sleep(1000)
          await driver.switchTo().frame(await driver.findElement(By.xpath("//iframe[@class='xcomponent-component-frame xcomponent-visible']")))

        let url = await driver.getCurrentUrl();
        console.log(url);
        if (url.indexOf("www.jcrew.com") > -1) {
            await driver.wait(until.elementLocated(paypalButton), 50000).isDisplayed();
        }else{

          await driver.wait(until.elementLocated(paypalButton), 50000).click();
          await driver.switchTo().defaultContent();

          await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
              driver.switchTo().window(allhandles[allhandles.length - 1]);

          });

          console.log("paypal window")
//            let getWindowHandle = await driver.getWindowHandles();
          await driver.sleep(30000)
          await driver.wait(until.elementLocated(loginButton), 50000).click();
          console.log("clicked login")
          await driver.sleep(30000)
          //await driver.wait(until.elementLocated(driver.findElement(By.id('email'))), 50e3).clear();
          await driver.wait(until.elementLocated(email), 50000).sendKeys(Paypal.Email);
          console.log("entered uname")

          await driver.wait(until.elementLocated(next), 50000).click();
          console.log("clicked next")
          await driver.sleep(10000)
          await driver.wait(until.elementLocated(password),50000).sendKeys(Paypal.Password);
          console.log("entered password")
          await driver.sleep(30000)
          await driver.wait(until.elementLocated(btnLogin),2000).click();
          await driver.sleep(60000)

          //await driver.findElement(submitButton).click()
          await driver.wait(until.elementLocated(submitButton), 50000).click();
          console.log("clicked continue")

          await driver.switchTo().defaultContent();
          await driver.findElement(xpath("//*[@id='billing-details']/div/div[1]/div[1]/div/span[2]")).isDisplayed();
          console.log("user is on Review page");*/
//        }
});
