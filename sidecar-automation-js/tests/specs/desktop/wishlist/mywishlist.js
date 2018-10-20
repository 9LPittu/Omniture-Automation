import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {productArrayPage} from '../../../pageObjects/arraypage';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
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
await driver.sleep(3000)
//await addProductToWishList();
//console.log('1')
  await  productArrayPage()
 // console.log('2')
  await closeIconInPAP()
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
  await driver.sleep(1000)
  await driver.findElement(By.id("btn__wishlist-wide")).click()
  await driver.sleep(3000)

});

test('Go to wish list to after adding the product to wishlist', async () => {
  let url = await driver.getCurrentUrl();
  if(url.includes("factory")){
  const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
  expect(loggedInUser).toBeTruthy()
  await driver.actions().mouseMove(loggedInUser).perform();
  await driver.sleep(2000)
  const wishlist = await driver.findElement(By.xpath("//dd/a[text()='Wishlist']"))
  expect(wishlist).toBeTruthy()
  wishlist.click()
  }else{
  const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
  expect(loggedInUser).toBeTruthy()
  await driver.actions().mouseMove(loggedInUser).perform();
  await driver.sleep(2000)
  const wishlist = await driver.findElement(By.xpath("//li/a[text()='Wishlist']"))
  expect(wishlist).toBeTruthy()
  wishlist.click()
  }
});

test('verify the product has been added to wishlist or not', async () => {
expect(await driver.findElement(By.xpath("//li[contains(@id,'item')]")).isDisplayed()).toBeTruthy();
});
/*export const addProductToWishList = async () =>{
  await  productArrayPage()
  await closeIconInPAP()
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
  await driver.sleep(1000)
  await driver.findElement(By.id("btn__wishlist-wide")).click()
  await driver.sleep(3000)
}*/
afterAll(async () => {
  await driver.quit()
})
