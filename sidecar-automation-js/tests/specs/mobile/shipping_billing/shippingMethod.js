import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
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

test('Verify Shipping methods', async () => {
  // OverNight
  await driver.findElement(By.xpath("//input[@id='method0']")).click();
  await driver.findElement(By.xpath("//input[@id='method0']")).isDisplayed()

  // Saturday
  await driver.findElement(By.xpath("//input[@id='method1']")).click();
  await driver.findElement(By.xpath("//input[@id='method1']")).isDisplayed()

  // Expedited
  await driver.findElement(By.xpath("//input[@id='method2']")).click();
  await driver.findElement(By.xpath("//input[@id='method2']")).isDisplayed()

  // Standard
  await driver.findElement(By.xpath("//input[@id='method3']")).click();
  await driver.findElement(By.xpath("//input[@id='method3']")).isDisplayed()

  // Economy
  await driver.findElement(By.xpath("//input[@id='method4']")).click();
  await driver.findElement(By.xpath("//input[@id='method4']")).isDisplayed()
  //await driver.findElement(By.xpath("//a[@id='main__button-continue-old']")).click();

});
