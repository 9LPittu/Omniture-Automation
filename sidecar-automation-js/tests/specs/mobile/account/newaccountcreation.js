import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
})


  test('test creation of new account', async () => {

// let i = 0;
// for (i = 1; i < 100; i++) {
var x = Math.floor((Math.random() * 1000000) + 1);
let userName = "AutomationTest"+x
let email = "AutomationTest"+x+"@gmail.com"
    await driver.navigate().to(globals.__baseUrl__+"/r/login")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='register-form__label--countrySection']")));
    await driver.sleep(2000)
    //await driver.executeScript('window.scrollTo(100, 0)')
    //await driver.sleep(1000)
    await driver.findElement(By.xpath("(//*[@id='loyaltysignup'])[1]")).click()
    await driver.sleep(12000)
   console.log ("User created >> " + userName)
    // const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
    // expect(userPannel).toBeTruthy()
    // userPannel.click()
    // await driver.sleep(3000)
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      await driver.sleep(1000)
      // Rewards
      await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav")).click()
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav/ul/li[9]/a"))
      expect(signOut).toBeTruthy()
      signOut.click()
      await driver.sleep(1000)
    } else {
      // Rewards
      await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav")).click()
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav/ul/li[9]/a"))
      expect(signOut).toBeTruthy()
      signOut.click()
      await driver.sleep(1000)
    }
   })
