import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { editItemAddedToBag } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})
test('Editing item added to bag', async () => {
  console.log("Editing item added to bag")
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  let result = await editItemAddedToBag();
  expect(result).toBeTruthy()
})

afterAll(async () => {
  await driver.quit()
})
