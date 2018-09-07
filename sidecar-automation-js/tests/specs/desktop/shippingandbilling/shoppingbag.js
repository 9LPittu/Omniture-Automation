import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {editAdress} from '../../../pageObjects/shippingaddresspageobj';
import { guestuser } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');



beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});
test('select product id and goto shoppingbag page', async () =>{
  await addProductTobag();
  console.log('product selection done')
  await driver.findElement(By.id("js-header__cart")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()


  console.log('selected the required product')

});

test('click on login as Guest button', async () =>{
  await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()
  console.log('login as Guest succesfully')

});


test('Add the address information', async () =>{
await addGuestFirstAddress();

console.log('Added the address and clicked on continue')

});

export const addProductTobag = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[5]")).click()

  await driver.sleep(2000)
  await driver.navigate().refresh()
  await driver.sleep(3000)
  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
  await driver.sleep(3000)
  await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(3000)
    await driver.findElement(By.id("btn__add-to-bag-wide")).click()
}

export const addGuestFirstAddress = async () =>{
  await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(guestuser.firstNameSA)
    await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
      await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
        await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
          await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
          await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
            await driver.sleep(3000)
              await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
              await driver.sleep(3000)
              await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
              await driver.sleep(3000)
              await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).click()

}

afterAll(async () => {
  await driver.quit()
})