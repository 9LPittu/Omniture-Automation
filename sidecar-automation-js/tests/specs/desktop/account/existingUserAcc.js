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

  test('Creating of an account with existing user account details', async () => {
    driver.sleep(2000);
    try {
      await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
       privacyPolicyClose.click()
       driver.sleep(3000)
     })
     } catch (err)
    { }

  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest"+x
  let email = "AutomationTest"+x+"@gmail.com"
    await driver.navigate().to(globals.__baseUrl__+"/r/login")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
    await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()

    await driver.sleep(2000)
    await driver.navigate().refresh()
    await driver.manage().window().maximize()
    await driver.sleep(2000)
    console.log ("User created >> " + userName)
    await driver.sleep(2000)
   const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
   expect(userPannel).toBeTruthy()
   userPannel.click()
    await driver.sleep(3000)
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      let signout = await driver.findElement(By.xpath("/html/body/div[3]/div[2]/div/div[2]/div/div[9]/a"))
      signout.click()
      console.log("logout success..")
      await driver.sleep(3000)
      await driver.navigate().to(globals.__baseUrl__+"/r/login")
      await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
      await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
      await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
      await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
      await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()
      await driver.sleep(2000)
      let errorMsg = await driver.findElement(By.xpath("//*[@id='unregistered-email']/span"))
      expect(errorMsg).toBeTruthy()
    } else {
    await driver.findElement(By.xpath(".//span[text()='My Account']")).click()
    await driver.findElement(By.xpath(".//li[text()='Sign Out']")).click()
    await driver.sleep(3000)
    await driver.navigate().to(globals.__baseUrl__+"/r/login")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
    await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()
    await driver.sleep(2000)
    let errorMsg = await driver.findElement(By.xpath("//*[@id='unregistered-email']/span"))
    expect(errorMsg).toBeTruthy()
   }

})
