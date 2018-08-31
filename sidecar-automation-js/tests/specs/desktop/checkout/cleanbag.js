import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })
  test('Clean Bag', async () => {
    let i =1;
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
      await driver.sleep(12000)
      let currentUrl = await driver.getCurrentUrl();
      if(currentUrl==globals.__baseUrl__+"/"){
        console.log("cleared the bag")
      }
     })
