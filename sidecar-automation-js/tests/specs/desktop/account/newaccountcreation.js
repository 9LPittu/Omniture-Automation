import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard ,guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  await driver.manage().window().maximize()
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('test creation of new account', async () => {
    driver.sleep(2000);
    try {
      await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
      // console.log("inside merge page")
       privacyPolicyClose.click()
       driver.sleep(3000)
     })
     } catch (err)
    { }
// let i = 0;
// for (i = 1; i < 100; i++) {
var x = Math.floor((Math.random() * 1000000) + 1);
 let userName = "AutomationTest"+x
 let email = "AutomationTest"+x+"@gmail.com"
    await driver.navigate().to(globals.__baseUrl__+"/r/login")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
    await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()

    await driver.sleep(2000)
    await driver.navigate().refresh()
    await driver.sleep(2000)
    console.log ("User created >> " + userName)

   const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
   expect(userPannel).toBeTruthy()

   userPannel.click()

    await driver.sleep(3000)
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {

      await driver.findElement(By.xpath(".//li[text()='Sign Out']")).click()
      await driver.sleep(3000)
    } else {
      await driver.findElement(By.xpath(".//span[text()='My Account']")).click()
      await driver.findElement(By.xpath(".//li[text()='Sign Out']")).click()
    await driver.sleep(3000)
   }
//}
   })

   afterAll(async () => {
     await driver.quit()
   })
