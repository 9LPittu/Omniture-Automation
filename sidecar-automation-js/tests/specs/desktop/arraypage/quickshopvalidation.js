import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

//describe('index', () => {
//  it('title is correct', async () => {
//    await load();
//    expect(await driver.getTitle()).toMatch('J.Crew');
//  });
//});


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

/*
 afterAll(async () => {
   await driver.quit()
 })



 test('Close email capture is present or not', async () => {

   let close_email_capture = await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']"))

   await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).then(function(webElement) {
         close_email_capture.click()
     }, function(err) {
         if (err.state && err.state === 'no such element') {
             console.log('Element not found')
         } else {
             driver.promise.rejected(err)
         }
     })
    })

*/
  test('quickshop validation', async () => {

      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
    //  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()
      await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))).perform();
      await driver.sleep(2000)
      await driver.findElement(By.xpath("(//div[contains(@class,'btn--quickshop')])")).click()
      //await driver.navigate().refresh()
      await driver.sleep(3000)
      const productsize= await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"))
      expect(productsize).toBeTruthy()
      const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
      expect(productaddtobag).toBeTruthy()
      const productaddtowishlist= await driver.findElement(By.id("btn__wishlist"))
      expect(productaddtowishlist).toBeTruthy()

   })
