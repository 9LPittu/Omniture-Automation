import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, selectItemAddToBag, verifyBag } from '../../../mobilepageobjects/marraypageobj';
import { validateShoppingBagPage } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify shopping Bag page', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
  await validateShoppingBagPage()
})

afterAll(async () => {
  await driver.quit()
})
