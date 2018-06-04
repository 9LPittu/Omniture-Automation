import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen, caualshirt } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')
let sleeptime = 7000;

test('title is correct', async () => {
  await load();
  await driver.sleep(sleeptime)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 each([
   ['Canada'],
//   ['United Kingdom'],
//   ['India']
 ]).test('%s - International Checkout', async contextchooser => {

   await driver.executeScript('window.scrollTo(0, 20000)')
    const footer = await driver.findElement(By.id('global__footer'))
    const location = await footer.findElement(By.xpath("//a[@class='footer__country-context__link']"))
    await expect(location).toBeTruthy()
    await location.click()
    await driver.getCurrentUrl( url => {
      expect(url).toMatch('r/context-chooser')
    })
    await driver.findElement(By.xpath("//span[text()='" + contextchooser +"']" )).click()
   await driver.actions().mouseMove(await driver.wait(until.elementLocated(categorymen), defaultTimeout)).perform();
   let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
    await driver.wait(until.elementLocated(caualshirt), defaultTimeout).click();
    }

      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
    //  await driver.navigate().refresh()
      await driver.sleep(sleeptime)
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(sleeptime)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(sleeptime)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(sleeptime)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(sleeptime)

      await driver.findElement(By.xpath("//*[@id='loginUser']")).clear()
      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys(logindetails.username)
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys(logindetails.password)
      await driver.sleep(sleeptime)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()

      await driver.sleep(sleeptime)

try {
  await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[1]")).then(mergebutton => {
   mergebutton.click()
   driver.sleep(sleeptime)
   driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
})
} catch (err)
{ }

try {
  await driver.findElement(By.xpath("//*[@id='order-summary']/a[1]")).then(continuesummary => {
   continuesummary.click()
   driver.sleep(sleeptime)
   driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
   driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()

  })
} catch (err)
{ }

       if (new String(currentUrl).valueOf() != ((new String("https://www.jcrew.com/").valueOf()) && new String("https://factory.jcrew.com/").valueOf())) {

 try {
         await driver.findElement(By.xpath("//*[@id='securityCode']")).then(securitycode => {
            console.log("inside securitycode")
            securitycode.sendKeys(creditcard.pin)
           })

      } catch (err )
      {

      }
         await driver.sleep(sleeptime)
        // await driver.findElement(By.xpath("//*[@id='securityCode']")).sendKeys(creditcard.pin)
         await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
        await driver.sleep(sleeptime)
       let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id  > " + orderNumberLet)

           driver.navigate().to(globals.__baseUrl__);

      }
   })
