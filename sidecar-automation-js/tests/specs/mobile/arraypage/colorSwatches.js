import { driver } from '../../../helpersMobile';
import { load, selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  //await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('verify color swatch is Displayed on hovering the product', async () => {
      await selectCategory();
       await driver.findElement(By.xpath("(//span[@class='tile__detail tile__detail--name'])[1]")).click()
       await driver.sleep(2000)
       await driver.executeScript('window.scrollTo(0, 700)')
       await driver.sleep(1000)
       expect(await driver.findElement(By.xpath("(//img[@class='colors-list__image'])[1]")).isDisplayed()).toBeTruthy();
       console.log('color swatch is Displayed')
});

afterAll(async () => {
  await driver.quit()
})
