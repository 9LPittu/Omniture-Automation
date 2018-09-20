import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';


const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

   test('Verifying forgot password functionality', async () => {
       await driver.sleep(1000)
       let currentUrl = await driver.getCurrentUrl();
       if(currentUrl.includes("factory")){
         await driver.sleep(1000)
         await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
       }else{
         await driver.sleep(1000)
         await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
         driver.sleep(4000)
         await driver.findElement(By.xpath("//h3[text()='Sign in']")).click()
         driver.sleep(2000)
       }
       await driver.sleep(3000)
       const forgotPasswordLink = await driver.findElement(By.xpath("//a[text()='I forgot my password!']"))
       expect(forgotPasswordLink).toBeTruthy()
       console.log('forgot password link is displayed');
       forgotPasswordLink.click()
       await driver.findElement(By.xpath("//input[@name='loginUser']")).sendKeys(logindetails.username5)
       await driver.sleep(1000)
       const sendNewPassword = await driver.findElement(By.xpath("//a[text()='Send Me a New Password']"))
       sendNewPassword.click()
       await driver.sleep(1000)
       const forgotPassword = await driver.findElement(By.xpath("//h2[text()='Forgot Password?']"))
       expect(forgotPassword).toBeTruthy()
       await driver.sleep(1000)
       const message = await driver.findElement(By.xpath("//p[@class='page-msg']")).getText()
       expect(message).toBeTruthy()
       if(message.includes(logindetails.username5)){
         console.log("new password has been sent to user email id")
       }
       await driver.sleep(1000)
        })

        afterAll(async () => {
          await driver.quit()
        })
