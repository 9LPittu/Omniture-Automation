import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import { guestuser } from '../../../testdata/jcrewTestData';
import {productArrayPage, addProductToBag,verifyAndClickOnBag} from '../../../pageObjects/arraypage';

const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')
});
test('verify ship to mutiple Address functionality', async () => {
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await productArrayPage()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()
  console.log('selected the required product')
  await driver.sleep(3000)
   expect(await driver.findElement(By.css("#multiShippingAddresses_checkbox")).isDisplayed()).toBeTruthy();
   await driver.findElement(By.css("#multiShippingAddresses_checkbox")).click();
   await driver.sleep(1000)
   await addGuestFirstAddress();
   //await driver.findElement(By.id("address-new")).click();
   await driver.sleep(1000)
   await addSecondGuestAddress();
   await driver.findElement(By.xpath("//*[@id='order-summary__button-continue']")).click()
  // await driver.findElement(By.id("order-summary__button-continue")).click()
   //shipAddress1
await driver.findElement(By.id("shipAddress1")).isDisplayed();

await driver.findElement(By.id("shipAddress0")).isDisplayed();
   await driver.wait(
       until.elementLocated(By.id("shipAddress1")), 20000
   ).then(element => {
      // selectByVisibleText(element, 0)
   });

      await driver.wait(
          until.elementLocated(By.id("shipAddress0")), 20000
      ).then(element => {
          //selectByVisibleText(element, 1)
      });

  console.log('verified multi shipping button')
});
export const addGuestFirstAddress = async () =>{
  await driver.findElement(By.xpath("//input[@id='firstNameSA']")).sendKeys(guestuser.firstNameSA)
    await driver.findElement(By.xpath("//input[@id='lastNameSA']")).sendKeys(guestuser.lastNameSA)
      await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys(guestuser.address3)
        await driver.findElement(By.xpath("//input[@id='address2']")).sendKeys(guestuser.address2)
          await driver.findElement(By.xpath("//input[@id='address1']")).sendKeys(guestuser.address1)
          await driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys(guestuser.zipcode)
            await driver.sleep(8000);
              await driver.findElement(By.xpath("//input[@id='phoneNumSA']")).sendKeys(guestuser.phoneNumSA)
              await driver.sleep(3000)
              await driver.findElement(By.xpath("//*[@id='main__button-continue']")).click()
              await driver.sleep(3000)
              await driver.findElement(By.xpath("//*[@id='shoppingAddressValidate']/div[2]/a")).click()

}
export const addSecondGuestAddress = async () =>{
  //#address-new

   await driver.findElement(By.css("#address-new")).click();
   await driver.findElement(By.css("#firstNameAM")).sendKeys("Auto Tester1 FN");
     await driver.findElement(By.css("#lastNameAM")).sendKeys("Auto Tester1 LN");
       await driver.findElement(By.css("#address3")).sendKeys("Nothing");
         await driver.findElement(By.css("#address1")).sendKeys("44 building-lvl 45");
         //address2
         await driver.findElement(By.css("#address2")).sendKeys("address test");
           await driver.findElement(By.css("#zipcode")).sendKeys("50009");
           await driver.sleep(8000);
             await driver.findElement(By.css("#phoneNumAM")).sendKeys("9658742361");
             //.button-submit
                await driver.findElement(By.css("#submit-new-shipping-address")).click();
                await driver.sleep(5000);
                await driver.findElement(By.xpath("//a[@class='button-submit']")).click();
}
function selectByVisibleText(select, textDesired) {
    select.findElements(By.tagName('option'))
    .then(options => {
        options.map(option => {
            option.getText().then(text => {
                if (text == textDesired)
                    option.click();
            });
        });
    });
}

afterAll(async () => {
  await driver.quit()
})
