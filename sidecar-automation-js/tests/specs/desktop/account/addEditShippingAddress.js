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
   await driver.manage().window().maximize()
   await driver.sleep(5000);
     try {
       // click on Add address
       await driver.executeScript('window.scrollTo(0, 50000)')
       driver.sleep(1000) 
       await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[@id='containerBorderLeft']/form/table/tbody/tr[1]/td/table[2]/tbody/tr[1]/td/a")));
       driver.sleep(1000)
       await driver.findElement(By.xpath("//*[@id='containerBorderLeft']/form/table/tbody/tr[1]/td/table[2]/tbody/tr[1]/td/a")).then(addAddress => {
        addAddress.click()
        driver.sleep(3000)
      })
      } catch (err)
     { }

// First name
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[9]/td/input[3]")).sendKeys("Auto Tester1 FN");
// Last name
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[12]/td/input[3]")).sendKeys("Auto Tester1 LN");
// Address
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[18]/td/input[3]")).sendKeys("1111 East 60th Street");
// City
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[24]/td/input[3]")).sendKeys("Chicago");
// State
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[27]/td/select")).sendKeys("Illinois");
// Zip code
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[30]/td/input[3]")).sendKeys("60637");
// Phonenumber
await driver.findElement(By.xpath("//*[@id='phoneText']")).sendKeys("7737029494");
// Save button
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[35]/td/a")).click()
driver.sleep(5000)
// await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
// driver.switchTo().window(allhandles[allhandles.length - 1]);
// }

// var winHandle = await driver.getWindowHandle();
// await driver.switchTo().window(winHandle)
// console.log("Switched to Window");

// Use this address
await driver.findElement(By.xpath("//td/table/tbody/tr/td[1]/a[2]")).click();
// Edit Address
let editAddress = await driver.findElement(By.xpath("//*[@id='containerBorderLeft']/form/table/tbody/tr[1]/td[1]/table[1]/tbody/tr[4]/td/a[1]"))
expect(editAddress).toBeTruthy()
await driver.findElement(By.xpath("//*[@id='containerBorderLeft']/form/table/tbody/tr[1]/td[1]/table[1]/tbody/tr[4]/td/a[1]")).click()
// Save button
await driver.findElement(By.xpath("//*[@id='clearTable']/tbody/tr[35]/td/a")).click()
// Edit Address
let verifyEditAddress = await driver.findElement(By.xpath("//*[@id='containerBorderLeft']/form/table/tbody/tr[1]/td[1]/table[1]/tbody/tr[4]/td/a[1]"))
expect(verifyEditAddress).toBeTruthy()
});
