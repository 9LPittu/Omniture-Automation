import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard,guestuser,logindetails } from '../../../testdata/jcrewTestData';
import element from '../../../util/commonutils';
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
  await driver.sleep(1000)
  console.log('user login succesfully')
}
await driver.navigate().to(globals.__baseUrl__+"/CleanPersistentCart.jsp")
await driver.sleep(10000)
 });

test('Go to shoppingBag page and select product', async () => {
  await driver.findElement(By.xpath("//span[text()='menu']")).click()
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//a[@data-department='women']")).click()
    await	driver.sleep(1000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await driver.sleep(1000)
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(1000)
       })
       } catch (err)
      { }
    await driver.sleep(1000)
    await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[3]")).click()
    await driver.sleep(1000)

});
 test('select product id and goto shoppingbag page', async () =>{
await scroll(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
await click(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"));
//await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click();
await driver.sleep(1000)
await click(By.id("btn__add-to-bag-wide"));
//await driver.findElement(By.id("btn__add-to-bag-wide")).click();
await driver.sleep(1000)
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
await driver.sleep(1000);
await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")));
await driver.sleep(1000)
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@id='button-checkout']")));
await driver.findElement(By.xpath("//a[@id='button-checkout']")).click();
await driver.sleep(1000)

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
          await driver.findElement(By.css("#address1")).sendKeys("770 broadway");
          //address2
          await driver.findElement(By.css("#address2")).sendKeys("address test");
            await driver.findElement(By.css("#zipcode")).sendKeys("10003");
            await driver.sleep(5000)
              await driver.findElement(By.css("#phoneNumAM")).sendKeys("9658742361");
              //.button-submit

                 await driver.findElement(By.css("#submit-new-shipping-address")).click();
                 await driver.sleep(1000);

                 await driver.findElement(By.xpath("(//input[@name='SelectedAddIndex'])[2]")).click()
                 await driver.sleep(1000)
                 await driver.findElement(By.xpath("//a[@class='button-submit']")).click();
                 //#nav-shipping
                 await driver.sleep(1000)

               }
