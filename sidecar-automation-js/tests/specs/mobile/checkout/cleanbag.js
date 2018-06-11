import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })
/*

  test('Clean Bag', async () => {
    let i =1;

 //for (i = 1450; i < 1500; i++) {
  let userName = "PerfTest"+i
   let email = "Perftest"+i+"@gmail.com"
      await driver.navigate().to(globals.__baseUrl__+"/r/login")
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys(email)
      await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys("nft123")
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
      await driver.sleep(4000)
      await driver.navigate().to(globals.__baseUrl__+"/CleanPersistentCart.jsp")
      await driver.sleep(3000)

           console.log("Bag cleaned user > " + email )
  //    }
     })

     */
