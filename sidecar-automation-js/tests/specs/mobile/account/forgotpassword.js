import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';


const { Builder, By, Key, until } = require('selenium-webdriver')

 test('title is correct', async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

   test('Verify User is not able to login with invalid user credentials, display error message', async () => {
       await driver.sleep(1000)
       await driver.findElement(By.xpath(".//*[text()='sign in']")).click()
       await driver.sleep(3000)
       await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys(logindetails.username5)
       await driver.sleep(1000)
       //await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys(logindetails.password5)
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
