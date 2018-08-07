import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Verify Order status Link', async () => {
    await driver.sleep(2000)
    //await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.executeScript('window.scrollTo(0, 1500)')
    await driver.sleep(2000)
    //await driver.executeScript('window.scrollTo(0, 800)')
    try {
      // click on Order status
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[@id='global__footer']/div/div[2]/div/div[1]/div/h6")));
      await driver.findElement(By.xpath("//*[@id='global__footer']/div/div[2]/div/div[1]/div/h6")).then(orderStatus => {
       orderStatus.click()
       driver.sleep(3000)
     })
     } catch (err)
    { }

     await driver.findElement(By.xpath("//*[@id='global__footer']/div/div[2]/div/div[1]/div/ul/li[1]/a")).click()

    // Capturing the Current URL
    let url = await driver.getCurrentUrl();
    if (url.indexOf("factory.jcrew.com") > -1) {
      console.log("We are in Factory Order Status page")
    }
      else
    {
      console.log("We are in Jcrew Order Status page")
    }

  })
