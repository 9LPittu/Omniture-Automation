import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginPageObj';
import { jcrew_gold,jcrew_prod } from '../../../testdata/usercredentials';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});
test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
    console.log('user login succesfully')
  }else{

  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  console.log('user login succesfully')
  }

});

test('Add product to bag', async () => {
//.c-nav__userpanel-item--wishlist
await driver.sleep(7000);
await addProductTobag();

});

test('Go to oder history and verify the funtionality', async () => {
//.c-nav__userpanel-item--wishlist
await driver.sleep(7000)
await driver.actions().mouseMove(await driver.findElement(By.id("c-header__userpanelrecognized"))).perform();
//class js-signout__link --button
await driver.sleep(2000)
await driver.findElement(By.css(".c-nav__userpanel-item--order-history")).click();
await driver.sleep(5000)

});

test('verify the order history page navigation', async () => {
////li[contains(@id,'item')]
expect(await driver.findElement(By.id("page__order-history")).isDisplayed()).toBeTruthy();
});


export const addProductTobag = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()

  await driver.sleep(2000)
  await driver.navigate().refresh()
  await driver.sleep(3000)
  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
  await driver.sleep(3000)
  await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(3000)

}
