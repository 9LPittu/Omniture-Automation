import { driver } from '../../../helpers';
import { globals } from '../../../jestJcrewQaConfig';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { clickOnContinue } from '../../../pageObjects/shippingaddresspageobj';
import { loginFromHomePage, clearBagItems } from '../../../pageObjects/loginPageObj';
import { verifyShippingMethodsPage } from '../../../pageObjects/shippingpageobj';
import { jcrew_gold, jcrew_prod,factory_prod } from '../../../testdata/jcrewTestData';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { checkout, shippingNavBtn,shippingNavigation } from '../../../pageObjects/Shippingpageobj';


beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  console.log('Home page loaded proprely')
});

test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();
  if ((url.indexOf("www.jcrew.com") > -1) || (url.indexOf("https://or.jcrew.com") > -1)) {
    await loginFromHomePage(jcrew_prod.username, jcrew_prod.password)
    console.log('user login succesfully')
  } else if((url.indexOf("factory.jcrew.com") > -1) || (url.indexOf("https://or.factory") > -1)){

    await loginFromHomePage(factory_prod.username,factory_prod.password)
    console.log('user login succesfully')
}
  else {
    await loginFromHomePage(jcrew_gold.username, jcrew_gold.password)
    console.log('user login succesfully')
  }

});

test('Clear the bag items if any products were avilable and Add one product', async () => {
  await clearBagItems();
  console.log('after clearing bagItem')
  await driver.sleep(10000);
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await checkout()
  console.log('after product selection')
  console.log('After checkout')
  //await shippingNavBtn()
  await shippingNavigation()
  await driver.sleep(5000);
  console.log('After checkout')
  //await clickOnContinue();
  console.log("after continue")
});

test('Goto ShippingMethod and verify all shipping methods and Gift Wrappings avilable', async () => {
  await verifyShippingMethodsPage();
  console.log('verified shipping Methods')
});

afterAll(async () => {
  await driver.quit()
})
