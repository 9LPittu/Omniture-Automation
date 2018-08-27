import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';

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
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[1]/form/fieldset/button/span[2]")).click();
      await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[2]/form/input")).click();
      console.log ("Loyalty User created from lightbox >> " + email)
      

      

    })

