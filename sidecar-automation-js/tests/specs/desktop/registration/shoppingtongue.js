import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, addProductToBag, validateShoppingTongue } from '../../../pageObjects/arraypage';
import { loginFromHomePage, clearBagItems } from '../../../pageObjects/loginpageobj';
import { jcrew_gold, jcrew_prod, factory_gold, factory_prod } from '../../../testdata/jcrewTestData';
import { waitSeconds } from '../../../util/commonutils';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  console.log('Home page loaded proprely')
});

test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username, jcrew_prod.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("uat.jcrew.com") > -1)) {

    await loginFromHomePage(jcrew_gold.username, jcrew_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("uat.factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_gold.username, factory_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("https://factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_prod.username, factory_prod.password)
    console.log('user login succesfully')
  }

});

test('Add product to bag', async () => {
  await waitSeconds(10)
  await clearBagItems()
  await waitSeconds(12)
  await productArrayPage()
  await addProductToBag()
});

test('verify shopping tongue issue was not there', async () => {
  let url = await driver.getCurrentUrl();
  await validateShoppingTongue(url);
  console.log("shopping tonge is displayed")
});

afterAll(async () => {
  await driver.quit()
})
