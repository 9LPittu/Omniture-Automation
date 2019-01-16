import { driver, defaultTimeout } from '../helpersMobile';
import { creditcard, guestuser, zipCode } from '../testdata/jcrewTestData';
import { waitSeconds } from '../util/MobileMethods';

const { Builder, By, Key, until } = require('selenium-webdriver')

const shoppingBagHeader = By.xpath("//h1[text()='Shopping Bag']");
const shoppingBagItems = By.xpath("//div[@id='order-listing']/h2[@class='page-subtitle']");//By.xpath("//*[contains(@class,'shopping_bag')]/p/span[2]");
const checkoutNowButton = By.xpath("(//a[text()='Check Out Now'])[1]");
const secureCheckOutHeader = By.xpath("//*[contains(@class,'secure_checkout_title_container')]");
const shippingAddressAdded = By.xpath("//div[text()='Ship to']/../div[contains(@class,'details')]");
const paymentMethodadded = By.xpath("//div[text()='Pay with']/../div[contains(@class,'details')]");
const defaultShippingMethod = By.xpath("//div[text()='Deliver by']/../div[contains(@class,'details')]");
const placeMyOrder = By.xpath("//button[text()='Place My Order']");
const orderNumber = By.xpath("//span[contains(@class,'_order_number')]");
//Shipping address
const newShippingAddress = By.xpath("//div[text()='Enter a new shipping address']");
const firstName = By.name("firstName");
const lastName = By.name("lastName");
const address1 = By.name("address1");
const zipcode = By.name("zip");
const phoneNum = By.name("phone");
const saveAddress = By.xpath("//button[text()='Save Address']");
//Paymentmethod
const newPaymentMethod = By.xpath("//div[text()='Enter a new payment method']");
const ccNum = By.name("cc-number");
const ccExp = By.name("cc-exp");
const cvv = By.name("cvc");
const savePaymentMethod = By.xpath("//*[text()='Save']");
//Guest user details
const checkoutAsGuest = By.xpath("//*[text()='Continue as Guest']");
const emailAddress = By.xpath("//div[text()='Email Address']");

//New elements
const paypalBtn = By.xpath("//div[@class='button-actions']/div[@class='c-paypal-button']");
const frame = By.xpath("(//iframe[@class='zoid-component-frame zoid-visible'])[1]");
//STS
const shipToStoreLink = By.xpath("//a[contains(@class,'ship_to_store')]");
const searchTextBox = By.xpath("//div[contains(@class,'store_search')]//label/div");
const searchIcon = By.xpath("//div[contains(@class,'store_search')]//button/div");
const storeContainer = By.xpath("(//div[contains(@class,'store_list')]/div/div[2])[1]");
const navigateToBack = By.xpath("(//div[contains(@class,'navbar_container')]//div[contains(@class,'icon')])[1]");
const jcrewStore = By.xpath("//p[text()='J.Crew Store']");

const checkoutNow_int = By.xpath("//a[text()='Check Out Now']");
const cvv_int = By.xpath("//input[@aria-label='security code']");
const placeMyOrder_int = By.xpath("//a[text()='Place My Order']");
const closeBizrate_int = By.xpath("//img[@alt='Close']");
const orderNumber_int = By.className("order-number notranslate");

export const verifyShoppingBagPage = async () => {
  expect(await driver.findElement(shoppingBagHeader)).toBeTruthy();
  let bagItems = await driver.findElement(shoppingBagItems)
  expect(bagItems).toBeTruthy();
  console.log("Bag items are: " + await bagItems.getText());
  expect(await driver.findElement(checkoutNowButton)).toBeTruthy();
};

export const clickOnCheckOutNow = async () => {
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(checkoutNowButton));
  expect(driver.findElement(checkoutNowButton)).toBeTruthy();
  await driver.findElement(checkoutNowButton).click();
  console.log("Clicked on checkout now button")
};

export const verifySecureCheckout_ExpressUser = async () => {
  expect(await driver.findElement(secureCheckOutHeader)).toBeTruthy();
  expect(await driver.findElement(shippingAddressAdded)).toBeTruthy();
  expect(await driver.findElement(paymentMethodadded)).toBeTruthy();
  expect(await driver.findElement(defaultShippingMethod)).toBeTruthy();
  expect(await driver.findElement(placeMyOrder)).toBeTruthy();
};

export const verifySecureCheckout_NonExpressUser = async () => {
  expect(await driver.findElement(secureCheckOutHeader)).toBeTruthy();
  expect(await driver.findElement(newShippingAddress)).toBeTruthy();
  expect(await driver.findElement(newPaymentMethod)).toBeTruthy();
  expect(await driver.findElement(placeMyOrder)).toBeTruthy();
};

export const addShippingAddress = async () => {
  expect(driver.findElement(newShippingAddress)).toBeTruthy();
  await driver.findElement(newShippingAddress).click();
  await waitSeconds(1);
  await driver.findElement(firstName).sendKeys(guestuser.firstNameSA);
  await driver.findElement(lastName).sendKeys(guestuser.lastNameSA);
  await driver.findElement(address1).sendKeys(guestuser.address1);
  await driver.findElement(zipcode).sendKeys(guestuser.zipcode);
  await waitSeconds(1);
  await driver.findElement(phoneNum).sendKeys(guestuser.phoneNumSA);
  await driver.findElement(saveAddress).click();
  expect(await driver.findElement(shippingAddressAdded)).toBeTruthy();
  console.log("Added shipping address");
}

export const addPaymentMethod = async () => {
  expect(driver.findElement(newPaymentMethod)).toBeTruthy();
  await driver.findElement(newPaymentMethod).click();
  await waitSeconds(1);
  await driver.findElement(ccNum).sendKeys(creditcard.number);
  await driver.findElement(ccExp).sendKeys(creditcard.monyear);
  await driver.findElement(cvv).sendKeys(creditcard.pin);
  await driver.findElement(savePaymentMethod).click();
  expect(await driver.findElement(paymentMethodadded)).toBeTruthy();
  console.log("Added Payment method");
}

export const guestUserCheckout = async () => {
  expect(driver.findElement(checkoutAsGuest)).toBeTruthy()
  driver.findElement(checkoutAsGuest).click()
  await driver.sleep(1000)
  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest" + x
  let email = "AutomationTest" + x + "@gmail.com"
  driver.findElement(emailAddress).sendKeys(email)
  driver.findElement(firstName).sendKeys(driver.findElement(guestuser.firstNameSA))
  driver.findElement(lastName).sendKeys(driver.findElement(guestuser.lastNameSA))
  driver.findElement(address1).sendKeys(driver.findElement(guestuser.address1))
  await driver.sleep(2000)
  driver.findElement(zipcode).sendKeys(driver.findElement(guestuser.zipcode))
  driver.findElement(phoneNum).sendKeys(driver.findElement(guestuser.phoneNumSA))
  driver.findElement(ccNum).sendKeys(driver.findElement(creditcard.number))
  driver.findElement(ccExp).sendKeys(driver.findElement(creditcard.monyear))
  cvv.sendKeys(creditcard.pin)
  await driver.sleep(5000)
};

export const selectStoreToShip = async () => {
  expect(await driver.findElement(shipToStoreLink)).toBeTruthy();
  await driver.findElement(shipToStoreLink).click();
  await driver.findElement(searchTextBox).clear();
  await driver.findElement(searchTextBox).sendKeys(zipCode.zipcode);
  await driver.findElement(searchIcon).click();
  expect(await driver.findElement(storeContainer)).toBeTruthy();
  console.log("search results displayed for entered zipcode");
  await driver.findElement(storeContainer).click();
  await driver.findElement(navigateToBack).click();
  expect(await driver.findElement(jcrewStore)).toBeTruthy();
};

export const clickOnPaypalPayment = async () => {
  /* await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(paypalBtn));
   await driver.sleep(1000)
   expect(driver.findElement(paypalBtn)).toBeTruthy()
   await driver.findElement(paypalBtn).click()
   console.log("Clicked on paypal payment option")*/
  await driver.executeScript('window.scrollTo(0, 1500)')
  await driver.sleep(3000)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(paypalBtn));
  await driver.switchTo().frame(await driver.findElement(frame));
  //await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(frame));
  await driver.sleep(3000)
  expect(driver.findElement(frame)).toBeTruthy();
  await driver.sleep(3000)
  await driver.findElement(paypalBtn).click();
};

export const clickOnCheckOutNow_int = async () => {
  await driver.findElement(checkoutNow_int).click();
}

export const placeOrder_int = async () => {
  let currentUrl = driver.getCurrentUrl()
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    try {
      //      console.log("inside securitycode")
      await driver.findElement(cvv_int).sendKeys(creditcard.pin);
    } catch (err) { }
    await driver.findElement(placeMyOrder_int).click();
    await waitSeconds(10);
    await driver.findElement(closeBizrate_int).click();
    let getOrderNumber = driver.findElement(orderNumber_int).getText()
    console.log("order Id  > " + getOrderNumber)
  }
};

export const placeOrder = async () => {
  let currentUrl = driver.getCurrentUrl()
  if (currentUrl.indexOf("https://or.") > -1) {  // Production review checkout
    try {
      //      console.log("inside securitycode")
      await driver.findElement(cvv).sendKeys(creditcard.pin);
    } catch (err) { }
    await driver.findElement(placeMyOrder).click();
    await waitSeconds(10);
    let getOrderNumber = driver.findElement(orderNumber).getText()
    console.log("order Id  > " + getOrderNumber)
  }

};
