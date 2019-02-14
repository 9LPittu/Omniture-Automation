import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { clickCheckoutBtn, checkoutAsGuest, enterCreditCardDetails, validateExpressUserCheckoutOrder, registerNowValidation, brizateValidationForGold} from '../../../pageObjects/checkoutObj';
import { waitSeconds } from '../../../util/commonutils';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
})

test('test Guest checkout', async () => {
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(2)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await checkoutAsGuest()

  //credit card details
  await enterCreditCardDetails()

  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    await waitSeconds(3)
    let getUrl = await driver.getCurrentUrl();
    await validateExpressUserCheckoutOrder(getUrl)
    await registerNowValidation()
    await productArrayPage();
    await addProductToBag();
    await verifyAndClickOnBag();
    await waitSeconds(1)
    await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
    await waitSeconds(1)
    await clickCheckoutBtn()
    let currentUrl = await driver.getCurrentUrl();
    await brizateValidationForGold(currentUrl, creditcard.pin)
  }
})
afterAll(async () => {
  await driver.quit()
})
