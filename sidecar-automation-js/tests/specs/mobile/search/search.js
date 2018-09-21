import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser, logindetails } from '../../../testdata/jcrewTestData';
import {  } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


 test('Search  is working', async () => {
 //  await driver.executeScript('window.scrollTo(0, 20000)')
     const currentUrl = await driver.get(`${__baseUrl__}/`)
     try {
     await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
     console.log("Email capture page")
     emailCapture.click()
     driver.sleep(3000)
      })
      } catch (err)
        { }
     let currentUrlF = await driver.getCurrentUrl();
     if (currentUrlF.indexOf("factory.jcrew.com") > -1) {
      await driver.sleep(2000)
      const headerSearchF = await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']"))
      expect(headerSearchF).toBeTruthy()
      await headerSearchF.click()
      await driver.sleep(2000)
      // headerSearch.click();
      const searchInputF = await driver.findElement(By.xpath("//input[@class='js-header__search__input header__search__input']"))
      expect(searchInputF).toBeTruthy()
      await searchInputF.clear();
      await driver.sleep(2000)
      await searchInputF.sendKeys("dress")
      searchInputF.sendKeys(Key.ENTER)
      let getCurrentUrl = await driver.getCurrentUrl()
      if(getCurrentUrl.indexOf("jcrew.com/r/search/") > -1){
       console.log("Search results page is displayed")
      }
     }
     else {
       await driver.sleep(2000)
       const headerSearch = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button search']"))
       expect(headerSearch).toBeTruthy()
       await headerSearch.click()
       await driver.sleep(2000)
       // headerSearch.click();
       const searchInput = await driver.findElement(By.xpath("//input[@placeholder='Search J.Crew']"))
       expect(searchInput).toBeTruthy()
       await searchInput.clear();
       await driver.sleep(2000)
       await searchInput.sendKeys("dress")
       searchInput.sendKeys(Key.ENTER)
       let getCurrentUrl = await driver.getCurrentUrl()
       if(getCurrentUrl.indexOf("jcrew.com/r/search/") > -1){
        console.log("Search results page is displayed")
       }
     }

  })

  afterAll(async () => {
    await driver.quit()
  })
