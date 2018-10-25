import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard,guestuser,logindetails} from '../../../testdata/jcrewTestData';
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

test('Verify Shipping methods', async () => {
  await driver.findElement(By.xpath("//a[text()='Shipping Options']")).click()
  await driver.sleep(1000)
  // OverNight
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[1]")).click();
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[1]")).isDisplayed()

  // Saturday
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[2]")).click();
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[2]")).isDisplayed()

  // Expedited
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[3]")).click();
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[3]")).isDisplayed()

  // Standard
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[4]")).click();
  await driver.findElement(By.xpath("(//input[@name='shippingMethod'])[4]")).isDisplayed()

  // Economy


});
