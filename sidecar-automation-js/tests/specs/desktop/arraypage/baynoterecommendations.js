import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage, addProductToBag, verifyAndClickOnBag, bayNoteRecommondationValidations } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('verifying baynoterecommendations in shopping bag page', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(1000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  let result = await bayNoteRecommondationValidations()
  expect(result).toBeTruthy()
})

afterAll(async () => {
  await driver.quit()
})
