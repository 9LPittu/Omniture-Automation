import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {productArrayPage} from '../../../pageObjects/arraypage';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});
test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
    console.log('user login succesfully')
  }else if((url.indexOf("or.jcrew.com") > -1 )){

  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("or.factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_gold.username,factory_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("https://factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_prod.username,factory_prod.password)
  console.log('user login succesfully')
  }

});

test('Add product to wishlist', async () => {
//.c-nav__userpanel-item--wishlist
await driver.sleep(1000)
await addProductToWishList();

});

test('Go to wish list to after adding the product to wishlist', async () => {
//.c-nav__userpanel-item--wishlist
await driver.sleep(2000)
await driver.actions().mouseMove(await driver.findElement(By.id("c-header__userpanelrecognized"))).perform();
//class js-signout__link --button
await driver.sleep(2000)
await driver.findElement(By.css(".c-nav__userpanel-item--wishlist")).click();
await driver.sleep(2000)

});

test('verify the product has been added to wishlist or not', async () => {
expect(await driver.findElement(By.xpath("//li[contains(@id,'item')]")).isDisplayed()).toBeTruthy();
});


export const addProductToWishList = async () =>{
  await  productArrayPage()
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
  await driver.sleep(1000)
  await driver.findElement(By.id("btn__wishlist-wide")).click()
  await driver.sleep(3000)

}
