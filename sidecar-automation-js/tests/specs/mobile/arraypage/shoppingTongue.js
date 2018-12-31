import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { verifyShoppingTongue } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Go to shoppingBag page and verify Shopping tongue', async () => {
  await selectCategory();
  await verifyShoppingTongue();
});

afterAll(async () => {
  await driver.quit()
})
