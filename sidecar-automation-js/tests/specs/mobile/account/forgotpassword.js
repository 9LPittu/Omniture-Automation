import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import {clickOnForgotPasswordLink,clickSendMeNewPasswordButton } from '../../../mobilepageobjects/mloginpageobj';


const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
   await load();
   expect(await driver.getTitle()).toMatch('J.Crew')
  })

   test('Verifying forgot password functionality', async () => {
    await clickOnForgotPasswordLink();
    await clickSendMeNewPasswordButton();
  })

    afterAll(async () => {
      await driver.quit()
  })
