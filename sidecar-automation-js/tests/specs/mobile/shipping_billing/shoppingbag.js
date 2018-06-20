import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import element from '../../../util/commonutils';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';


const { By, Key, until } = require('selenium-webdriver')



test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('Go to shoppingBag page and select product', async () => {
  await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']")).click();
  await driver.findElement(By.xpath("//*[@class='js-header__search__input header__search__input']")).sendKeys('F4212');
  await driver.findElement(By.xpath("//*[@class='icon-searchtray icon-searchtray-search']")).click();
  await driver.findElement(By.xpath("//a[@class='product-tile__link']/img")).click();
  await driver.sleep(7000)


});
 test('select product id and goto shoppingbag page', async () =>{
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")));
await driver.sleep(5000);
await driver.findElement(By.xpath("//*[@id='c-product__sizes']/div/div/ul/li[10]")).click();
await driver.sleep(5000)
await driver.findElement(By.id("btn__add-to-bag-wide")).click();
await driver.sleep(5000)
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
await driver.sleep(5000);
await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")));
//await driver.findElement(By.css(".js-cart-size")).click();
//await driver.findElement(By.xpath("//a[@id='js-header__cart']/span[@class='primary-nav__text']")).click();
await driver.sleep(7000)
//a[@id='button-checkout']
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='button-checkout']")));
await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();
//await clickOnCheckout();
await driver.sleep(5000)



 });

test('click on login as Guest button', async () =>{
await driver.findElement(By.xpath("//form[@id='frmGuestCheckOut']/a")).click();

});
