import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
})

test('Adding single / multiple items from single PDP', async () => {
  console.log("Adding single / multiple items from single PDP")
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await driver.sleep(1000)
  await verifyAndClickOnBag()
})

afterAll(async () => {
  await driver.quit()
})
