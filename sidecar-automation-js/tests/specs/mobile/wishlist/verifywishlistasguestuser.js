import { driver } from '../../../helpersMobile';
import { load, selectCategory} from '../../../mobilepageobjects/mhomepageobj';
import { verifyGuestWishlist } from '../../../mobilepageobjects/marraypageobj';

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Wishlist guestuser functionality', async () => {
  await selectCategory();
  await verifyGuestWishlist()
});
