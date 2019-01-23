import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage, addProductToBagForEdit, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { editItemAddedToBag } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Editing item added to bag', async () => {
  console.log("Editing item added to bag")
  await driver.manage().timeouts().implicitlyWait(20000)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await driver.sleep(3000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBagForEdit()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  let result = await editItemAddedToBag();
  expect(result).toBeTruthy()
})

afterAll(async () => {
  await driver.quit()
})
