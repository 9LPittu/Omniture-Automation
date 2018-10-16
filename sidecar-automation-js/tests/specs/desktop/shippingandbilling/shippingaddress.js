import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { addEditRemoveAddress} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginpageobj';
import {productArrayPage, addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
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
  console.log('cleared the bag items')
  driver.sleep(10000);
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  console.log('selected the required product')
  console.log('clicked on checkout')
});

test('Verify Adding/Editing/Removing different addresses', async () => {
  await addEditRemoveAddress();
  console.log('verified add/Edit and remove functionalities')
});

afterAll(async () => {
  await driver.quit()
})
