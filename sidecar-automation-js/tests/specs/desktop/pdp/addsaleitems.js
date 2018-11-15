import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { addsaleitemsToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple items from Sale category', async () => {
  console.log("Adding single / multiple items from Sale category")
  await driver.manage().timeouts().implicitlyWait(20000)
  await addsaleitemsToBag()
  await driver.sleep(1000)
  await verifyAndClickOnBag()
})

afterAll(async () => {
  //await driver.quit()
})
