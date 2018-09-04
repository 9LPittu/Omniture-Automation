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

  test('Store Locator link should navigate to store search page', async () => {
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//span[text()='menu']")).click()
  await driver.sleep(2000)
  // Store locator link
  await driver.findElement(By.xpath("//a[text()='STORE LOCATOR']")).click()
  await driver.sleep(4000)
  // Capturing the Current URL
  let url = await driver.getCurrentUrl();
  if (url.indexOf("stores.factory.jcrew.com") > -1) {
    console.log("We are in Factory Store search page")
  }
    else
  {
    console.log("We are in Jcrew Store search page")
  }

})
