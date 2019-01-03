import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { addSaleItemsToBag } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple items from Sale category', async () => {
  await addSaleItemsToBag();
})

afterAll(async () => {
  await driver.quit()
})
