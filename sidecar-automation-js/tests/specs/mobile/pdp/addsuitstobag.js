import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { addSuitItemsToBag } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple Suit items to Bag', async () => {
  await addSuitItemsToBag();
})

afterAll(async () => {
  await driver.quit()
})
