import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('Wishlist guestuser functionality', async () => {
   //await driver.navigate().refresh()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='men']")).click()
     await	driver.sleep(2000);
       await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()

        let currentUrl = await driver.getCurrentUrl();
       try {
   await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
    console.log("Email capture page")
    emailCapture.click()
    driver.sleep(3000)
    })
  } catch (err)
 { }
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
   await driver.sleep(1000)
   await driver.executeScript('window.scrollTo(0, 600)')
   await driver.sleep(1000)
     await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
     //await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
     
     await driver.sleep(1000)
     await driver.findElement(By.id("btn__wishlist-wide")).click()
     await driver.sleep(6000)
      if(currentUrl.includes("/r/login")){
        console.log("User navigates to login page")
      }
    });
