import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { verifyPreSelectedSize } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verifying preselectedsize', async () => {
  await verifyPreSelectedSize();
})

afterAll(async () => {
  //await driver.quit()
})
