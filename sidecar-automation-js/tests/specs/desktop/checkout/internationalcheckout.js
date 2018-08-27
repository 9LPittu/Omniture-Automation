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
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 each([
   ['Canada'],
//   ['United Kingdom'],
//   ['India']
 ]).test('%s - International Checkout', async contextchooser => {
   driver.sleep(2000);
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
   await driver.executeScript('window.scrollTo(0, 20000)')
   driver.sleep(3000)
    //const footer = await driver.findElement(By.id('global__footer'))
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@class='footer__country-context__link']")));
    driver.sleep(3000)
    const footer = await driver.findElement(By.id('global__footer'))
    const location = await footer.findElement(By.xpath("//a[@class='footer__country-context__link']"))
    await expect(location).toBeTruthy()
    await location.click()
    await driver.getCurrentUrl( url => {
      expect(url).toMatch('r/context-chooser')
    })
    await driver.findElement(By.xpath("//span[text()='" + contextchooser +"']" )).click()
    await driver.sleep(3000)
   await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
   await driver.sleep(3000)
   let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
    await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }

      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      await driver.navigate().refresh()
      await driver.sleep(sleeptime)
    //  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
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

       if (currentUrl.indexOf("https://or.") > -1) {

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
        if (currentUrl.indexOf("factory.jcrew.com") > -1) {
          console.log(">> inside factory")
          await driver.findElement(By.xpath("//*[@id='orderSummaryContainer']/div/a")).click()
        } else {
          await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
        }
        await driver.sleep(sleeptime)
        const bizrate = await driver.findElement(By.xpath("//div[@class='brdialog-close']"))
        expect(bizrate).toBeTruthy()
        bizrate.click()
        await driver.sleep(2000)
       let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id  > " + orderNumberLet)

           driver.navigate().to(globals.__baseUrl__);

      }
   })
