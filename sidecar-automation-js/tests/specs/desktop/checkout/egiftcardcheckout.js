import { driver, defaultTimeout } from '../../../helpers';
import { load ,closeIconInPAP} from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { loginFromHomePage, loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { verifyAndClickOnBag ,validateSignOut} from '../../../pageObjects/arraypage';
import { goToGiftCardsPage,addEgiftCard,clickCheckoutBtn,brizateValidationForGold} from '../../../pageObjects/checkoutObj';
import { waitSeconds } from '../../../util/commonutils';

beforeAll(async () => {
  await load();
})

test('eGiftCard Checkout - Express User', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await loginFromHomePage(logindetails.username, logindetails.password)
  await waitSeconds(2)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await waitSeconds(2)
  let currentUrl = await driver.getCurrentUrl();
  await validateSignOut(currentUrl)
  await waitSeconds(3)
  await goToGiftCardsPage()
  await waitSeconds(2)
  await closeIconInPAP()
  await addEgiftCard()
  await verifyAndClickOnBag()
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password)
  await waitSeconds(2)
  await brizateValidationForGold(currentUrl,creditcard.pin)
})


afterAll(async () => {
  await driver.quit()
})
