import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

 test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('verify color swatch is Displayed on hovering the product', async () => {
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='women']")).click()
     await	driver.sleep(1000);
       await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
       await driver.sleep(1000)
       try {
         await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
         closeIcon.click()
         driver.sleep(3000)
        })
        } catch (err)
       { }
       await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
       await driver.sleep(2000)
       await driver.executeScript('window.scrollTo(0, 700)')
       await driver.sleep(1000)
       expect(await driver.findElement(By.xpath("(//img[@class='colors-list__image'])[1]")).isDisplayed()).toBeTruthy();
       console.log('color swatch is Displayed')
});
