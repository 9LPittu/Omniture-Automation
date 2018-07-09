import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding gift card items to bag', async () => {

      await driver.navigate().refresh()
      driver.sleep(2000);
      try {
        await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
        // console.log("inside merge page")
         privacyPolicyClose.click()
         driver.sleep(3000)
       })
       } catch (err)
      { }
      await driver.sleep(3000)
      await driver.executeScript('window.scrollTo(0, 20000)')
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[contains(text(),'The J.Crew Gift Card')]")));
      await driver.sleep(2000)
      driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[@id='classicGiftCard']")).click()
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[@id='amountList']/a)[1]")).click()
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",await driver.findElement(By.xpath("//h3[text()='From']")));
      await driver.sleep(2000)
      driver.findElement(By.id("senderName")).sendKeys("tester1")
      await driver.sleep(2000)
      //await driver.executeScript("arguments[0].scrollIntoView(true);",await driver.findElement(By.id("RecipientName")));
      //await driver.sleep(2000)
      driver.findElement(By.id("RecipientName")).sendKeys("tester2")
      await driver.sleep(2000)
      driver.findElement(By.xpath("//input[@id='submitClassic']")).click()
      await driver.sleep(2000)
      driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(2000)
      let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log ("Bag Size >> " + bagSize)
      expect(bagSize).toBeTruthy()
   })
