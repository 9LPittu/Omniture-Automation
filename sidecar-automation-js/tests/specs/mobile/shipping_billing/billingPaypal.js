import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { logindetails } from '../../../testdata/jcrewTestData';
import { loginFromHomePage } from '../../../mobilepageobjects/mloginpageobj';
import { selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/marraypageobj';
import { clickOnPaypalPayment } from '../../../mobilepageobjects/singlePageCheckout';
import { doPaypalPayment } from '../../../mobilepageobjects/shippingBillingObj';
import { waitSeconds } from '../../../util/MobileMethods';

test('title is correct', async () => {
  await load();
  await waitSeconds(2)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify User is able to login with valid user credentials', async () => {
  await loginFromHomePage(logindetails.username4, logindetails.password4)
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await waitSeconds(10)
});

test('Go to shoppingBag page,select product and add to bag', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
});

test('selecting a Paypal method', async () => {
  await clickOnPaypalPayment()
  await doPaypalPayment()
});
