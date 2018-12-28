import { driver } from '../../../helpersMobile';
import { waitSeconds } from '../../../util/commonutils';
import { load,scrollAndClickOnContextChooser,selectAndVerifyCountry } from '../../../mobilepageobjects/mhomepageobj';


const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
   await load();
   expect(await driver.getTitle()).toMatch('J.Crew')
  })

  test('Verify Context Chooser functionality', async () => {
    console.log("User is able to Navigate to country from context chooser");
    await waitSeconds(2);
    await scrollAndClickOnContextChooser();
    await selectAndVerifyCountry();
    console.log("User is able to Navigate to country from context chooser");
  })

  afterAll(async () => {
    await driver.quit()
  })
