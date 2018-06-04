import { driver } from '../helpers';
import { load } from '../pageObjects/homepageobj';

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


 test('Close email capture is present or not', async () => {
  await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).click()
  driver.sleep(4000)
    })


  test('test Guest checkout', async () => {
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
      //await driver.actions().mouseMove(await driver.findElement(By.xpath("//*[@id='global__header']/div/div[2]/section/div/div[3]/div/ul/li[3]/a/span"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__add-to-bag-wide")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      await driver.sleep(10000)
      await driver.findElement(By.xpath("//*[@id='loginUser']")).sendKeys("applabs9@gmail.com")
      await driver.findElement(By.xpath("//*[@id='loginPassword']")).sendKeys("applabs")
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//a[text()='Sign In & Check Out']")).click()
      // add the wishlist need to added - if any failures come in middl
      await driver.sleep(3000)
      await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

// address confirmation page :
await driver.sleep(3000)
await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).click()

//shipping & gift pageObjects
await driver.sleep(3000)
await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

       //credit card details
       await driver.sleep(3000)
         await driver.findElement(By.xpath("//input[@id='creditCardNumber']")).sendKeys("4111111111111111")
         await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys("256")
         await driver.findElement(By.xpath("//select[@id='expirationMonth']")).sendKeys("12")
         await driver.findElement(By.xpath("//select[@id='expirationYear']")).sendKeys("2016")
         await driver.findElement(By.xpath("//input[@id='nameOnCard']")).sendKeys("Automated Test User")
         await driver.findElement(By.xpath("//input[@id='emailReceipt']")).sendKeys("receipt_mail@example.org")
        // await driver.findElement(By.xpath("//a[@id='billing-options-submit']")).click()
        await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()

        await driver.sleep(2000)

    //   let currentUrl = await driver.getCurrentUrl();
    //   console.log("current URL > " + currentUrl.indexOf("https://or.jcrew.com"))

       //if (currentUrl == "https://or.jcrew.com/checkout2/billing.jsp") {  // Production review checkout
       if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
         await driver.sleep(3000)
         await driver.findElement(By.xpath("//*[@id='button-submitorder']")).click()
        await driver.sleep(4000)
        let orderNumberLet = await driver.findElement(By.xpath("//span[@class='order-number notranslate']")).getText()

           console.log("order Id let > " + orderNumberLet)
      }
   })
