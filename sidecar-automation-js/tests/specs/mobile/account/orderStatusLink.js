import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
})

  test('Verify Order status Link', async () => {
    await driver.sleep(2000)
    let url = null;
    url = await driver.getCurrentUrl();
    if(url.includes("factory")){
      await driver.executeScript('window.scrollTo(0, 15000)')
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h6[text()='Let Us Help You']")));
       driver.sleep(3000)
      await driver.findElement(By.xpath("//h6[text()='Let Us Help You']")).then(letushelp => {
       letushelp.click()
       driver.sleep(3000)
     })
     // click on Order status
    await driver.findElement(By.xpath("//div[text()='Order Status']")).click()
  }else{
    await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
    await driver.sleep(2000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h4[text()='How can we help?']")));
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//li[text()='Order Status?']")).click()
    await driver.sleep(1000)

  }
    await driver.sleep(1000)
    // Capturing the Current URL
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.includes("order_status")) {
      console.log("We are in Order Status page")
    }

  })

  afterAll(async () => {
    await driver.quit()
  })
