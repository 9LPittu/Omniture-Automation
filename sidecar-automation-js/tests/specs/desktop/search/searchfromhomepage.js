import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP, searchBoxValidation } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage } from '../../../pageObjects/arraypage';

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')
});

test('verifying search functionality', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await searchBoxValidation()
  console.log("search functionality is working")
 });

 afterAll(async () => {
  await driver.quit()
})
