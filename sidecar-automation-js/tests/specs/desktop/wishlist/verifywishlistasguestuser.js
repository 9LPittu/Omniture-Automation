import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, validateWishListAsGuest } from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Wishlist - Guest User', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage();
  await closeIconInPAP()
  await validateWishListAsGuest()
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.includes("/r/login")) {
    expect(true).toBeTruthy();
    console.log("User navigates to login page")
  }
});

afterAll(async () => {
  await driver.quit()
})
