import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import {checkout, shippingNavBtn} from '../../../pageObjects/Shippingpageobj';
import {paymentMethod} from '../../../pageObjects/billingobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';
import {productArrayPage,addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage';

beforeAll(async () => {
   await load();
   await driver.manage().timeouts().implicitlyWait(20000)
   console.log('Home page loaded proprely')
});

test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
    console.log('user login succesfully')
  }else if((url.indexOf("or.jcrew.com") > -1 )){

  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("or.factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_gold.username,factory_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("https://factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_prod.username,factory_prod.password)
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
  await driver.sleep(1000)
  await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await checkout()
  await driver.sleep(1000)
  console.log('after product selection')
  console.log('clicked on checkout')
  await driver.sleep(5000);
  await shippingNavBtn()
  //await driver.findElement(By.id("nav-billing")).click();
  await driver.sleep(3000);
});

test('Goto Billng page and check verify credit/debit card or paypal process', async () => {
      console.log('---')
      await paymentMethod('Paypal');
      console.log('After payment Method')
 });

 afterAll(async () => {
  await driver.quit()
})
