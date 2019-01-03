import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { addSingleLineItemToBag } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple items from single PDP', async () => {
  await selectCategory();
  await addSingleLineItemToBag();
})

afterAll(async () => {
  await driver.quit()
})
