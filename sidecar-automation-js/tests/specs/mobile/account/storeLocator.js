import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Store Locator link should navigate to store search page', async () => {
    let url = null;
    url = await driver.getCurrentUrl();
    if(url.includes("factory")){
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//span[text()='menu']")).click()
  await driver.sleep(2000)
  // Store locator link
  await driver.findElement(By.xpath("//a[text()='STORE LOCATOR']")).click()
  await driver.sleep(1000)
}else{
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  driver.findElement(By.xpath("//li[text()='Find a J.Crew Store']")).click()
  await driver.sleep(1000)
}
await driver.sleep(1000)
  // Capturing the Current URL
 url = await driver.getCurrentUrl();
  if (url.indexOf("stores.factory.jcrew.com") > -1) {
    console.log("We are in Factory Store search page")
  }
    if(url.indexOf("stores.jcrew.com") > -1)
  {
    console.log("We are in Jcrew Store search page")
  }

})

afterAll(async () => {
  await driver.quit()
})
