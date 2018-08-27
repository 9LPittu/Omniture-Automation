import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';
const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(3000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('Wishlist - Registered User', async () => {
      await loginFromHomePage(logindetails.username1,logindetails.password1)

      driver.sleep(4000);

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
      //await driver.actions().mouseMove(await driver.findElement(By.xpath("//*[@id='global__header']/div/div[2]/section/div/div[3]/div/ul/li[3]/a/span"))).perform();
      driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
      await driver.findElement(By.xpath("//span[text()='shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
    //  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__wishlist-wide")).click()

      const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
      expect(userPannel).toBeTruthy()

      //await driver.navigate().refresh()
        userPannel.click()
       //await driver.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/div/dd[1]/a")).click()
       await driver.sleep(3000)
       await driver.findElement(By.xpath("//*[@id='nav__ul']/li[text()='My Wishlist']")).click()
       await driver.sleep(3000)


       const wishlistitems = driver.findElement(By.xpath("(//img[@class='item-image notranslate_alt'])[1]"))
       await driver.wait(until.elementIsVisible(wishlistitems), 5000)
       expect(wishlistitems).toBeTruthy()
  var elementsCount = wishlistitems.length;
  console.log("wishlistitems"+ elementsCount)
  console.log("product name"+ wishlistitems)
    });
