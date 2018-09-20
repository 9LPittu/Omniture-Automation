import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
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

//img[@src='https://static.cdn.responsys.net/i5/responsysimages/jcrew/contentlibrary/!forms/jc/prefcenter/images/subscribe_button.gif']
test('verify the email preferences section', async () => {
  await driver.sleep(5000)
await driver.findElement(By.xpath("//a[text()='Email Preferences']")).click();
await driver.sleep(2000);
//await driver.findElement(By.xpath("//section[@class='glb-long-grey-line']/following-sibling::section//following-sibling::p/a/img")).click();
await driver.switchTo().frame(await driver.findElement(By.id("emailSub")));
const subscribebutton = await driver.findElement(By.xpath("//section[@class='glb-long-grey-line']/p/a/img"));
expect(subscribebutton.isDisplayed()).toBeTruthy();
subscribebutton.click()
await driver.sleep(2000)

});

afterAll(async () => {
  await driver.quit()
})