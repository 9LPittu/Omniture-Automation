import { driver, defaultTimeout } from '../../../helpers';
import { load, categorymen, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage'
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj'
import { mergeButton } from '../../../pageObjects/ShoppingBagObj'
import { clickCheckoutBtn,STSSameDayDelivery,STSReviewCheckout } from '../../../pageObjects/checkoutObj'

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
  await driver.sleep(1000);
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password);
  await driver.sleep(1000)
  await mergeButton();
  await driver.sleep(2000)
  let currentUrl = await driver.getCurrentUrl();
  await STSSameDayDelivery(currentUrl,zipCode.zipcode)
  await STSReviewCheckout(currentUrl, creditcard.pin)
})


afterAll(async () => {
  await driver.quit()
})
