import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

 test('title is correct', async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

  test('Login with given username and password', async () => {
    let url = await driver.getCurrentUrl();
    await driver.manage().window().maximize()

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
