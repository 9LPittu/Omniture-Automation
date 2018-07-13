import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser, logindetails } from '../../../testdata/jcrewTestData';
import {  } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


 test('Search  is working', async () => {
 //  await driver.executeScript('window.scrollTo(0, 20000)')
     const currentUrl = await driver.get(`${__baseUrl__}/`)
     await driver.sleep(2000)
    const headerSearch = await driver.findElement(By.xpath("//div[contains(@class,'js-primary-nav__link--search')]"))
    expect(headerSearch).toBeTruthy()
    await headerSearch.click()
    await driver.sleep(2000)
   // headerSearch.click();
    const searchInput = await driver.findElement(By.xpath("//input[contains(@class,'js-header__search__input header__search__input')]"))
    expect(searchInput).toBeTruthy()
    await searchInput.clear();
    await driver.sleep(2000)
    await searchInput.sendKeys("dress")
    searchInput.sendKeys(Key.ENTER)
    let getCurrentUrl = await driver.getCurrentUrl()
    if(getCurrentUrl.indexOf("jcrew.com/r/search/") > -1){
      console.log("Search results page is displayed")
    }
  })
