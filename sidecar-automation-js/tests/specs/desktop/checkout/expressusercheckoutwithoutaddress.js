import { driver } from '../../../helpers';
import { loadLoginUrl, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { createNewAccount } from '../../../pageObjects/loginPageObj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { waitSeconds } from '../../../util/commonutils';
import { clickCheckoutBtn,expressUserWithoutAddressValidation,validateExpressUserCheckoutOrder} from '../../../pageObjects/checkoutObj';

beforeAll(async () => {
  await loadLoginUrl();
})

test('Non express User Checkout (user without address/credit card details)', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await createNewAccount();
  await productArrayPage();
  await closeIconInPAP();
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn();
  await expressUserWithoutAddressValidation()
  let currentUrl = await driver.getCurrentUrl();
  await validateExpressUserCheckoutOrder(currentUrl)
})

afterAll(async () => {
  await driver.quit()
})
