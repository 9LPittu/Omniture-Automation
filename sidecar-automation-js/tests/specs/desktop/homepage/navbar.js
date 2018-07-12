import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 afterAll(async () => {
   await driver.quit()
 })

    test('Navbar is visible', async () => {
        expect(await driver.findElement(By.id('c-header__navbar'))).toBeTruthy()
     })

    test('Global promo is visible and links correctly', async () => {
      const promo = driver.findElement(By.id("global__promo"))
      expect(promo).toBeTruthy()
      await promo.click()
      await driver.sleep(2000)
      await driver.getCurrentUrl(url => {
        expect(url.match('womens_feature/newarrivals')).toBeTruthy()
      })
      await driver.navigate().back()
    })

    test('Stores is visible and url direct to right URL', async () => {
      const stores = driver.findElement(By.className("primary-nav__text primary-nav__text--stores"))
      expect(stores).toBeTruthy()
      await stores.click()
      await driver.sleep(2000)
      await driver.getCurrentUrl(url => {
        expect(url.match('https://stores.jcrew.com')).toBeTruthy()
      })
      await driver.navigate().back()
    })

    test('Search allows you to input a search term', async () => {
      const search = await driver.findElement(By.id("inputSearchDesktop"))
      await expect(search).toBeTruthy()
      await driver.findElement(By.xpath("//span[contains(text(),'search')]")).click()
      await search.sendKeys('shirts', Key.ENTER)
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        expect(url).toMatch(new RegExp('/r/search', 'i'))
      })
      await driver.navigate().back()
    })

    describe('All main nav links work', () => {
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
      })
/*
      test('Blog links to blog page', async () => {
        try {
          let currentUrl = await driver.getCurrentUrl();
        if (currentUrl.indexOf("factory.jcrew.com") > -1) {
          console.log("No blog links for factory")
          } else {
          const subnav = await driver.findElement(By.className(
            "c-header__department-nav js-header__department-nav"
          ))
          await subnav.findElement(By.linkText('BLOG')).click()
          await driver.sleep(2000)
          await driver.getCurrentUrl().then(url => {
            expect(url.match('hello')).toBeTruthy()
          })
        }
        } catch (err) {
          throw err
        }
      }) */
    })

afterAll(async () => {
  await driver.quit()
})
