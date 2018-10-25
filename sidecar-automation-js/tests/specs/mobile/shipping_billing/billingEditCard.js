import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
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
  /*await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='main__button-continue-old']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();
  await driver.sleep(1000)*/
});

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

test('Verify Editing a credirCard', async () => {
  await driver.findElement(By.xpath("//a[text()='Billing']")).click()
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@class='item-edit']")));
  await driver.sleep(1000)
  //await driver.findElement(By.xpath("//li[@id='cardId1']")).isDisplayed();
  await driver.findElement(By.xpath("//a[@class='item-edit']")).isDisplayed();
  await driver.findElement(By.xpath("//a[@class='item-edit']")).click();
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).clear().sendKeys("4111111111111111");
  await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys("123");
  await driver.findElement(By.id('expirationMonth')).sendKeys('06');
  await driver.findElement(By.id('expirationYear')).sendKeys('2028');
  await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys("tester");
  await driver.sleep("1000")
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@data-bma='update_payment_method']")));
  await driver.sleep("1000")
  await driver.findElement(By.xpath("//a[@data-bma='update_payment_method']")).click()
  await driver.sleep("1000")
});
