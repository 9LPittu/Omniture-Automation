import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { addMultiLineItems, verifyAndClickOnBag } from '../../../pageObjects/arraypage'

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple items from multiple PDP', async () => {
  console.log("Adding single/multiple items from multiple PDP test case")
  await driver.manage().timeouts().implicitlyWait(20000)
  await addMultiLineItems()
  await verifyAndClickOnBag()
})

afterAll(async () => {
  await driver.quit()
})
