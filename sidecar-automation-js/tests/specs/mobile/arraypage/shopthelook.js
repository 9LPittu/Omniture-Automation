import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, verifyShopTheLook } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('shop the look validation', async () => {
  await selectCategory();
  await verifyShopTheLook();
})
afterAll(async () => {
  await driver.quit()
})