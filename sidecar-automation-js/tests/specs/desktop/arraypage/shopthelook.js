import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, shopTheLookValidations } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
})

test('shop the look validation', async () => {
  let result = false;
  console.log("verifying shop the look")
  await productArrayPage()
  await closeIconInPAP()
  result = await shopTheLookValidations();
  expect(result).toBeTruthy();
})

afterAll(async () => {
  await driver.quit()
})
