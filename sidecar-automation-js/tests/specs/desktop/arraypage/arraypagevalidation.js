import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, validateProductArrayPage } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
})

test('Product arraypage validation', async () => {
  console.log("Array page is being verified ")
  await driver.manage().timeouts().implicitlyWait(30000);
  await productArrayPage();
  await closeIconInPAP();
  let result = await validateProductArrayPage();
  expect(result).toBeTruthy();
})

afterAll(async () => {
  await driver.quit()
})
