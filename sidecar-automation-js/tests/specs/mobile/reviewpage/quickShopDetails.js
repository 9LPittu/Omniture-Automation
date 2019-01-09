import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import { verifyBag, selectCategory, addShopTheLookItemToBag, addShopTheLookItemToWishList, verifyFullDetailsInShopTheLook, verifySizeCartInShopTheLook, validateSizesAndColors } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify add to Bag in Shop The Look Tray', async () => {
  await selectCategory()
  await addShopTheLookItemToBag()
  await verifyBag()
})

test('Verify add to Wish list in Shop The Look Tray', async () => {
  await selectCategory()
  await addShopTheLookItemToWishList(logindetails.username, logindetails.password)
})

test('Verify Size Cart in Shop The Look Tray', async () => {
  await selectCategory()
  await verifySizeCartInShopTheLook()
})

test('Verify Full details in Shop The Look Tray', async () => {
  await selectCategory()
  await verifyFullDetailsInShopTheLook()
})

test('Verify sizes and color swatches in Shop The Look Tray', async () => {
  await selectCategory()
  await validateSizesAndColors()
})

afterAll(async () => {
  await driver.quit()
})
