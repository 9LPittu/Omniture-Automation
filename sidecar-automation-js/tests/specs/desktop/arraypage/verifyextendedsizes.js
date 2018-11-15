import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {verifyExtendedSizesValidation} from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('extended sizes validation', async () => {
  let result = false;
  console.log("Verifying for extended sizes")
  await driver.manage().timeouts().implicitlyWait(20000)
  let currentUrl = await driver.getCurrentUrl();
  result = await verifyExtendedSizesValidation(currentUrl)
  expect(result).toBeTruthy()
  console.log("in test case extended size validations::"+result)
})

afterAll(async () => {
  await driver.quit()
})
