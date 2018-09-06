import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/shoppingbagObj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');

test('Go to home page', async () => {
    await load();
    await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
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

 test('Verify Adding Editing Shipping addresses', async () => {
   await driver.sleep(8000);
   //click on Address book
   await driver.findElement(By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[6]/a")).click();
   let url = await driver.getCurrentUrl();
   if(url.includes("factory")){
     await driver.sleep(3000);

     if(await driver.findElement(By.xpath("(//img[contains(@src,'btn_edit')])[1]")).isDisplayed()){
       await driver.findElement(By.xpath("(//img[contains(@src,'btn_edit')])[1]")).click()
       await driver.sleep(1000)
       await driver.findElement(By.name("ADDRESS<>firstName")).clear()
       await driver.findElement(By.name("ADDRESS<>firstName")).sendKeys("testing")
       await driver.sleep(1000)
       var parent = driver.getWindowHandle();
       await driver.findElement(By.xpath("//*[text()='EDIT ADDRESS & SAVE CHANGES']")).click()
       await driver.sleep(5000)
       await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
           driver.switchTo().window(allhandles[allhandles.length - 1]);
       });
       await driver.findElement(By.xpath("//a[text()='Use This Address']")).click()
       await driver.sleep(3000)
       await driver.switchTo().window(parent)
       await driver.findElement(By.xpath("//*[text()='EDIT ADDRESS & SAVE CHANGES']")).click()
       await driver.sleep(1000)
     }
   }else{
     if(await driver.findElement(By.xpath("(//*[text()='EDIT'])[1]")).isDisplayed()){
       await driver.findElement(By.xpath("(//*[text()='EDIT'])[1]")).click()
       await driver.sleep(1000)
       await driver.findElement(By.name("ADDRESS<>firstName")).clear()
       await driver.findElement(By.name("ADDRESS<>firstName")).sendKeys("testing")
       await driver.sleep(1000)
       var parent = driver.getWindowHandle();
       await driver.findElement(By.xpath("//*[text()='EDIT ADDRESS & SAVE CHANGES']")).click()
       await driver.sleep(5000)
       await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
           driver.switchTo().window(allhandles[allhandles.length - 1]);
       });
       await driver.findElement(By.xpath("//a[text()='Use This Address']")).click()
       await driver.sleep(3000)
       await driver.switchTo().window(parent)
       await driver.findElement(By.xpath("//*[text()='EDIT ADDRESS & SAVE CHANGES']")).click()
       await driver.sleep(1000)
   }
}

      if(url.includes("factory")){
        driver.sleep(1000)
        await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//img[contains(@src,'btn_add_new_address')]")));
        driver.sleep(1000)
        await driver.findElement(By.xpath("//img[contains(@src,'btn_add_new_address')]]")).then(addAddress => {
         addAddress.click()
         driver.sleep(3000)
       })
      }else{
        driver.sleep(1000)
        await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[text()='ADD A NEW ADDRESS']")));
        driver.sleep(1000)
        await driver.findElement(By.xpath("//a[text()='ADD A NEW ADDRESS']")).then(addAddress => {
         addAddress.click()
         driver.sleep(3000)
       })
      }
// First name
await driver.findElement(By.name("ADDRESS<>firstName")).sendKeys("Auto Tester1 FN");
// Last name
await driver.findElement(By.name("ADDRESS<>lastName")).sendKeys("Auto Tester1 LN");
// Address
await driver.findElement(By.name("ADDRESS<>address1")).sendKeys("1111 East 60th Street");
// City
await driver.findElement(By.name("ADDRESS<>city")).sendKeys("Chicago");
// State
await driver.findElement(By.name("ADDRESS<>state_cd")).sendKeys("Illinois");
// Zip code
await driver.findElement(By.name("ADDRESS<>postal")).sendKeys("60637");
// Phonenumber
await driver.findElement(By.name("ADDRESS<>phone")).sendKeys("7737029494");
var parent = driver.getWindowHandle();
// Save button
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[35]/td/a")).click()
driver.sleep(5000)
await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
    driver.switchTo().window(allhandles[allhandles.length - 1]);
});
// Use this address
await driver.findElement(By.xpath("//a[text()='Use This Address']")).click()
driver.sleep(2000)
await driver.switchTo().window(parent)
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[35]/td/a")).click()
driver.sleep(5000)
if(url.includes("factory")){
  await driver.findElement(By.xpath("(//img[contains(@src,'btn_delete')])[2]")).click()
  driver.sleep(2000)
}else{
  await driver.findElement(By.xpath("(//a[text()='DELETE'])[2]")).click()
  driver.sleep(2000)
  driver.actions().sendKeys(Key.ENTER).perform();
  /*Robot robot1 = new Robot();
  robot1.keyPress(KeyEvent.VK_ENTER);
  robot1.keyRelease(KeyEvent.VK_ENTER);
  driver.sleep(2000)*/
}
});
