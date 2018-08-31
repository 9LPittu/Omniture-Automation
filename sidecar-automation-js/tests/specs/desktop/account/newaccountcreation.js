import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard ,guestuser } from '../../../testdata/jcrewTestData';
import {createNewAccount} from '../../../pageObjects/loginPageObj'
const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('test creation of new account', async () => {
        await createNewAccount()
        await driver.actions().mouseMove(await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))).perform();
        await driver.sleep(1000)
        await driver.findElement(By.xpath(".//*[text()='Sign Out']")).click()
        await driver.sleep(5000)
        expect(await driver.findElement(By.xpath(".//*[text()='sign in']"))).toBeTruthy()
        console.log("sign out from the application successfully")
        await driver.sleep(1000)
   })

   afterAll(async () => {
     await driver.quit()
   })
