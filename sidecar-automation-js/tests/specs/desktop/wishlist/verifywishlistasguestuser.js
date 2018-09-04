import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {logindetails } from '../../../testdata/jcrewTestData';
import {productArrayPage} from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(3000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('Wishlist - Guest User', async () => {

      driver.sleep(1000);
      await productArrayPage();
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(1000)
      await driver.findElement(By.id("btn__wishlist-wide")).click()
      await driver.sleep(1000)
      let currentUrl = await driver.getCurrentUrl(); 
      if(currentUrl.includes("/r/login")){
        console.log("User navigates to login page")
      }
    });
