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

  test('Verify User is able to login with valid user credentials', async () => {
      await loginFromHomePage(logindetails.username, logindetails.password);

      let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      await driver.sleep(2000)
      await driver.findElement(By.xpath(".//span[text()='My Account']")).click()
      await driver.sleep(1000)
      const signOut = await driver.findElement(By.xpath("(//a[text()='Sign Out'])[3]"))
      expect(signOut).toBeTruthy()
      signOut.click()
    //  await driver.findElement(By.xpath(".//span[text()='My Details']")).click()
    //  await driver.findElement(By.xpath(".//li[text()='Sign Out']")).click()
      await driver.sleep(3000)
    } else {
      await driver.sleep(10000)
      const myaccount = await driver.findElement(By.xpath("//span[@id='c-header__userpanelrecognized']"))
      expect(myaccount).toBeTruthy()
      await driver.sleep(1000)
      // Rewards
      await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav")).click()
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav/ul/li[9]/a"))
      expect(signOut).toBeTruthy()
      signOut.click()
      await driver.sleep(1000)
    }
   })

   test('Verify User is not able to login with invalid user credentials, display error message', async () => {
       await driver.sleep(3000)
       await driver.navigate().to(globals.__baseUrl__)
       await driver.findElement(By.xpath(".//*[text()='sign in']")).click()
       await driver.sleep(4000)
       await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys("dummyemail@gmail.com")
       await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys("123456ab")
       await driver.sleep(2000)
       await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
       await driver.sleep(2000)
       const errorMsg = await driver.findElement(By.className('js-invalid-msg is-important'))
       expect(errorMsg).toBeTruthy()

        })
