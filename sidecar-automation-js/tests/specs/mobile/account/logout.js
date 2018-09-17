import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Verify User is able to login with valid user credentials', async () => {
      await loginFromHomePage(logindetails.username, logindetails.password);
      await driver.sleep(10000)
      const myaccount = await driver.findElement(By.xpath("//span[@id='c-header__userpanelrecognized']"))
      expect(myaccount).toBeTruthy()
      await driver.sleep(1000)
      // Rewards
      await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav")).click()
      //await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[@id='c-header__userpanelrecognized']"))).perform();
      await driver.sleep(2000)
      const signOut = await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav/ul/li[9]/a"))
      //const signOut = await driver.findElement(By.xpath("//a[@class='js-signout__link']"))
      expect(signOut).toBeTruthy()
      signOut.click()
      await driver.sleep(1000)
      const signIn = await driver.findElement(By.xpath(".//span[text()='sign in']"))
      expect(signIn).toBeTruthy()
      console.log("sign out from the application successfully")
      expect(await driver.getTitle()).toMatch('J.Crew')
   })

   afterAll(async () => {
    await driver.quit()
  })