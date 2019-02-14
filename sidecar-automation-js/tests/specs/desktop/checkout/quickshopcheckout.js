import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage } from '../../../pageObjects/arraypage'
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { mergeButton } from '../../../pageObjects/ShoppingBagObj';
import { quickShopCheckoutValidation,clickCheckoutBtn,quickShopReview } from '../../../pageObjects/checkoutObj';
import { waitSeconds } from '../../../util/commonutils';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
})

test('Quick shop Checkout - Express User', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await waitSeconds(1)
  await quickShopCheckoutValidation()
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password);
  await mergeButton()
  let currentUrl = await driver.getCurrentUrl();
  await quickShopReview(currentUrl,creditcard.pin)
})

afterAll(async () => {
  await driver.quit()
})
