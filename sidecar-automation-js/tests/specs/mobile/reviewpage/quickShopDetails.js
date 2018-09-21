import { driver } from '../../../helpersMobile';
import { load,selectCategory,verifyBag} from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


 test('Verify add to Bag in Shop The Look Tray', async () => {

       await selectCategory()
       await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
       await driver.sleep(2000)
       await driver.executeScript('window.scrollTo(0, 800)')
       await driver.sleep(2000)
       await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
       await driver.sleep(1000)
       const shopthelookElement = await driver.findElement(By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]"));
       expect(shopthelookElement).toBeTruthy()
       shopthelookElement.click()
       await driver.sleep(2000)
       await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
       await driver.sleep(2000)
       await driver.findElement(By.id("btn__add-to-bag-wide")).click()
       await driver.sleep(2000)
       await verifyBag()
 })

 test('Verify add to Wish list in Shop The Look Tray', async () => {

 await selectCategory()
 await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
 await driver.sleep(2000)
 await driver.executeScript('window.scrollTo(0, 800)')
 await driver.sleep(2000)
 await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
 await driver.sleep(1000)
 const shopthelookElement = await driver.findElement(By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]"));
 expect(shopthelookElement).toBeTruthy()
 shopthelookElement.click()
 await driver.sleep(2000)
 await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
 await driver.sleep(2000)
   await driver.findElement(By.id("btn__wishlist-wide")).click()
   await driver.sleep(6000)
   await driver.findElement(By.xpath("//input[@type='email']")).sendKeys(logindetails.username)
   await driver.findElement(By.xpath("//input[@type='password']")).sendKeys(logindetails.password)
   await driver.sleep(2000)
   await driver.findElement(By.xpath("//button[text()='Sign In Here']")).click()
   await driver.sleep(10000)
   let currentUrl = await driver.getCurrentUrl()
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
     const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
     expect(userPannel).toBeTruthy()
     userPannel.click()
     await driver.sleep(3000)
     await driver.findElement(By.xpath("//a[contains(text(),'My Wishlist')]")).click()
   } else {
     await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
     driver.sleep(2000)
     const myaccount = await driver.findElement(By.xpath("//button[@class='hamburger-item']/h3"))
     expect(myaccount).toBeTruthy()
     await driver.sleep(1000)
     // Rewards
     myaccount.click()
     await driver.sleep(1000)
     await driver.findElement(By.xpath("//li[text()='Wishlist']")).click()

   }
 await driver.sleep(3000)
 const wishlistitems = driver.findElement(By.xpath("(//img[@class='item-image notranslate_alt'])[1]"))
 await driver.wait(until.elementIsVisible(wishlistitems), 5000)
 expect(wishlistitems).toBeTruthy()

 })

 test('Verify Size Cart in Shop The Look Tray', async () => {

   await selectCategory()
   await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
   await driver.sleep(2000)
   await driver.executeScript('window.scrollTo(0, 800)')
   await driver.sleep(2000)
   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
   await driver.sleep(1000)
   const shopthelookElement = await driver.findElement(By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]"));
   expect(shopthelookElement).toBeTruthy()
   shopthelookElement.click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("//*[@id='c-product__sizes']/div/button")).click()
   await driver.sleep(2000)
   const size = await driver.findElement(By.xpath("//*[@id='page__size-charts']/div/div/div[3]/h3"))
   expect(size).toBeTruthy()
   console.log("size Chart is available for product")

 })

 test('Verify Full details in Shop The Look Tray', async () => {

   await selectCategory()
   await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
   await driver.sleep(2000)
   await driver.executeScript('window.scrollTo(0, 800)')
   await driver.sleep(2000)
   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
   await driver.sleep(1000)
   const shopthelookElement = await driver.findElement(By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]"));
   expect(shopthelookElement).toBeTruthy()
   shopthelookElement.click()
   await driver.sleep(2000)
         await driver.executeScript('window.scrollTo(0, 1000)')
         await driver.sleep(2000)
         const size = await driver.findElement(By.xpath("//*[@id='c-product__description']/div/div/div[1]/span"))
         expect(size).toBeTruthy()
         console.log("Full Details are available for product")

 })

 test('Verify sizes and color swatches in Shop The Look Tray', async () => {
   await selectCategory()
   await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
   await driver.sleep(2000)
   await driver.executeScript('window.scrollTo(0, 800)')
   await driver.sleep(2000)
   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
   await driver.sleep(1000)
   const shopthelookElement = await driver.findElement(By.xpath("(//li[contains(@class,'js-styled-with-pdp styled-with')])[1]"));
   expect(shopthelookElement).toBeTruthy()
   shopthelookElement.click()
   await driver.sleep(2000)
   // Sizes
   const sizes = await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
   expect(sizes).toBeTruthy()
   console.log("sizes are available for product")
   await driver.sleep(2000)
   // Color swatches
   expect(await driver.findElement(By.xpath("(//img[@class='colors-list__image'])[1]")).isDisplayed()).toBeTruthy();
   console.log('color swatch is Displayed')
 })

 afterAll(async () => {
  await driver.quit()
})
