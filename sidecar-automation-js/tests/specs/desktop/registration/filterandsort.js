import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, validateFilterAndSort } from '../../../pageObjects/arraypage';

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')
});

test('verifying sort and filter functionality', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await driver.sleep(2000)
  await validateFilterAndSort()
  console.log("Sort functionality is working")
});

afterAll(async () => {
  await driver.quit()
})
