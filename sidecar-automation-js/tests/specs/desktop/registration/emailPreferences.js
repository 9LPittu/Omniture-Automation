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

//img[@src='https://static.cdn.responsys.net/i5/responsysimages/jcrew/contentlibrary/!forms/jc/prefcenter/images/subscribe_button.gif']
test('verify the email preferences section', async () => {
  await driver.sleep(7000)
await driver.findElement(By.partialLinkText("Email Preferences")).click();
await driver.sleep(7000);
//await driver.findElement(By.xpath("//section[@class='glb-long-grey-line']/following-sibling::section//following-sibling::p/a/img")).click();
await driver.switchTo().frame("emailSub");
expect(await driver.findElement(By.xpath("//section[@class='glb-long-grey-line']/following-sibling::section//following-sibling::p/a")).isDisplayed()).toBeTruthy();
await driver.executeScript("arguments[0].click();",driver.findElement(By.xpath("//section[@class='glb-long-grey-line']/following-sibling::section//following-sibling::p/a")));
await driver.sleep(8000)
});

/*
test('verify Email Preferences', async () => {
    await driver.sleep(10000);
    // Email preferences
    await driver.findElement(By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[3]/a")).click()
    await driver.sleep(6000);
    // Subscribe button
    driver.switchTo().window(await driver.findElement(By.xpath("/html/body/section[2]/p/a")).click());
    // Enter email id
    await driver.findElement(By.xpath("//*[@id='emailAdd']")).sendKeys("goldnonexpress1@example.org");
    // Confirm email id
    await driver.findElement(By.xpath("//*[@id='emailAddConf']")).sendKeys("goldnonexpress1@example.org");
    // Country
    await driver.Select(driver.findElement(By.xpath("//*[@id='country']"))).selectByValue("United States");
    // Zip code
    await driver.findElement(By.xpath("//*[@id='zipCode']")).sendKeys("60637");
    // Sign up me button
    await driver.findElement(By.xpath("//*[@id='signUpSubscribe']/input[9]")).click()
    // Thank you page
    const continueShop = await driver.findElement(By.xpath("/html/body/div[2]/div/div/div[3]/div[1]/a"))
    expect(continueShop).toBeTruthy()
    console.log("subscription is clicked")

}) 

*/
