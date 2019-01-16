import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { logindetails } from '../../../testdata/jcrewTestData';
import { loginFromHomePage } from '../../../mobilepageobjects/mloginpageobj';
import { removeAddedCreditCard } from '../../../mobilepageobjects/shippingBillingObj';

test('title is correct', async () => {
  await load();
  await waitSeconds(2)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify User is able to login with valid user credentials', async () => {
  await waitSeconds(1)
  await loginFromHomePage(logindetails.username4, logindetails.password4)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await waitSeconds(5)
});

test('Go to shoppingBag page,select product and add to bag', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
});

// In Billing page, Remove Card
test('Verify removing credirCard', async () => {
  await waitSeconds(1)
  await clickOnCheckOutNow()
  await removeAddedCreditCard()
});
