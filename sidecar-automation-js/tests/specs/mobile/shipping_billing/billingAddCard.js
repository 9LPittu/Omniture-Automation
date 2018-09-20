import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard,logindetails,guestuser } from '../../../testdata/jcrewTestData';
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
   let currentUrl = await driver.getCurrentUrl()
   if(currentUrl.includes("factory")){
   await driver.findElement(By.xpath("//span[@class='primary-nav__text' and contains(text(), 'sign in')]")).click()
   await driver.sleep(1000)
 }else{
   await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("//h3[text()='Sign in']")).click()
 }
   await driver.sleep(2000)
   await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys(logindetails.username4)
   await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys(logindetails.password4)
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
   console.log("login is completed")
   await driver.sleep(1000)
   await driver.navigate().to(globals.__baseUrl__+"/CleanPersistentCart.jsp")
   await driver.sleep(10000)
  });

test('Go to shoppingBag page,select product and add to bag', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h2[text()='Summary']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();
  await driver.sleep(1000)
});

/*test('Add product to bag', async () => {
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
  /*await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();
  await driver.sleep(1000)
});*/

// In Shipping page, Verify Shipping methods

/*test('selecting a Shipping methods', async () => {

  // Economy
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[1]")).click();
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[1]")).isDisplayed()
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();
  await driver.sleep(1000)
});*/

test('Verify adding a credirCard', async () => {
  await driver.findElement(By.xpath("//a[text()='Billing']")).click()
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[@class='form-label']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//span[@class='form-label']")).click();
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys(creditcard.number);
  await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin);
  await driver.findElement(By.id('expirationMonth')).sendKeys(creditcard.expirationMonth);
  await driver.findElement(By.id('expirationYear')).sendKeys(creditcard.expirationYear);
  await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys(creditcard.nameOnCard);
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@class='button-submit']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@class='button-submit']")).click();
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//div[@class='payment-method first-card same-billing last']")).click();

});
