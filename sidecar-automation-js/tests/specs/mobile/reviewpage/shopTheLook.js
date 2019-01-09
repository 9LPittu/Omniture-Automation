import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, validateShopTheLookHeader } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify Shop The Look feature', async () => {
  await selectCategory()
  await validateShopTheLookHeader()
})

afterAll(async () => {
  await driver.quit()
})
