import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')
let currentUrl;

test('title is correct', async () => {
  await load();
  currentUrl = await driver.getCurrentUrl();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 afterAll(async () => {
   await driver.quit()
 })

    test('Navbar is visible', async () => {
        currentUrl = await driver.getCurrentUrl();
        if(currentUrl.includes("factory")){
          expect(await driver.findElement(By.xpath("//ul[@class='department-nav__list']"))).toBeTruthy()
          console.log("nav bar is displaying")
        }else{
          expect(await driver.findElement(By.xpath("//ul[@class='nc-nav__departments']"))).toBeTruthy()
          console.log("nav bar is displaying")
        }
     })

    test('Global promo is visible and links correctly', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
        try{
      const promo = driver.findElement(By.id("global__promo"))
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    }catch(err){}
    }else{
      try{
      const promo = driver.findElement(By.className("nc-promo"))
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    }catch(err){}
    }
    })

    test('Stores is visible and url direct to right URL', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
      const stores = driver.findElement(By.className("primary-nav__text primary-nav__text--stores"))
      expect(stores).toBeTruthy()
      await stores.click()
      await driver.sleep(2000)
      await driver.getCurrentUrl(url => {
        expect(url.match('https://stores.jcrew.com')).toBeTruthy()
      })
      await driver.navigate().back()
      await driver.navigate().to(currentUrl)
      console.log("User navigated to stores page")
  }else{
    await driver.executeScript('window.scrollTo(0, 20000)')
    await driver.sleep(2000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[text()='Store Locator']")));
    await driver.sleep(1000)
    driver.findElement(By.xpath("//div[text()='Store Locator']")).click()
    await driver.sleep(1000)
    await driver.getCurrentUrl(url => {
      expect(url.match('https://stores.jcrew.com')).toBeTruthy()
    })
    await driver.navigate().back()
    await driver.navigate().to(currentUrl)
    console.log("User navigated to stores page")
  }
    })

    test('Search allows you to input a search term', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
      const search = await driver.findElement(By.id("inputSearchDesktop"))
      await expect(search).toBeTruthy()
      await driver.findElement(By.xpath("//span[contains(text(),'search')]")).click()
      await search.sendKeys('shirts', Key.ENTER)
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        expect(url).toMatch(new RegExp('/r/search', 'i'))
      })
      await driver.navigate().back()
      await driver.navigate().to(currentUrl)
      console.log("User is able to search for items")
    }else{
      var searchText = await driver.findElement(By.xpath("//input[@class='nc-nav__search__input']"));
      searchText.sendKeys("pants")
      await driver.sleep(1000)
      driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        expect(url).toMatch(new RegExp('/r/search', 'i'))
      })
      await driver.navigate().back()
      await driver.navigate().to(currentUrl)
      console.log("User is able to search for items")
    }
    })
    test('All main nav links', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
        await driver.findElement(By.xpath("//span[text()='Women']")).click()
        await driver.sleep(1000)
        let womenCategory = driver.getCurrentUrl()
        if(womenCategory.includes("womens")){

        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//span[text()='Men']")).click()
        await driver.sleep(1000)
        let menCategory = driver.getCurrentUrl()
        if(menCategory.includes("mens")){
        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//span[text()='Girls']")).click()
        await driver.sleep(1000)
        let girlCategory = driver.getCurrentUrl()
        if(girlCategory.includes("girls")){

        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//span[text()='Boys']")).click()
        await driver.sleep(1000)
        let boysCategory = driver.getCurrentUrl()
        if(boysCategory.includes("boys")){

        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//span[text()='Clearance']")).click()
        await driver.sleep(1000)
        let saleCategory = driver.getCurrentUrl()
        if(saleCategory.includes("sale")){

        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
      }else{
        await driver.findElement(By.xpath("//a[text()='Women']")).click()
        await driver.sleep(1000)
        let womenCategory = await driver.getCurrentUrl();
        if(womenCategory.includes("womens")){
        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//a[text()='Men']")).click()
        await driver.sleep(1000)
        let menCategory = await driver.getCurrentUrl();
        if(menCategory.includes("mens")){
        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//a[text()='kids']")).click()
        await driver.sleep(1000)
        let girlsCategory = await driver.getCurrentUrl();
        if(girlsCategory.includes("Girls")){

        }
        await driver.navigate().to(currentUrl)
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//a[text()='sale']")).click()
        await driver.sleep(1000)
        let saleCategory = await driver.getCurrentUrl();
        if(saleCategory.includes("sale")){

        }
        await driver.navigate().to(currentUrl)
        console.log("Navigated to all main nav")
      }
    })

  /* describe('All main nav links work', () => {
      afterEach(async () => {
        await driver.navigate().back()
      })

     each([
        ['WOMEN'],
        ['MEN'],
        ['BOYS'],
        ['GIRLS'],
        ['SALE'],
  //      ['FACTORY'],
  ]).test('%s links to correct page', async link => {
    currentUrl = await driver.getCurrentUrl();
    if(currentUrl.includes("factory")){
        try {
          const subnav = await driver.findElement(By.className(
            "c-header__department-nav js-header__department-nav"
          ))
          await subnav.findElement(By.linkText(link)).click()
          await driver.sleep(2000)
          await driver.getCurrentUrl().then(url => {
            const reg = new RegExp(link, 'i')
            expect(url.match(reg)).toBeTruthy()
          })
        } catch (err) {
          throw err
        }
      }else{
          const subnav = await driver.findElement(By.xpath("//ul[@class='nc-nav__departments']"))
          await subnav.findElement(By.linkText(link)).click()
          await driver.sleep(2000)
          await driver.getCurrentUrl().then(url => {
            const reg = new RegExp(link, 'i')
            expect(url.match(reg)).toBeTruthy()
          })
      }
      })

    }) */

afterAll(async () => {
  await driver.quit()
})
