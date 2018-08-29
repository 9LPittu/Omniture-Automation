import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(3000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

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
     test('Verify My Account page', async () => {
       driver.sleep(10000)
       let url = await driver.getCurrentUrl();
       if(url.includes("l/account/rewards")){
         expect(url.includes("l/account/rewards")).toBeTruthy()
         console.log("User navigated to my account page")
       }
       //verify my account link
       const myaccount = await driver.findElement(By.xpath("//h2/span[text()='My Account']"))
       expect(myaccount).toBeTruthy()
       const myrewards = await driver.findElement(By.xpath("//li/a[text()='My Rewards']"))
       expect(myrewards).toBeTruthy()
       const yourrewardstab = await driver.findElement(By.xpath("//div[text()='Your J.Crew Rewards']"))
       expect(yourrewardstab).toBeTruthy()
       const yourbenefitstab = await driver.findElement(By.xpath("//div[text()='Your Benefits']"))
       expect(yourbenefitstab).toBeTruthy()
       const youractivitytab = await driver.findElement(By.xpath("//div[text()='Your Activity']"))
       expect(youractivitytab).toBeTruthy()
       console.log("Your Rewards, Your Benefits and Your Activity tabs are displayed on account rewars page");
    })
