import { driver } from '../../../helpersMobile';
import { load, goToGiftCardsPage } from '../../../mobilepageobjects/mhomepageobj';
import { logindetails} from '../../../testdata/jcrewTestData';
import { waitSeconds } from '../../../util/MobileMethods';
import { addGiftCardToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { loginFromHomePage, clearBagItems } from '../../../mobilepageobjects/mloginpageobj';
import { verifyShoppingBagPage, clickOnCheckOutNow, verifySecureCheckout_ExpressUser, placeOrder } from '../../../mobilepageobjects/singlePageCheckout';

  test('title is correct', async () => {
    await load();
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

  test('Classic Card Checkout - Express User', async () => {
   // await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
    await loginFromHomePage(logindetails.username, logindetails.password)
    await clearBagItems();
    await waitSeconds(12);
    await goToGiftCardsPage();
    await addGiftCardToBag("classicGiftCard");
    await clickOnBagIcon();
    await verifyShoppingBagPage();
    await clickOnCheckOutNow();
    await verifySecureCheckout_ExpressUser();
    await placeOrder();
   })

   afterAll(async () => {
    await driver.quit()
  })
