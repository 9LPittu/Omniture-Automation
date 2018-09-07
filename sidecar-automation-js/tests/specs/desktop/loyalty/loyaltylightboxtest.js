import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
 // await load();
 await driver.get(`${__baseUrl__}/`);
  await driver.sleep(3000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


     test('Verify Loyalty Lightbox is comming every time and able to login via loyalty Light Box', async () => {
      var x = Math.floor((Math.random() * 99900000) + 1);
      let userName = "LoyaltyTest"+x
      let email = userName+x+"@gmail.com"
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[1]/form/fieldset/input[1]")).sendKeys(email)
      driver.sleep(1000)
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[1]/form/fieldset/button/span[2]")).click();
      driver.sleep(3000)
      let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      await driver.findElement(By.xpath("//input[@placeholder='CREATE YOUR PASSWORD']")).sendKeys("123456ab");
    }else {
        await driver.findElement(By.xpath("//input[@placeholder='Create Your Password']")).sendKeys("123456ab");
    }
      driver.sleep(1000)
      await driver.findElement(By.xpath("//*[text()='Join Now']")).click();
      driver.sleep(10000)
      console.log ("Loyalty User created from lightbox >> " + email)
      const myaccount = await driver.findElement(By.xpath("//*[text()='My Account']"))
      expect(myaccount).toBeTruthy()
      console.log("Logged in to application using loyalty lightbox successfully")
    })
