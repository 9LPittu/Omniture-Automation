import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
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

test('click signout button in MyAccout', async () => {
//id c-header__userpanelrecognized --hover
  await driver.sleep(7000)
  await driver.actions().mouseMove(await driver.findElement(By.id("c-header__userpanelrecognized"))).perform();
//class js-signout__link --button
await driver.sleep(2000)
await driver.findElement(By.xpath("//a[@class='js-signout__link']")).click();
await driver.sleep(5000)

});

test('verify signout happened succesfully', async () => {
expect(await driver.findElement(By.xpath(".//span[text()='sign in']")).isDisplayed()).toBeTruthy();
  });
