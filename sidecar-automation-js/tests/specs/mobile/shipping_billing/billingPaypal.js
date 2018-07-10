import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import element from '../../../util/commonutils';
import {loginFromHomePage, clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import { Billing } from '../../../testdata/jcrewTestData';

const { By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   })

 test('Verify User is able to login with valid user credentials', async () => {
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//span[@class='primary-nav__text' and contains(text(), 'sign in')]")).click()
   await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys("autouser3@example.org")
   await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys("test1234")
   await driver.sleep(5000)
   await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
   console.log("login is completed")
   await driver.sleep(8000)
  });

test('Go to shoppingBag page and select product', async () => {
  await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']")).click();
  await driver.findElement(By.xpath("//*[@class='js-header__search__input header__search__input']")).sendKeys('F4212');
  await driver.findElement(By.xpath("//*[@class='icon-searchtray icon-searchtray-search']")).click();
  await driver.sleep(4000)
  await driver.findElement(By.xpath("//a[@class='product-tile__link']/img")).click();
  await driver.sleep(7000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[@class='btn__label' and contains(text(),'4')]"))); 
  await driver.findElement(By.xpath("//span[@class='btn__label' and contains(text(),'4')]")).click();

});

test('Add product to bag', async () => {
  await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click();
  await driver.sleep(4000)
  await driver.findElement(By.xpath("//span[@class='icon-header icon-header-bag icon-bag']")).click();
  await driver.sleep(4000)
  await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();
  await driver.sleep(4000)
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();
  await driver.sleep(4000)
});

// In Shipping page, Verify Shipping methods

test('selecting a Shipping methods', async () => {

  // Economy
  await driver.findElement(By.xpath("//input[@id='method4']")).click();
  await driver.findElement(By.xpath("//input[@id='method4']")).isDisplayed()
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();

});

test('selecting a Paypal method', async () => {

          await driver.wait(until.elementLocated(paypalRadio), 50000).click();
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
          await driver.wait(until.elementLocated(email), 50000).sendKeys(Billing.Paypal.Email);
          console.log("entered uname")

          await driver.wait(until.elementLocated(next), 50000).click();
          console.log("clicked next")
          await driver.sleep(10000)
          await driver.wait(until.elementLocated(password),50000).sendKeys(Billing.Paypal.Password);
          console.log("entered password")
          await driver.sleep(30000)
          await driver.wait(until.elementLocated(btnLogin),2000).click();
          await driver.sleep(60000)

          //await driver.findElement(submitButton).click()
          await driver.wait(until.elementLocated(submitButton), 50000).click();
          console.log("clicked continue")

          await driver.switchTo().defaultContent();
          await driver.findElement(xpath("//*[@id='billing-details']/div/div[1]/div[1]/div/span[2]")).isDisplayed();
          console.log("user is on Review page");
        }
});
