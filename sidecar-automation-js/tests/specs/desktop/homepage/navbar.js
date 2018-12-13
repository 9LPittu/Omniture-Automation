import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')
const dep_nav_list = By.xpath("//ul[@class='department-nav__list']");
const nav_dep = By.xpath("//ul[@class='nc-nav__departments']");
const global_promo = By.id("global__promo");
const nc_promo = By.className("nc-promo");
const primary_nav = By.className("primary-nav__text primary-nav__text--stores");
const store_locator = By.xpath("//div[text()='Store Locator']");
const search = By.xpath("//span[contains(text(),'search')]");
const search_input = By.xpath("//input[@class='nc-nav__search__input']");
const men = By.xpath("//a[text()='Men']");
const  nav_fliyout_list =By.xpath("//li[@class='nc-nav__flyout-link__wrapper nc-nav__list-item']");

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
          expect(await driver.findElement(dep_nav_list)).toBeTruthy()
          console.log("nav bar is displaying")
        }else{
          expect(await driver.findElement(nav_dep)).toBeTruthy()
          console.log("nav bar is displaying")
        }
     })

    test('Global promo is visible and links correctly', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
        try{
      const promo = driver.findElement(global_promo)
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    }catch(err){}
    }else{
      try{
      const promo = driver.findElement(nc_promo)
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    }catch(err){}
    }
    })

    test('Stores is visible and url direct to right URL', async () => {
      currentUrl = await driver.getCurrentUrl();
      if(currentUrl.includes("factory")){
      const stores = driver.findElement(primary_nav)
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
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(store_locator));
    await driver.sleep(1000)
    driver.findElement(store_locator).click()
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
      await driver.findElement(search).click()
      await search.sendKeys('shirts', Key.ENTER)
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        expect(url).toMatch(new RegExp('/r/search', 'i'))
      })
      await driver.navigate().back()
      await driver.navigate().to(currentUrl)
      console.log("User is able to search for items")
    } else {
      var searchText = await driver.findElement(search_input);
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

   describe('Verify Nav mouse over shows the sub-category items', () => {
    
     each([
        ['New'], 
        ['Women'],
        ['Men'],
        ['kids'],
        ['Brands We Love'],
        ['sale'],
  ]).test('%s mouse over shows the sub category items  ', async () => {
    if(currentUrl.includes("factory")){ 
    } else {
    await driver.actions().mouseMove(await driver.findElement(men)).perform();
      const ele = await driver.findElements(nav_fliyout_list)
      console.log(ele.length)
     }
    })
  })


   describe('All main nav links work', () => {
      afterEach(async () => {
        await driver.navigate().back()
      })

     each([
        ['Women'],
        ['Men'],
       // ['kids']
      //  ['Girls'],
      //  ['Boys'],
  ]).test('%s links to correct page', async link => {
    currentUrl = await driver.getCurrentUrl();
    if(currentUrl.includes("factory")){
        try {
          const subnav = await driver.findElement(By.className(
            "c-header__department-nav js-header__department-nav"
          ))

          await subnav.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and text() = '"+link+"']")).click()
         
         // await subnav.findElement(By.linkText(link)).click()
        //await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[1]/h2[text()='"+link+"']")).click()
          await driver.sleep(2000)
          await driver.getCurrentUrl().then(url => {
            const reg = new RegExp(link, 'i')
            expect(url.match(reg)).toBeTruthy()
          })
        } catch (err) {
          throw err
        }
      }else{
        await driver.findElement(By.xpath("//a[text()='" +link+"']")).click()
          await driver.sleep(2000)
          await driver.getCurrentUrl().then(url => {
            if (link == 'kids') {
              link = 'Girls';
            }
            const reg = new RegExp(link, 'i')
            expect(url.match(reg)).toBeTruthy()
          })
      }
      })

    }) 

afterAll(async () => {
  await driver.quit()
})