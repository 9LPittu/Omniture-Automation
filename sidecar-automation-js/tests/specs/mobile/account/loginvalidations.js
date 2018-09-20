import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems,signOutFromApplication} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Verify User is able to login with valid user credentials', async () => {
      await loginFromHomePage(logindetails.username, logindetails.password);
      await signOutFromApplication()
   })

   test('Verify User is not able to login with invalid user credentials, display error message', async () => {
       await driver.sleep(3000)
       await driver.navigate().to(globals.__baseUrl__)
       let currentUrl = await driver.getCurrentUrl();
       if(currentUrl.includes("factory")){
         await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
       }else{
         await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
         driver.sleep(2000)
         await driver.findElement(By.xpath("//h3[text()='Sign in']")).click()
       }
       await driver.sleep(2000)
       await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys("dummyemail@gmail.com")
       await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys("123456ab")
       await driver.sleep(2000)
       await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
       await driver.sleep(2000)
       const errorMsg = await driver.findElement(By.className('js-invalid-msg is-important'))
       expect(errorMsg).toBeTruthy()
        })

        afterAll(async () => {
          await driver.quit()
        })
