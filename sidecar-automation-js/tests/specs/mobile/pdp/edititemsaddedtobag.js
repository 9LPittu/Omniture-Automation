import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory,selectItemAddToBag, verifyBag } from '../../../mobilepageobjects/marraypageobj';
import { editItemAddedToBag } from '../../../mobilepageobjects/mpdppage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Edit item added to bag', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
  let result = await editItemAddedToBag()
  expect(result).toBeTruthy()
})

afterAll(async () => {
  await driver.quit()
})
