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

  test('Verify Context Chooser functionality ', async () => {
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(1000)
       })
       } catch (err)
      { }
    let currentUrl = await driver.getCurrentUrl();
    if(currentUrl.includes("factory")){
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
    }else{
      await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
      await driver.sleep(5000)
      await driver.executeScript('window.scrollTo(0, 5000)')
      await driver.sleep(3000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//span[text()='Ship to']")));
      await driver.sleep(1000)
      let countryName = await driver.findElement(By.xpath("//a[@aria-label='Change country for shipping']")).getText()
      if(countryName=="United States"){
        await driver.findElement(By.xpath("//a[@aria-label='Change country for shipping']")).click()
      }

    }
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//*[text()='UNITED STATES & CANADA']")).click()
    // Select Canada
    await driver.findElement(By.xpath("//span[text()='Canada']")).click()
      await driver.sleep(5000)
      let url = await driver.getCurrentUrl();
      if (url.indexOf("ca") > -1) {
        console.log('user is on Canada context')
      }
  })

  afterAll(async () => {
    await driver.quit()
  })
