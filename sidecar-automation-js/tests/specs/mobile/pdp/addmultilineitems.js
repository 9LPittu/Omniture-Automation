import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { addMultiLineItemToBag } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Adding single / multiple items from multiple PDP', async () => {
  await selectCategory();
  await addMultiLineItemToBag();
})

afterAll(async () => {
  await driver.quit()
})
