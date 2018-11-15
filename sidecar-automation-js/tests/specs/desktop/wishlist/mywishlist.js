import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, myWishListValidation,goToWishList,validateWishListBag } from '../../../pageObjects/arraypage';
import { loginFromHomePage } from '../../../pageObjects/loginpageobj';
import { jcrew_gold, jcrew_prod, factory_gold, factory_prod } from '../../../testdata/jcrewTestData';

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

test('Add product to wishlist', async () => {
  await driver.sleep(2000)
  await productArrayPage()
  await closeIconInPAP()
  await myWishListValidation()
});

test('Go to wish list to after adding the product to wishlist', async () => {
  let url = await driver.getCurrentUrl();
  await goToWishList(url)
});

test('verify the product has been added to wishlist or not', async () => {
  await validateWishListBag()
});

afterAll(async () => {
  await driver.quit()
})
