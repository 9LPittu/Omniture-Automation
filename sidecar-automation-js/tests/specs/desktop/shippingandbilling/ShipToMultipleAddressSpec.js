import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingAddressObj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import { www } from '../../../testdata/Prod';
import { or,Billing } from '../../../testdata/NonProd';
import { guestuser } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});
// test('Login with given username and password', async () => {
//   let url = await driver.getCurrentUrl();
//
//   if (url.indexOf("www.jcrew.com") > -1) {
//
//     await loginFromHomePage(www.username,www.password)
//     console.log('user login succesfully')
//   }else{
//
//   await loginFromHomePage(or.username,or.password)
//   console.log('user login succesfully')
//   }
//
//
//
// });


test('verify ship to mutiple Address functionality', async () => {
await addProductTobag();



  await driver.findElement(By.id("js-header__cart")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
  await driver.findElement(By.xpath("//a[text()='Check Out as a Guest']")).click()

  console.log('selected the required product')
   expect(await driver.findElement(By.css("#multiShippingAddresses_checkbox")).isDisplayed()).toBeTruthy();
   await driver.findElement(By.css("#multiShippingAddresses_checkbox")).click();
   await addGuestFirstAddress();
   //await driver.findElement(By.id("address-new")).click();
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



export const addProductTobag = async () =>{
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
             await driver.findElement(By.css("#phoneNumAM")).sendKeys("9658742361");
             //.button-submit

                await driver.findElement(By.css("#submit-new-shipping-address")).click();
                await driver.sleep(5000);

                if(await driver.findElement(By.css("#dropdown-state-province")).isDisplayed()){
                  await driver.findElement(By.css("#city")).sendKeys("ALTOONA");
                //  await driver.Select(driver.findElement(By.css("#dropdown-state-province"))).selectByValue("AK");
                  await driver.wait(
                      until.elementLocated(By.id("dropdown-state-province")), 20000
                  ).then(element => {
                      selectByVisibleText(element, "AK")
                  });
                  await driver.findElement(By.css("#submit-new-shipping-address")).click();
                }
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
