import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//const baseUrl = process.env.BASEURL
//const isHeadless = false || process.env.HEADLESS
//const chromeOptions = isHeadless ? new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox") : new chrome.Options()

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

     test('Order Status is visible and url direct to right url', async () => {
       await driver.executeScript('window.scrollTo(0, 20000)')
       await driver.sleep(2000)
       await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[text()='Order Status']")));
       await driver.sleep(2000)
       expect(driver.findElement(By.xpath("//div[text()='Order Status']"))).toBeTruthy()
       await driver.findElement(By.xpath("//div[text()='Order Status']")).click()
       await driver.sleep(2000)
       await driver.getCurrentUrl(url => {
         expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
       })
       await driver.navigate().back()
     })

     test('Location change is visible and links to correct place', async () => {
        await driver.sleep(2000)
        const footer = await driver.findElement(By.id('global__footer'))
        const location = await footer.findElement(By.xpath("//a[@class='footer__country-context__link']"))
        await expect(location).toBeTruthy()
        await location.click()
        await driver.getCurrentUrl( url => {
          expect(url).toMatch('r/context-chooser')
        })

        await driver.findElement(By.xpath("//span[text()='Canada']")).click()
        await driver.sleep(2000)
        console.log("user is able to select the country from context chooser")
      })

      afterAll(async () => {
        await driver.quit()
      })

      