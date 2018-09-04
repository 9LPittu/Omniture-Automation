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

  test('Verify Context Chooser functionality ', async () => {
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(1000)
       })
       } catch (err)
      { }
      await driver.executeScript('window.scrollTo(0, 20000)')
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@class='footer__country-context__link']")));
      // Change Link for Country change
      await driver.findElement(By.xpath("//a[@class='footer__country-context__link']")).click()
      await driver.sleep(2000)
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section[1]/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(1000)
       })
       } catch (err)
      { }
      // Us and Canada option
      await driver.findElement(By.xpath("//*[text()='UNITED STATES & CANADA']")).click()
      // Select Canada
      await driver.findElement(By.xpath("//span[text()='Canada']")).click()
      await driver.sleep(5000)
      let url = await driver.getCurrentUrl();
      if (url.indexOf("ca") > -1) {
        console.log('user is on Canada context')
      }

  })
