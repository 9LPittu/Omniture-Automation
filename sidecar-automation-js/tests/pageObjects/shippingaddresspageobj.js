import { driver } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import {goToShoppingBag} from '../pageObjects/shoppingbagobj';
import {login,loginInAfterCheckoutPage} from '../pageObjects/loginpageobj';

const { Builder, By, Key, until } = require('selenium-webdriver');
export const zipCode = () => driver.findElement(By.css("input#zipcode"));
export const multiShipAddr = () =>  driver.findElement(By.xpath("//input[@type='checkbox' and @name='multiShippingAddresses']"));
export const continueButton = () => driver.findElement(By.css("a#order-summary__button-continue"));
export const inStorePick = () => driver.findElement(By.xpath("//div[@class='form-radio-set hide']/label/input[@name='numberOfShippingAddress']"));
export const overnight = () => driver.findElement(By.css("#method0"));
export const expedited = () => driver.findElement(By.css("#method1"));
export const standard = () => driver.findElement(By.css("#method2"));
export const economy = () => driver.findElement(By.css("#method3"));
export const giftsYesRadio = () => driver.findElement(By.css("#includesGifts"));
export const just_a_Gift_Receipt = () => driver.findElement(By.css("#giftReceiptMessage0"));
export const gift_Wrapping_Service = () => driver.findElement(By.css("#giftWrapService0"));

/**

**/
export const addEditAdress = async () =>{

      let editLink =   await driver.findElement(By.xpath("//*[@id='address-1']/parent::label/following-sibling::span/a[1]"));
      //  expect(editLink.isDisplayed()).toMatch(true);
      //  expect(removeLink.isDisplayed()).toMatch(true);
      editLink.click();
      //	//input[@id='address3']
       await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys("Testing");
       //	//a[@id='submit-new-shipping-address']
       await driver.findElement(By.xpath("//a[@id='submit-new-shipping-address']")).click();
       //	//a[@*='item-link item-link-submit']
       await driver.findElement(By.xpath("//a[@*='item-link item-link-submit']")).click();
       let addressListSize
       //let addressListSize = await driver.findElements(By.xpath("//*[@id='address-book']/ul/li")).size();
       driver.findElements(By.xpath("//*[@id='address-book']/ul/li")).then(function(links){

         console.log('Found', links.length, 'addresses size.' )
         console.log("//*[@id='address-book']/ul/li["+links.length+"]/*/span")
         let windowText =  driver.findElement(By.xpath("//*[@id='address-book']/ul/li["+links.length+"]/*/span")).getText();
         console.log('Text compared:: '+windowText)

          });
};

export const addEditRemoveAddress = async () =>{
  //#address-new
  await driver.sleep(4000);
    await driver.findElement(By.css("#nav-shipping")).click();
   await driver.findElement(By.css("#address-new")).click();
   await driver.findElement(By.css("#firstNameAM")).sendKeys("Auto Tester1 FN");
     await driver.findElement(By.css("#lastNameAM")).sendKeys("Auto Tester1 LN");
       await driver.findElement(By.css("#address3")).sendKeys("Nothing");
         await driver.findElement(By.css("#address1")).sendKeys("44 building-lvl 45");
         //address2
         await driver.findElement(By.css("#address2")).sendKeys("address test");
           await driver.findElement(By.css("#zipcode")).sendKeys("10012");
           await driver.sleep(3000);
             await driver.findElement(By.css("#phoneNumAM")).sendKeys("9658742361");
             //.button-submit

                await driver.findElement(By.css("#submit-new-shipping-address")).click();
                await driver.sleep(5000);

                // if(await driver.findElement(By.css("#dropdown-state-province")).isDisplayed()){
                //   await driver.findElement(By.css("#city")).sendKeys("ALTOONA");
                // //  await driver.Select(driver.findElement(By.css("#dropdown-state-province"))).selectByValue("AK");
                //   await driver.wait(
                //       until.elementLocated(By.id("dropdown-state-province")), 20000
                //   ).then(element => {
                //       selectByVisibleText(element, "AK")
                //   });
                //   await driver.findElement(By.css("#submit-new-shipping-address")).click();
                // }
                await driver.findElement(By.xpath("//a[@class='button-submit']")).click();
                //#nav-shipping
                await driver.findElement(By.css("#nav-shipping")).click();
                await driver.findElement(By.xpath("//*[@id='address-2']/parent::label/following-sibling::span/a[2]")).click();
                driver.sleep(3000)

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

  export const removeAdress = async () =>{
        let removeLink =   await driver.findElement(By.xpath("//*[@id='address-1']/parent::label/following-sibling::span/a[2]"));

        removeLink.click();


    };

      export const clickOnInStorPickup = async ()=>{
        let pickup =   await driver.findElement(By.xpath("//div[@class='form-radio-set hide']/label/input[@name='numberOfShippingAddress']"));
        pickup.click();
      }
//input#zipcode

    export const inputZipCode = async ()=>{
      let zipcode =   await driver.findElement(By.css("input#zipcode"));
      zipcode.sendKeys("20009");
    }
    /**

    **/
  export const clickMultiAddress = async () =>{
    let multiShippingAddresses =   await driver.findElement(By.xpath("//input[@type='checkbox' and @name='multiShippingAddresses']"));
    multiShippingAddresses.click();

  };


  /**

  **/
    export const clickOnContinue = async ()=>{
        await driver.findElement(By.css("a#order-summary__button-continue")).click();

    }
//#miniCartTabQty
export const verifyShipToMultiAddress = async ()=>{
  let quantity = await driver.findElement(By.css("#miniCartTabQty")).getText();

  // if(!(quantity == ("(1)"))){
  //   //await driver.findElement(By.css("##multiShippingAddresses_checkbox")).isDisplayed()
  //    expect(await driver.findElement(By.css("#multiShippingAddresses_checkbox")).isDisplayed()).not.toBeTruthy();
  // }else{
  //   expect(await driver.findElement(By.css("#multiShippingAddresses_checkbox")).isDisplayed()).toBeTruthy();
  // }
}
