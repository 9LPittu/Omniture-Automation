import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {editAdress} from '../../../pageObjects/shippingaddresspageobj';
import { guestuser } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');



test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});
test('select product id and goto PAP page ', async () =>{
  await selectProduct();
  console.log('product selection done')

//await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()


});

test('verify color swatch is Displayed in pap page on hovering the product', async () =>{
  await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]"))).perform();
   expect(await driver.findElement(By.xpath("//ul[@class='product__colors colors-list']")).isDisplayed()).toBeTruthy();
    console.log('color swatch is Displayed')


});



export const selectProduct = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
  }
  await driver.sleep(3000)




}
