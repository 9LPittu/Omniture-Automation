import { driver, defaultTimeout } from '../../../helpersMobile';
import { load,goToGiftCardPage} from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';



const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Adding gift card items to bag', async () => {
      await goToGiftCardPage()
      await driver.sleep(1000)
      await addClassicGiftCard()
      await driver.sleep(1000)
      await verifyBagItems()
   })

   export const addClassicGiftCard = async () => {
     await driver.findElement(By.xpath("//a[@id='classicGiftCard']")).click()
     await driver.sleep(1000)
     await driver.findElement(By.xpath("(//div[@id='amountList']/a)[1]")).click()
     await driver.sleep(1000)
     await driver.executeScript("arguments[0].scrollIntoView(true);",await driver.findElement(By.xpath("//h3[text()='From']")));
     await driver.sleep(1000)
     driver.findElement(By.id("senderName")).sendKeys("tester1")
     await driver.sleep(1000)
     driver.findElement(By.id("RecipientName")).sendKeys("tester2")
     await driver.sleep(1000)
     driver.findElement(By.xpath("//input[@id='submitClassic']")).click()
     await driver.sleep(1000)
   }

   export const verifyBagItems = async () => {
   let currentUrl = await driver.getCurrentUrl();
   if (currentUrl.indexOf("factory.jcrew.com") > -1) {
     const bag = await driver.findElement(By.id("js-header__cart"))
     await driver.executeScript("arguments[0].scrollIntoView(true);",bag);
     await driver.sleep(1000)
     bag.click()
     await driver.sleep(1000)
     const itemsF = await driver.findElement(By.xpath("//*[@id='js-header__cart']/span[2]/span")).getText();
     expect(itemsF).toBeTruthy()
   }else{
     await driver.get(globals.__baseUrl__);
     await driver.sleep(1000)
  /*   let bagIcon = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button bag']"))
     await driver.executeScript("arguments[0].scrollIntoView(true);",bagIcon);
     await driver.sleep(1000)
     bagIcon.click()
     await driver.sleep(1000)*/
     const items = await driver.findElement(By.xpath("//span[@class='cart-badge']")).getText();
     expect(items).toBeTruthy()
     console.log("numbers of items in the Bag are.. "+items)
   }

 }

   afterAll(async () => {
    await driver.quit()
  })
