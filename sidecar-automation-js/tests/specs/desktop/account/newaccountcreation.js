import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard ,guestuser } from '../../../testdata/jcrewTestData';
import {createNewAccount} from '../../../pageObjects/loginPageObj'

const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('test creation of new account', async () => {
        await createNewAccount()
        let url = await driver.getCurrentUrl()
        if(url.includes("factory")){
        const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
        expect(loggedInUser).toBeTruthy()
        await driver.actions().mouseMove(loggedInUser).perform();
        await driver.sleep(2000)
        const signOut = await driver.findElement(By.xpath("//a[@class='js-signout__link']"))
        expect(signOut).toBeTruthy()
        signOut.click()
        await driver.sleep(5000)
        expect(await driver.findElement(By.xpath(".//*[text()='sign in']"))).toBeTruthy()
        console.log("sign out from the application successfully")
        await driver.sleep(1000)
      }else{
        const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
        expect(loggedInUser).toBeTruthy()
        await driver.actions().mouseMove(loggedInUser).perform();
        await driver.sleep(3000)
        const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
        expect(signOut).toBeTruthy()
        signOut.click()
        await driver.sleep(5000)
        expect(await driver.findElement(By.xpath(".//*[text()='Sign In']"))).toBeTruthy()
        console.log("sign out from the application successfully")
        await driver.sleep(1000)
      }

   })

   afterAll(async () => {
     await driver.quit()
   })
