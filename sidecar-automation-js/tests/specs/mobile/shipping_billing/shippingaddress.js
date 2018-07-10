import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import { guestuser } from '../../../testdata/jcrewTestData';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginPageObj';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';
import {click,scroll} from "../../../util/MobileMethods"

const { By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('Login into application with username and password', async () =>{
//await driver.findElement(By.id("c-header__userpanel")).click();


let url = await driver.getCurrentUrl();

if (url.indexOf("www.jcrew.com") > -1) {
  await driver.get("https://www.jcrew.com/r/login");
  await driver.findElement(By.id("sidecarUser")).sendKeys(jcrew_prod.username);
  await driver.findElement(By.id("sidecarPassword")).sendKeys(jcrew_prod.password);
  await driver.findElement(By.className("btn--primary btn--signin js-button-submit")).click();

  console.log('user login succesfully')
}else{
  await driver.get("https://or.jcrew.com/r/login");
  await driver.sleep(5000);
  await driver.findElement(By.id("sidecarUser")).sendKeys(jcrew_gold.username);
  await driver.findElement(By.id("sidecarPassword")).sendKeys(jcrew_gold.password);
  await driver.findElement(By.className("btn--primary btn--signin js-button-submit")).click();
  console.log('user login succesfully')
}
await clearBagItems();


 });

test('Go to shoppingBag page and select product', async () => {
  await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']")).click();
  await driver.findElement(By.xpath("//*[@class='js-header__search__input header__search__input']")).sendKeys('F4212');
  await driver.findElement(By.xpath("//*[@class='icon-searchtray icon-searchtray-search']")).click();
  await driver.findElement(By.xpath("//a[@class='product-tile__link']/img")).click();
  await driver.sleep(7000)
  //  await driver.findElement(By.id("button-checkout")).click();
  //li[@data-name='4']
  //id--btn__add-to-bag-wide
  //span[@class='primary-nav__text']
//id--button-checkout
//a[@class='button-general button-submit']

});
 test('select product id and goto shoppingbag page', async () =>{
await scroll(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
await click(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
//await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click();
await driver.sleep(5000)
await click(By.id("btn__add-to-bag-wide"));
//await driver.findElement(By.id("btn__add-to-bag-wide")).click();
await driver.sleep(5000)
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
await driver.sleep(5000);
await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")));
await driver.sleep(7000)
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='button-checkout']")));
await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();


 });

  test('verify add/edit/remove addresses functionaliy in shippingAddress page', async () =>{

    await addEditRemoveAddress();
 });

 export const addEditRemoveAddress = async () =>{
   //#address-new
     await driver.findElement(By.css("#nav-shipping")).click();
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
                 //#nav-shipping
                 await driver.findElement(By.css("#nav-shipping")).click();
                 await driver.findElement(By.xpath("//*[@id='address-2']/parent::label/following-sibling::span/a[2]")).click();
                 driver.sleep(3000)



 }
