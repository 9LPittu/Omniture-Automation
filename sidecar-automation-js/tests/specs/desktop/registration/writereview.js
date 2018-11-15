import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage , validateWritingReviews} from '../../../pageObjects/arraypage';
import { logindetails } from '../../../testdata/jcrewTestData';
import { loginFromHomePage } from '../../../pageObjects/loginPageObj';

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')
});


test('verify write a review functionality', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await loginFromHomePage(logindetails.loyaltyuser, logindetails.password);
  await productArrayPage()
  await closeIconInPAP()
  await validateWritingReviews()
});

afterAll(async () => {
  await driver.quit()
})
