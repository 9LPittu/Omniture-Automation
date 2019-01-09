import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { logindetails } from '../../../testdata/jcrewTestData';
import { selectCategory, verifyWishListPage } from '../../../mobilepageobjects/marraypageobj';

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Wishlist Registered User functionality', async () => {
  await selectCategory()
  await verifyWishListPage(logindetails.username, logindetails.password)
})
