import { driver } from '../../../helpersMobile';
import { waitSeconds } from '../../../util/MobileMethods';
import { load,scrollAndClickOnContextChooser,selectAndVerifyCountry } from '../../../mobilepageobjects/mhomepageobj';

const each = require('jest-each')

beforeAll(async () => {
   await load();
   await driver.manage().timeouts().implicitlyWait(20000)
   expect(await driver.getTitle()).toMatch('J.Crew')
  })

  each([
    ['Canada'],
  ]).test('Verify Context Chooser functionality', async contextchooser => {
    console.log("User is able to Navigate to country from context chooser");
    await waitSeconds(2);
    console.log("should display canada"+contextchooser)
    await scrollAndClickOnContextChooser();
    await selectAndVerifyCountry(contextchooser);
    console.log("User is able to Navigate to country from context chooser");
  })

  afterAll(async () => {
    await driver.quit()
  })
