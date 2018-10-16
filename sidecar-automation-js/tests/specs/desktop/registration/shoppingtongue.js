import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import {productArrayPage,addProductToBag} from '../../../pageObjects/arraypage';
import { globals } from '../../../jestJcrewQaConfig';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
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

test('Add product to bag', async () => {
  await driver.sleep(10000);
await clearBagItems()
await driver.sleep(12000);
await productArrayPage()
await addProductToBag()
});

test('verify shopping tongue issue was not there', async () => {
  let url = await driver.getCurrentUrl();
  if(url.includes("factory")){
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[text()='bag']"))).perform();
    await driver.sleep(1000)
    const shoppingTonge = await driver.findElement(By.xpath("//div[@class='c-header__minibag is-active']"))
    expect(shoppingTonge).toBeTruthy()
    console.log("shopping tonge is displayed")
  }else{
    await driver.sleep(3000)
    await driver.actions().mouseMove(await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button']"))).perform();
    await driver.sleep(1000)
    const shoppingTonge = await driver.findElement(By.xpath("//*[@class='nc-nav__bag__panel is-open']"))
    expect(shoppingTonge).toBeTruthy()
    console.log("shopping tonge is displayed")
  }

});


afterAll(async () => {
  await driver.quit()
})
