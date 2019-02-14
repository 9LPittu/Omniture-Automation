import { driver } from '../../../helpers';
import { load, closeIconInPAP,mergeButton } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { loginInAfterCheckoutPage } from '../../../pageObjects/loginPageObj';
import { waitSeconds } from '../../../util/commonutils';
import { scrollToFooter, clickCheckoutBtn, doOrder, clickContextUser,brizateValidationForGold} from '../../../pageObjects/checkoutObj';

const each = require('jest-each')

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
})

each([
  ['Canada'],
  //   ['United Kingdom'],
  //   ['India']
]).test('%s - International Checkout', async contextchooser => {
  await scrollToFooter()
  await closeIconInPAP()
  await waitSeconds(2)
  await clickContextUser(contextchooser)
  await productArrayPage();
  await closeIconInPAP()
  await addProductToBag();
  await verifyAndClickOnBag();
  await waitSeconds(3)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  await clickCheckoutBtn()
  await loginInAfterCheckoutPage(logindetails.username, logindetails.password)
  await waitSeconds(2)
  /*try {
    await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[2]")).then(mergebutton => {
      mergebutton.click()
      driver.sleep(sleeptime)
      driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
    })
  } catch (err) { } */
  await mergeButton()
  await doOrder()
  let currentUrl = await driver.getCurrentUrl();
  await brizateValidationForGold(currentUrl, creditcard.pin)
})

afterAll(async () => {
  await driver.quit()
})
