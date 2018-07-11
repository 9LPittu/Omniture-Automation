import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//const baseUrl = process.env.BASEURL
//const isHeadless = false || process.env.HEADLESS
//const chromeOptions = isHeadless ? new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox") : new chrome.Options()

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Navbar is visible', async () => {
    console.log("verifying navigation bar ")
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        expect(await driver.findElement(By.xpath("//div[@class='header__primary-nav__inner']"))).toBeTruthy()
        console.log("verified navigation bar ")
    }
    else{
        expect(await driver.findElement(By.id('c-header__navbar'))).toBeTruthy()
        console.log("verified navigation bar ")
      }
     })

    test('Global promo is visible and links correctly', async () => {
      console.log("verifying global__promo")
      const promo = driver.findElement(By.id("global__promo"))
      expect(promo).toBeTruthy()
      await promo.click()
      await driver.sleep(2000)
      await driver.getCurrentUrl(url => {
        expect(url.match('womens_feature/newarrivals')).toBeTruthy()
        console.log("verified global__promo")
      })
      await driver.navigate().back()
    })
/*
    test('Global promo should have a "details" modal', async () => {
      const button = driver.findElement(By.xpath(
        "//button[@class='js-header__promo__btn--details js-has-hover header__promo__btn--details header__promo__btn--details--react']"
      ))
      await driver.wait(until.elementIsVisible(button), 10000)
      expect(button).toBeTruthy()
      await button.click()
      const modal = await driver.findElement(By.className(
        'js-header__promo__details-pushdown header__promo__details-pushdown'
      ))
      expect(modal).toBeTruthy()
      await driver.findElement(By.className(
        'js-btn--close--global-promo-detail btn--close--global-promo-detail btn--close'
      )).click()
    })

    test('Logo and navbar remain fixed as you scroll', async () => {
      const { height } = await driver.findElement(By.css('body')).getRect()
      await driver.executeScript('window.scroll(0, arguments[0])', height)
      expect(await driver.findElement(By.id("c-header__navbar"))).toBeTruthy()
      expect(await By.css("c-header__logo")).toBeTruthy()
      await driver.executeScript('window.scroll(0, 0)')
    })

    test('Promo bar on top of nav should not be visible on scroll', async () => {
      const { height } = await driver.findElement(By.className('no-touch')).getRect()
      await driver.executeScript('window.scroll(0, arguments[0])', height)
      const promo = driver.findElements(By.id("global__promo")).length > 0
      expect(promo).toBeFalsy()
      await driver.executeScript('window.scroll(0, 0)')

    }) */

    test('Search allows you to input a search term', async () => {
      console.log("verifying Search")
      await driver.sleep(2000)
      const searchIcon = await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']"))
      await expect(searchIcon).toBeTruthy()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//span[contains(text(),'search')]")).click()
      const searchinput = await driver.findElement(By.className("js-header__search__input header__search__input"))
      await driver.sleep(1000)
      await searchinput.sendKeys('shirts', Key.ENTER)
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        expect(url).toMatch(new RegExp('/r/search', 'i'))
        console.log("verified search")
      })
      await driver.navigate().back()
    })

    describe('All main nav links work', () => {
      afterEach(async () => {
        await driver.navigate().back()
      })

     each([
        ['WOMEN'],
//        ['MEN'],
//        ['BOYS'],
//        ['GIRLS'],
    //    ['SALE'],
  //      ['FACTORY'],
  ]).test('%s links to correct page', async link => {
    console.log("verifying links")
        try {
        await driver.sleep(3000)
        await driver.findElement(By.xpath("//span[text()='menu']")).click()
        await driver.sleep(3000)
        await driver.findElement(By.linkText(link)).click()
        await driver.sleep(3000)
        await driver.findElement(By.xpath("//a[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'new arrivals')]")).click()
        await	driver.sleep(3000);
          await driver.getCurrentUrl().then(url => {
            const reg = new RegExp(link, 'i')
            expect(url.match(reg)).toBeTruthy()
          })
        } catch (err) {
          throw err
          driver.navigate().to(globals.__baseUrl__)
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
      })*/
    })
