import { driver } from '../helpers';
import { waitSeconds } from '../util/commonutils';
import { globals } from '../jestJcrewQaConfig';
import { goToShoppingBag } from '../pageObjects/shoppingbagobj';
import { login, loginInAfterCheckoutPage } from '../pageObjects/loginpageobj';
import { creditcard } from '../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver');
export const zipCode = () => driver.findElement(By.css("input#zipcode"));
export const multiShipAddr = () => driver.findElement(By.xpath("//input[@type='checkbox' and @name='multiShippingAddresses']"));
export const continueButton = () => driver.findElement(By.css("a#order-summary__button-continue"));
export const inStorePick = () => driver.findElement(By.xpath("//div[@class='form-radio-set hide']/label/input[@name='numberOfShippingAddress']"));
export const overnight = () => driver.findElement(By.css("#method0"));
export const expedited = () => driver.findElement(By.css("#method1"));
export const standard = () => driver.findElement(By.css("#method2"));
export const economy = () => driver.findElement(By.css("#method3"));
export const giftsYesRadio = () => driver.findElement(By.css("#includesGifts"));
export const just_a_Gift_Receipt = () => driver.findElement(By.css("#giftReceiptMessage0"));
export const gift_Wrapping_Service = () => driver.findElement(By.css("#giftWrapService0"));

//Edit and save address changes 
//const pageActCreditCard = By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[6]/a");
const pageActCreditCard = By.xpath("(//a[@href='/account/address_book.jsp'][contains(.,'Address Book')])[2]");
const editBtn = By.xpath("(//img[contains(@src,'btn_edit')])[1]");
const address_firstname = By.name("ADDRESS<>firstName");
const address_lastname = By.name("ADDRESS<>lastName");
const editSaveChangesTxt = By.xpath("//*[text()='EDIT ADDRESS & SAVE CHANGES']");
const useThisAddress = By.xpath("//a[text()='Use This Address']");
const editTxt = By.xpath("(//*[text()='EDIT'])[1]");
const newAddressBtn = By.xpath("//img[contains(@src,'btn_add_new_address')]");
const addNewAddress = By.xpath("//a[text()='ADD A NEW ADDRESS']");
const address1 = By.name("ADDRESS<>address1");
const addressCity = By.name("ADDRESS<>city");
const addressState = By.name("ADDRESS<>state_cd");
const addressPostal = By.name("ADDRESS<>postal");
const addressPhone = By.name("ADDRESS<>phone");
const clearTable = By.xpath("//*[@id='clearTable']/tbody/tr[35]/td/a");
const deleteBtn = By.xpath("(//img[contains(@src,'btn_delete')])[2]");
const deleteTxt2 = By.xpath("(//a[text()='DELETE'])[2]");

//Credit Card Elements 
//const pageAccount = By.xpath("//*[@id='page__accounta']/div/div[1]/nav[2]/ul/li[5]/a");
const pageAccount = By.xpath("(//a[@href='/account/payment_info.jsp'][contains(.,'Payment Methods')])[2]");
const creditCardList = By.xpath("//*[@id='creditCardList']/table[1]/tbody/tr[3]/td/a[1]");
const CCNumber = By.xpath("//*[@id='ccNumber']");
const exmonth = By.xpath("//*[@id='eXmonth']");
const exyear = By.xpath("//*[@id='eXyear']");
const cardHolderName = By.xpath("//*[@id='cardholderName']");
const addCreditCard = By.xpath("//*[@id='AddCreditCard']/table/tbody[4]/tr/td[4]/input[2]");
const addNewCard = By.xpath("//input[@name='addANewCard']");
const selectAddress = By.xpath("//*[@id='selectAddressList']");
const addCardAndSave = By.xpath("//input[@value='ADD NEW CARD & SAVE CHANGES']");
const deleteCard = By.xpath("(//img[@name='delete'])[2]");
const saveCard = By.xpath("//input[@value='Save']");
const deleteTxt = By.xpath("(//*[text()='DELETE'])[2]");

export const addEditAdress = async () => {
  let editLink = await driver.findElement(By.xpath("//*[@id='address-1']/parent::label/following-sibling::span/a[1]"));
  editLink.click();
  await driver.findElement(By.xpath("//input[@id='address3']")).sendKeys("Testing");
  await driver.findElement(By.xpath("//a[@id='submit-new-shipping-address']")).click();
  await driver.findElement(By.xpath("//a[@*='item-link item-link-submit']")).click();
  driver.findElements(By.xpath("//*[@id='address-book']/ul/li")).then(function (links) {
    console.log('Found', links.length, 'addresses size.')
    console.log("//*[@id='address-book']/ul/li[" + links.length + "]/*/span")
    let windowText = driver.findElement(By.xpath("//*[@id='address-book']/ul/li[" + links.length + "]/*/span")).getText();
    console.log('Text compared:: ' + windowText)
  });
};

export const addEditRemoveAddress = async () => {
  //#address-new
  await waitSeconds(4);
  await driver.findElement(By.css("#nav-shipping")).click();
  await driver.findElement(By.css("#address-new")).click();
  await driver.findElement(By.css("#firstNameAM")).sendKeys("Auto Tester1 FN");
  await driver.findElement(By.css("#lastNameAM")).sendKeys("Auto Tester1 LN");
  await driver.findElement(By.css("#address3")).sendKeys("Nothing");
  await driver.findElement(By.css("#address1")).sendKeys("44 building-lvl 45");
  //address2
  await driver.findElement(By.css("#address2")).sendKeys("address test");
  await driver.findElement(By.css("#zipcode")).sendKeys("10012");
  await waitSeconds(3);
  await driver.findElement(By.css("#phoneNumAM")).sendKeys("9658742361");
  //.button-submit
  driver.sleep(3000)
  await driver.findElement(By.css("#submit-new-shipping-address")).click();
  await waitSeconds(5);
  await driver.findElement(By.xpath("//a[@class='button-submit']")).click();
  driver.sleep(3000)
  //#nav-shipping
  await driver.findElement(By.css("#nav-shipping")).click();
  driver.sleep(3000)
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

export const removeAdress = async () => {
  let removeLink = await driver.findElement(By.xpath("//*[@id='address-1']/parent::label/following-sibling::span/a[2]"));
  removeLink.click();
};

export const clickOnInStorPickup = async () => {
  let pickup = await driver.findElement(By.xpath("//div[@class='form-radio-set hide']/label/input[@name='numberOfShippingAddress']"));
  pickup.click();
}

export const inputZipCode = async () => {
  let zipcode = await driver.findElement(By.css("input#zipcode"));
  zipcode.sendKeys("20009");
}

export const clickMultiAddress = async () => {
  let multiShippingAddresses = await driver.findElement(By.xpath("//input[@type='checkbox' and @name='multiShippingAddresses']"));
  multiShippingAddresses.click();
};

export const clickOnContinue = async () => {
  await driver.findElement(continueButton).click();
}

//#miniCartTabQty
export const verifyShipToMultiAddress = async () => {
  let quantity = await driver.findElement(By.css("#miniCartTabQty")).getText();
}

export const validateAddEditCreditCard = async () => {
  await waitSeconds(10)
  await driver.findElement(pageAccount).click();
  await waitSeconds(2);
  if (await driver.findElement(creditCardList).isDisplayed()) {
    await driver.findElement(creditCardList).click()
    await waitSeconds(3);
    await driver.findElement(CCNumber).clear()
    await driver.findElement(CCNumber).sendKeys("4111111111111111")
    await driver.findElement(exmonth).sendKeys(creditcard.expirationMonth)
    await driver.findElement(exyear).sendKeys(creditcard.expirationYear)
    await driver.findElement(cardHolderName).clear()
    await driver.findElement(cardHolderName).sendKeys("AutomationUser")
    await waitSeconds(2)
    await driver.findElement(addCreditCard).click()
    let addConfirm1 = await driver.findElement(creditCardList).isDisplayed()
    expect(addConfirm1).toBeTruthy()
    console.log("edited the creditcard from payment methods");
  }
  try {
    // click on Add new card
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(addNewCard));
    await waitSeconds(1);
    await driver.findElement(addNewCard).then(addNewCard => {
      addNewCard.click()
      driver.sleep(2000)
    })
  } catch (err) { }
  await driver.findElement(CCNumber).sendKeys("4111111111111111")
  await driver.findElement(exmonth).sendKeys(creditcard.expirationMonth)
  await driver.findElement(exyear).sendKeys(creditcard.expirationYear)
  await driver.findElement(cardHolderName).sendKeys("visa")
  await driver.findElement(selectAddress).sendKeys("45,vstreet")
  await waitSeconds(3)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(addCardAndSave).click()
    await waitSeconds(2)
    console.log("user added new credit card succesfully")
    let deletebutton = await driver.findElement(deleteCard)
    deletebutton.click()
    await waitSeconds(2)
  } else {
    await driver.findElement(saveCard).click()
    await waitSeconds(2)
    console.log("user added new credit card succesfully")
    let deletebutton = await driver.findElement(deleteTxt)
    deletebutton.click()
    await waitSeconds(2)
  }

}

export const validateEditShippingAddress = async () => {
  await waitSeconds(10)
  //click on Address book
  await driver.findElement(pageActCreditCard).click();
  let url = await driver.getCurrentUrl();
  if (url.includes("factory")) {
    await waitSeconds(3);

    if (await driver.findElement(editBtn).isDisplayed()) {
      await driver.findElement(editBtn).click()
      await waitSeconds(1)
      await driver.findElement(address_firstname).clear()
      await driver.findElement(address_firstname).sendKeys("testing")
      await waitSeconds(1)
      var parent = driver.getWindowHandle();
      await driver.findElement(editSaveChangesTxt).click()
      await waitSeconds(5)
      await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
        driver.switchTo().window(allhandles[allhandles.length - 1]);
      });
      await driver.findElement(useThisAddress).click()
      await waitSeconds(3)
      await driver.switchTo().window(parent)
      await driver.findElement(editSaveChangesTxt).click()
      await waitSeconds(1)
    }
  } else {
    if (await driver.findElement(editTxt).isDisplayed()) {
      await driver.findElement(editTxt).click()
      await waitSeconds(1)
      await driver.findElement(address_firstname).clear()
      await driver.findElement(address_firstname).sendKeys("testing")
      await waitSeconds(1)
      var parent = driver.getWindowHandle();
      await driver.findElement(editSaveChangesTxt).click()
      await waitSeconds(5)
      await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
        driver.switchTo().window(allhandles[allhandles.length - 1]);
      });
      await driver.findElement(useThisAddress).click()
      await waitSeconds(3)
      await driver.switchTo().window(parent)
      await driver.findElement(editSaveChangesTxt).click()
      await waitSeconds(1)
    }
  }

  if (url.includes("factory")) {
    driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(newAddressBtn));
    driver.sleep(1000)
    await driver.findElement(newAddressBtn).then(addAddress => {
      addAddress.click()
      driver.sleep(3000)
    })
  } else {
    driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(addNewAddress));
    driver.sleep(1000)
    await driver.findElement(addNewAddress).then(addAddress => {
      addAddress.click()
      driver.sleep(3000)
    })
  }
  // First name
  await driver.findElement(address_firstname).sendKeys("Auto Tester1 FN");
  // Last name
  await driver.findElement(address_lastname).sendKeys("Auto Tester1 LN");
  // Address
  await driver.findElement(address1).sendKeys("1111 East 60th Street");
  // City
  await driver.findElement(addressCity).sendKeys("Chicago");
  // State
  await driver.findElement(addressState).sendKeys("Illinois");
  // Zip code
  await driver.findElement(addressPostal).sendKeys("60637");
  // Phonenumber
  await driver.findElement(addressPhone).sendKeys("7737029494");
  var parent = driver.getWindowHandle();
  // Save button
  await driver.findElement(clearTable).click()
  driver.sleep(5000)
  await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
    driver.switchTo().window(allhandles[allhandles.length - 1]);
  });
  // Use this address
  await driver.findElement(useThisAddress).click()
  driver.sleep(2000)
  await driver.switchTo().window(parent)
  await driver.findElement(clearTable).click()
  driver.sleep(5000)
  if (url.includes("factory")) {
    await driver.findElement(deleteBtn).click()
    driver.sleep(2000)
  } else {
    await driver.findElement(deleteTxt2).click()
    driver.sleep(2000)
    driver.actions().sendKeys(Key.ENTER).perform();
  }
}
