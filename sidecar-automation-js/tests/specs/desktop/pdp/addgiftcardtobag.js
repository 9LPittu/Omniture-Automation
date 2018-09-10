import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding gift card items to bag', async () => {

      await driver.sleep(3000)
      await driver.executeScript('window.scrollTo(0, 30000)')
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(text(),'The J.Crew Gift Card')]")));
      await driver.sleep(2000)
      driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
      await driver.sleep(2000)
      await driver.executeScript('window.scrollTo(0, 200)')
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[@id='classicGiftCard']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[@id='amountList']/a)[1]")).click()
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",await driver.findElement(By.xpath("//h3[text()='From']")));
      await driver.sleep(2000)
      driver.findElement(By.id("senderName")).sendKeys("tester1")
      await driver.sleep(2000)
      driver.findElement(By.id("RecipientName")).sendKeys("tester2")
      await driver.sleep(2000)
      driver.findElement(By.xpath("//input[@id='submitClassic']")).click()
      await driver.sleep(2000)
      let url = await driver.getCurrentUrl();
      if (url.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.id("js-header__cart")).click()
        await driver.sleep(1000)
        let bagsize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
        console.log("=======Bag size "+bagsize)
        expect(bagSize).toBeTruthy()
      }else{
        await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__icon']")).click()
        await driver.sleep(1000)
        let bagsize = await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__count']")).getText()
        expect(bagsize).toBeTruthy()
        console.log("=======Bag size "+bagsize)
        expect(bagSize).toBeTruthy()
      }

   })

   afterAll(async () => {
    await driver.quit()
  })
