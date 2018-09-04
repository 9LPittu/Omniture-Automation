import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
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

test('verify myDeatils section', async () => {
await driver.sleep(5000);
expect(await driver.findElement(By.xpath("//ul/li[2]/a[text()='My Details']")).isDisplayed()).toBeTruthy()
await driver.findElement(By.partialLinkText("My Details")).click();
await driver.sleep(2000);
let currentUrl = await driver.getCurrentUrl();
if(currentUrl.includes("r/account/details")){
  expect(await driver.findElement(By.xpath("//div/div/div[@class='account__my-details--header']"))).toBeTruthy()
  console.log("User navigates to My Details page")
}



});
