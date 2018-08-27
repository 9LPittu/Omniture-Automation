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

    test('Wishlist - Guest User', async () => {

      driver.sleep(1000);

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
      //await driver.actions().mouseMove(await driver.findElement(By.xpath("//*[@id='global__header']/div/div[2]/section/div/div[3]/div/ul/li[3]/a/span"))).perform();
      driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        //await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
      //await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
      await driver.findElement(By.xpath("//span[text()='shirts']")).click()
    }
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      await driver.sleep(5000)
      await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
    //  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(1000)
      await driver.findElement(By.id("btn__wishlist-wide")).click()
      await driver.sleep(1000)
      if(currentUrl.includes("/r/login")){
        console.log("User navigates to login page")
      }
    });
