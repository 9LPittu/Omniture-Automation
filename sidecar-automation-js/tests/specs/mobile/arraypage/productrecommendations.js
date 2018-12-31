import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, verifyProductRecommendations } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('product recommendations validation', async () => {
  await selectCategory();
  await verifyProductRecommendations();
  console.log("product recommendations are displayed")
})

afterAll(async () => {
  await driver.quit();
})
