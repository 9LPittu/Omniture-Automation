import { driver, defaultTimeout } from '../../../helpersMobile';
import { load, selectCategory, selectItemAddToBag, verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Wishlist Registered User functionality', async () => {
    await selectCategory();
    await driver.sleep(2000)
    await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 700)')
    await driver.sleep(1000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
      await driver.sleep(1000)
      await driver.findElement(By.id("btn__wishlist-wide")).click()
      await driver.sleep(6000)

      await driver.findElement(By.xpath("//input[@type='email']")).sendKeys(logindetails.username)
      await driver.findElement(By.xpath("//input[@type='password']")).sendKeys(logindetails.password)
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//*[@id='page__signin']//button)[2]")).click() // Factory
      // await driver.findElement(By.xpath("//button[text()='Sign In Here']")).click() // Jcrew
      await driver.sleep(10000)
      const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
      expect(userPannel).toBeTruthy()

      //await driver.navigate().refresh()
        userPannel.click()
       //await driver.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/div/dd[1]/a")).click()
       await driver.sleep(3000)
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
         await driver.findElement(By.xpath("//a[contains(text(),'My Wishlist')]")).click()
      } else {
        await driver.findElement(By.xpath("//span[text()='My Details']")).click()
        await driver.sleep(3000)
        await driver.findElement(By.xpath("//*[@id='nav__ul']/li[text()='My Wishlist']")).click()
    }
  await driver.sleep(3000)
  const wishlistitems = driver.findElement(By.xpath("(//img[@class='item-image notranslate_alt'])[1]"))
  await driver.wait(until.elementIsVisible(wishlistitems), 5000)
  expect(wishlistitems).toBeTruthy()

   })
