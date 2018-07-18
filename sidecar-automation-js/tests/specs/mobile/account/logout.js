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
      await driver.sleep(3000)
      await driver.findElement(By.xpath(".//span[text()='My Details']")).click()
      await driver.findElement(By.xpath(".//li[text()='Sign Out']")).click()
      await driver.sleep(3000)
    } else {
      await driver.sleep(5000)
      //await driver.findElement(By.xpath(".//span[text()='My Account']")).click()
      //await driver.findElement(By.xpath(".//span[text()='My Details']")).click()
      //const signOut = await driver.findElement(By.xpath(".//li[text()='Sign Out']"))
      const signOut = await driver.findElement(By.xpath("//*[@id='main_inside']/tbody/tr/td[1]//a[contains(text(),'Sign Out')]")).click()
      //expect(signOut).toBeTruthy()
//      signOut.click()
      await driver.sleep(3000)
      expect(await driver.getTitle()).toMatch('J.Crew')
  //    await driver.findElement(By.xpath("//*[@id='nav__ul']/li[10]")).click()

    }
   })
