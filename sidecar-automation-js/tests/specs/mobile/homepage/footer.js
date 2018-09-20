import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//const baseUrl = process.env.BASEURL
//const isHeadless = false || process.env.HEADLESS
//const chromeOptions = isHeadless ? new chrome.Options().headless().addArguments("--disable-gpu", "--no-sandbox") : new chrome.Options()

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


/*
    test('Twitter is working', async () => {
      await driver.executeScript('window.scrollTo(0, 20000)')
        await driver.sleep(2000)
      const footer = await driver.findElement(By.tagName('footer'))
      const contact = footer.findElement(By.id('c-footer__twitter'))
      expect(contact).toBeTruthy()
      await driver.actions({ bridge: true }).move({origin: contact}).click().perform()
      await driver.getCurrentUrl( url => {
        expect(url.match('https://twitter.com/jcrew_help')).toBeTruthy()
      })
      await driver.navigate().back()
    })
    */
/*

    test('Phone is working', async () => {
      await driver.executeScript('window.scrollTo(0, 20000)')
        await driver.sleep(2000)
       const footer = await driver.findElement(By.id('global__footer'))
       const phone = footer.findElement(By.className('footer__help__item footer__help__item--phone'))
       expect(phone).toBeTruthy()
       const aTag = await phone.findElement(By.tagName('a'))
       await aTag.getAttribute('href').then( link => {
           expect(link.replace(/%20/g, ' ')).toMatch('tel:1 800 562 0258')
         })
     })


     test('Email is working', async () => {
      const footer = await driver.findElement(By.id('global__footer'))
       const email = footer.findElement(By.xpath("//*[@id='c-footer__email']/a"))
       expect(email).toBeTruthy()
//       const aTag = email.findElement(By.tagName('a'))
       await email.getAttribute('href').then( mail => {
         expect(mail).toMatch('mailto:help@jcrew.com')
       })
     })

     test('Twitter is working', async () => {
       const footer = await driver.findElement(By.id('global__footer'))
       const twitter = footer.findElement(By.id('footer__help__item footer__help__item--twitter'))
       expect(twitter).toBeTruthy()
     })
*/

test('Order Status is visible and url direct to right url', async () => {
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h6[text()='Contact Us']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//h6[text()='Let Us Help You']")).click()
  await driver.sleep(1000)
  const orderstatuslink = driver.findElement(By.xpath("//a/div[text()='Order Status']"))
  expect(orderstatuslink).toBeTruthy()
  await orderstatuslink.click()
  await driver.sleep(2000)
  await driver.getCurrentUrl(url => {
    expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
  })
  await driver.navigate().back()
  await driver.sleep(1000)
})
     test('Location change is visible and links to correct place', async () => {
       let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
         //await driver.executeScript('window.scrollTo(0, -10000)')
         await driver.sleep(1000)
         await driver.executeScript('window.scrollTo(0, 10000)')
         await driver.sleep(1000)
         await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[text()='change']")));
         await driver.sleep(1000)
         await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).click()
         await driver.sleep(1000)
         const change = await driver.findElement(By.xpath("//a[text()='change']"))
         await expect(change).toBeTruthy()
         change.click()
         await driver.sleep(1000)
         //await driver.get(currentUrl+"/r/context-chooser")
         //await driver.sleep(1000)
      } else {
        await driver.sleep(2000)
        await driver.executeScript('window.scrollTo(0, 10000)')
        await driver.sleep(1000)
        await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[text()='change']")));
        await driver.sleep(1000)
        const change = await driver.findElement(By.xpath("//a[text()='change']"))
        await expect(change).toBeTruthy()
        change.click()
        await driver.sleep(1000)
    }
        /*const footer = await driver.findElement(By.id('global__footer'))
        const location = await footer.findElement(By.xpath("//a[@class='footer__country-context__link']"))
        await expect(location).toBeTruthy()
        await location.click()*/
        await driver.getCurrentUrl( url => {
          expect(url).toMatch('r/context-chooser')
        })
        await driver.sleep(1000)
        await driver.findElement(By.xpath("//h5[text()='UNITED STATES & CANADA']")).click()
try {
  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()
} catch (err) {

}
        await driver.findElement(By.xpath("//span[text()='Canada']")).click()
        await driver.sleep(2000)
      })