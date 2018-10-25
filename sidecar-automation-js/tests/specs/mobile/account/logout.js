import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems,signOutFromApplication} from '../../../mobilepageobjects/mloginpageobj';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Verify User is able to login with valid user credentials', async () => {
      await loginFromHomePage(logindetails.username, logindetails.password);
      await signOutFromApplication()
      console.log("sign out from the application successfully")
      expect(await driver.getTitle()).toMatch('J.Crew')
   })

   afterAll(async () => {
    await driver.quit()
  })
