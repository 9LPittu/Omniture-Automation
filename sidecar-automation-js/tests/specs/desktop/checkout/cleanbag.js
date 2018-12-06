import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { loginFromHomePage } from '../../../pageObjects/loginPageObj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { waitSeconds } from '../../../util/commonutils';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Clean Bag', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  let i = 1;
  let userName = "PerfTest" + i
  let email = "Perftest" + i + "@gmail.com"
  let password = "nft123"
  await loginFromHomePage(email, password)
  await waitSeconds(3)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await waitSeconds(2)
  await driver.navigate().to(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
  await waitSeconds(2)

  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl == globals.__baseUrl__) {
    console.log("cleared bag")
  }
})


afterAll(async () => {
  await driver.quit()
})
