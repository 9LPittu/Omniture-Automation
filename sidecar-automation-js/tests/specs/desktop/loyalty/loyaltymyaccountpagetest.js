import { driver } from '../../../helpers';
import { load , loyaltyMyAccountPageValidation} from '../../../pageObjects/jcrewdesktoppageobj';
import { jcrew_gold, jcrew_prod, factory_gold, factory_prod } from '../../../testdata/jcrewTestData';
import { loginFromHomePage } from '../../../pageObjects/loginPageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username, jcrew_prod.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("or.jcrew.com") > -1)) {

    await loginFromHomePage(jcrew_gold.username, jcrew_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("or.factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_gold.username, factory_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("https://factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_prod.username, factory_prod.password)
    console.log('user login succesfully')
  }
});

test('Verify My Account page', async () => {
  await loyaltyMyAccountPageValidation()
  console.log("Your Rewards, Your Benefits and Your Activity tabs are displayed on account rewars page");
})

afterAll(async () => {
  await driver.quit()
})
