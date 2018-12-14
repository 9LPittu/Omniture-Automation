import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { mergeButton } from '../../../pageObjects/ShoppingBagObj';
import { waitSeconds } from '../../../util/commonutils';
import { clickCheckoutBtn,brizateValidationForGold,saveOrderDetails,expressUserCheckoutValidation} from '../../../pageObjects/checkoutObj';

let currentUrl;

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Express User Checkout and verifying order summary and order history', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password);
  await waitSeconds(2)
  await mergeButton();
  await waitSeconds(2)
  await saveOrderDetails()
  currentUrl = await driver.getCurrentUrl();
  await brizateValidationForGold(currentUrl,creditcard.pin)
})

test('Verify Order Summary', async () => {
   let currentUrl = await driver.getCurrentUrl();
   await expressUserCheckoutValidation(currentUrl)
})

afterAll(async () => {
  await driver.quit()
})
