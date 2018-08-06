import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {addEditAdress, addEditRemoveAddress, verifyShipToMultiAddress} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage, clearBagItems} from '../../../pageObjects/loginpageobj';
import {goToShoppingBag, loginAsGuestButton, addAddress, clickOnCheckout} from '../../../pageObjects/shoppingbagObj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod,creditcard } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
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

test('Verify Adding and Editing credit card', async () => {
  await driver.sleep(8000);
  //click on Address book
  await driver.findElement(By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[5]/a")).click();
  await driver.manage().window().maximize()
  await driver.sleep(5000);
    try {
      // click on Add address
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//*[@id='creditCardList']/table[2]/tbody/tr[2]/td/input[2]")));
      await driver.findElement(By.xpath("//*[@id='creditCardList']/table[2]/tbody/tr[2]/td/input[2]")).then(addAddress => {
       addAddress.click()
       driver.sleep(3000)
     })
     } catch (err)
    { }
    await driver.sleep(5000);
    await driver.findElement(By.xpath("//*[@id='ccNumber']")).sendKeys("4111111111111111")
    // await driver.findElement(By.xpath("//input[@id='securityCode']")).sendKeys(creditcard.pin)
    await driver.findElement(By.xpath("//*[@id='eXmonth']")).sendKeys(creditcard.expirationMonth)
    await driver.findElement(By.xpath("//*[@id='eXyear']")).sendKeys(creditcard.expirationYear)
    await driver.findElement(By.xpath("//*[@id='cardholderName']")).sendKeys("AutomationUser")
    await driver.findElement(By.xpath("//*[@id='selectAddressList']")).sendKeys("45,vstreet")
    await driver.sleep(2000)
    // Edit button
    let addConfirm = await driver.findElement(By.xpath("//*[@id='creditCardList']/table[1]/tbody/tr[3]/td/a[1]")).isDisplayed()
    expect(addConfirm).toBeTruthy()
    // Verifying Edit card
    await driver.findElement(By.xpath("//*[@id='creditCardList']/table[1]/tbody/tr[3]/td/a[1]")).click()
    await driver.sleep(3000);
    await driver.findElement(By.xpath("//*[@id='ccNumber']")).clear()
    await driver.findElement(By.xpath("//*[@id='ccNumber']")).sendKeys("4111111111111111")
    await driver.findElement(By.xpath("//*[@id='eXmonth']")).sendKeys(creditcard.expirationMonth)
    await driver.findElement(By.xpath("//*[@id='eXyear']")).sendKeys(creditcard.expirationYear)
    await driver.findElement(By.xpath("//*[@id='cardholderName']")).clear()
    await driver.findElement(By.xpath("//*[@id='cardholderName']")).sendKeys("AutomationUser")
    await driver.sleep(2000)
    await driver.findElement(By.xpath("//*[@id='AddCreditCard']/table/tbody[4]/tr/td[4]/input[2]")).click()
    let addConfirm1 = await driver.findElement(By.xpath("//*[@id='creditCardList']/table[1]/tbody/tr[3]/td/a[1]")).isDisplayed()
    expect(addConfirm1).toBeTruthy()
});
