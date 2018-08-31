import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {logindetails}

const { Builder, By, Key, until } = require('selenium-webdriver')

 test('title is correct', async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

   test('Verify User is not able to login with invalid user credentials, display error message', async () => {

         await driver.findElement(By.xpath(".//*[text()='sign in']")).click()
         await driver.sleep(3000)
         //await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys(logindetails.username5)
         //await driver.sleep(1000)
         const forgotPasswordLink = await driver.findElement(By.xpath("//a[text()='I forgot my password!']"))
         expect(forgotPasswordLink).toBeTruthy()
         console.log('forgot password link is displayed');
         forgotPasswordLink.click()
         await driver.sleep(1000)
         await driver.findElement(By.xpath("//input[@name='loginUser']")).sendKeys(logindetails.username5)
         await driver.sleep(1000)
         const sendNewPassword = await driver.findElement(By.xpath("//a[text()='Send Me a New Password']"))
         sendNewPassword.click()
         await driver.sleep(1000)
         const forgotPassword = await driver.findElement(By.xpath("//h2[text()='Forgot Password?']"))
         expect(forgotPassword).toBeTruthy()
         await driver.sleep(1000)
         const newPasswordText = await driver.findElement(By.xpath("//section[@id='registered']/p")).getText()
         expect(newPasswordText).toBeTruthy()
         newPasswordText.includes(logindetails.username5)
         console.log("new password sent to user email")
    })
