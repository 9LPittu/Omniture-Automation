import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(3000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


     test('Verify Loyalty footer Faq links', async () => {

      await driver.sleep(2000)
      await driver.executeScript('window.scrollTo(0, 50000)')
      await driver.sleep(2000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h6[text()='J.CREW REWARDS']")));
      await driver.sleep(2000)
      const jcrewrewards = await driver.findElement(By.xpath("//h6[text()='J.CREW REWARDS']"));
      expect(jcrewrewards).toBeTruthy()
      //verify sign up now link
      const signUpNow = await driver.findElement(By.xpath("//div[text()='Sign Up Now']"));
      expect(signUpNow).toBeTruthy()
      signUpNow.click()
      await driver.sleep(5000)
      let loginurl = await driver.getCurrentUrl();
      expect(loginurl.includes("/r/login")).toBeTruthy()
      console.log("user navigated to login page");
      await driver.sleep(2000)
      driver.navigate().back()
      await driver.sleep(2000)
      const FAQs = await driver.findElement(By.xpath("//div[text()='FAQs']"));
      expect(FAQs).toBeTruthy()
      FAQs.click()
      await driver.sleep(4000)
      let faqurl = await driver.getCurrentUrl();
      expect(faqurl.includes("/l/rewards")).toBeTruthy()
      console.log("user navigated to FAQs page");
      await driver.sleep(2000)
    })
