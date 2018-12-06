import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj'
import { mergeButton } from '../../../pageObjects/ShoppingBagObj'
import { clickCheckoutBtn,STSSameDayDelivery,STSReviewCheckout } from '../../../pageObjects/checkoutObj'
import { waitSeconds } from '../../../util/commonutils'

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('ship to store Checkout', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password);
  await waitSeconds(1)
  await mergeButton();
  await waitSeconds(2)
  let currentUrl = await driver.getCurrentUrl();
  await STSSameDayDelivery(currentUrl,zipCode.zipcode)
  await STSReviewCheckout(currentUrl, creditcard.pin)
})

afterAll(async () => {
  await driver.quit()
})
