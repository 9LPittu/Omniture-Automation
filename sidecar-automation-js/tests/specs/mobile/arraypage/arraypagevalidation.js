import { driver, defaultTimeout } from '../../../helpersMobile';
import { selectCategory, validateAyyayPage } from '../../../mobilepageobjects/marraypageobj';
import { load } from '../../../mobilepageobjects/mhomepageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Product arraypage validation', async () => {
  console.log("Array page is being verified")
  await selectCategory();
  await validateAyyayPage();
})

afterAll(async () => {
  await driver.quit()
})
