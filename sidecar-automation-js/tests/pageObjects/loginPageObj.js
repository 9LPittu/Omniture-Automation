import { driver, defaultTimeout, defaultTimeout_short } from '../helpers';
import { logindetails } from '../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')
//Login Elements
const signIn_factory = By.xpath(".//span[text()='sign in']");
const signIn_jcrew = By.xpath("//a[text()='Sign In']");
const sidecar_user = By.id("sidecarUser");
const sidecar_password = By.id("sidecarPassword");
const signIn_submitBtn = By.xpath("//button[@class='btn--primary btn--signin js-button-submit']");

//Login After checkout Elements
const inputEmail = By.id("loginUser");
const inputPassword = By.id("loginPassword");
const signInHere = By.xpath("//a[@class='button-general button-submit']");

//Register new user elements
const firstName = By.xpath("//*[@id='sidecarRegisterFirstName']");
const lastName = By.xpath("//*[@id='sidecarRegisterLastName']");
const emailId = By.xpath("//*[@id='sidecarRegisterEmail']");
const password_register = By.xpath("//*[@id='sidecarRegisterPassword']");
const registerBtn = By.xpath("//button[@id='loyaltysignup']");
const navPanel_factory = By.id("c-header__userpanelrecognized");
const navPanel_jcrew = By.xpath("//a[@class='nc-nav__account_button']");

const signOut_factory = By.xpath("//a[@class='js-signout__link']");
const signOut_jcrew = By.xpath("//li[5]/a[text()='Sign Out']");

//Forgot Password
const forgotPasswordLink = By.xpath("//a[text()='I forgot my password!']");
const forgot_userName = By.xpath("//input[@name='loginUser']");
const forgot_sendNewPassword = By.xpath("//a[text()='Send Me a New Password']");
const forgot_forgotPassword = By.xpath("//h2[text()='Forgot Password?']");
const forgot_newPasswordTxt = By.xpath("//section[@id='registered']/p");

export const loginFromHomePage = async (username, password) => {
  let url = await driver.getCurrentUrl()
  if (url.indexOf("https://factory.jcrew.com") > -1) {
    await driver.findElement(signIn_factory).click()
  } else {
    await driver.findElement(signIn_jcrew).click()
  }
  await driver.findElement(sidecar_user).sendKeys(username)
  await driver.findElement(sidecar_password).sendKeys(password)
  await driver.findElement(signIn_submitBtn).click()
  console.log("Login success")
};

export const loginInAfterCheckoutPage = async (username, password) => {
  await driver.findElement(inputEmail).sendKeys(username)//"testuser1@example.org")
  await driver.findElement(inputPassword).sendKeys(password)//"test1234")
  await driver.findElement(signInHere).click()
  //expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
}

export const clearBagItems = async () => {
  let url = await driver.getCurrentUrl();
  if (url.indexOf("www.jcrew.com") > -1) {
    await driver.get("https://www.jcrew.com/CleanPersistentCart.jsp")
  } else if (url.indexOf("or.jcrew.com") > -1) {
    await driver.get("https://or.jcrew.com/CleanPersistentCart.jsp")
  } else if (url.indexOf("or.factory.jcrew.com") > -1) {
    await driver.get("https://or.factory.jcrew.com/CleanPersistentCart.jsp")
  } else if (url.indexOf("https://factory.jcrew.com") > -1) {
    await driver.get("https://factory.jcrew.com/CleanPersistentCart.jsp")
  }
}

export const createNewAccount = async () => {
  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest" + x
  let email = "AutomationTest" + x + "@gmail.com"
  //await driver.navigate().to(globals.__baseUrl__ + "/r/login")
  await driver.findElement(firstName).sendKeys(userName)
  await driver.findElement(lastName).sendKeys("tester")
  await driver.findElement(emailId).sendKeys(email)
  await driver.findElement(password_register).sendKeys("nft123")
  await driver.findElement(registerBtn).click()
  await driver.sleep(5000)
  let url = await driver.getCurrentUrl()
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(navPanel_factory)
    expect(loggedInUser).toBeTruthy()
  } else {
    const loggedInUser = await driver.findElement(navPanel_jcrew)
    expect(loggedInUser).toBeTruthy()
  }
  console.log("User is able to create new account")
  console.log("User created >> " + email)
}

export const loggedInUserValidation = async (url) => {
  let result = false;
  await driver.sleep(5000)
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(navPanel_factory)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    const signOut = await driver.findElement(signOut_factory)
    expect(signOut).toBeTruthy()
    result = true;
    signOut.click()
  } else {
    const loggedInUser = await driver.wait(until.elementLocated(navPanel_jcrew), defaultTimeout_short);
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform()
    const signOut = await driver.findElement(signOut_jcrew)
    expect(signOut).toBeTruthy()
    result = true;
    signOut.click();
  }
  await driver.sleep(5000)
  return result;
}

export const invalidUserValidation = async (url) => {
  let result = false;
  if (url.includes("factory")) {
    await driver.findElement(signIn_factory).click()
  } else {
    await driver.findElement(signIn_jcrew).click()
  }
  await driver.findElement(sidecar_user).sendKeys("dummyemail@gmail.com")
  await driver.findElement(sidecar_password).sendKeys("123456ab")
  await driver.findElement(signIn_submitBtn).click()
  await driver.sleep(2000)
  const errorMsg = await driver.findElement(By.className('js-invalid-msg is-important'))
  expect(errorMsg).toBeTruthy()
  result = true;
  console.log('login with Invalid details and display error message');
  return result;
}

export const forgotPassword = async (url) => {
  let result = false;
  if (url.includes("factory")) {
    await driver.findElement(signIn_factory).click()
  } else {
    await driver.findElement(signIn_jcrew).click()
  }
  await driver.sleep(3000)
  let forgotPassword_Link = await driver.findElement(forgotPasswordLink)
  expect(forgotPassword_Link).toBeTruthy()
  console.log('forgot password link is displayed');
  forgotPassword_Link.click()
  await driver.sleep(1000)
  await driver.findElement(forgot_userName).sendKeys(logindetails.username5)
  await driver.sleep(1000)
  const sendNewPassword = await driver.findElement(forgot_sendNewPassword)
  sendNewPassword.click()
  await driver.sleep(1000)
  const forgotPassword = await driver.findElement(forgot_forgotPassword)
  expect(forgotPassword).toBeTruthy()
  await driver.sleep(1000)
  const newPasswordText = await driver.findElement(forgot_newPasswordTxt).getText()
  expect(newPasswordText).toBeTruthy()
  result = true;
  newPasswordText.includes(logindetails.username5)
  return result;
}

export const newAccountCreateValidation = async (url) => {
  let result = false;
  console.log("in test case account function")
  if (url.includes("factory")) {
    const loggedInUser = await driver.findElement(navPanel_factory)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform();
    await driver.sleep(2000)
    const signOut = await driver.findElement(signOut_factory)
    expect(signOut).toBeTruthy()
    signOut.click()
    await driver.sleep(5000)
    expect(await driver.findElement(signIn_factory)).toBeTruthy()
    result = true;
    console.log("sign out from the application successfully")
  } else {
    console.log("in test case create validation")
    const loggedInUser = await driver.findElement(navPanel_jcrew)
    expect(loggedInUser).toBeTruthy()
    await driver.actions().mouseMove(loggedInUser).perform()
    await driver.sleep(3000)
    const signOut = await driver.findElement(signOut_jcrew)
    expect(signOut).toBeTruthy()
    signOut.click()
    await driver.sleep(5000)
    expect(await driver.findElement(signIn_jcrew)).toBeTruthy()
    result = true;
    console.log("sign out from the application successfully")
  }
  return result;
}

export const validateAddEditCreditCard = async () => {
  await driver.sleep(10000);
  await driver.findElement(By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[5]/a")).click();
  await driver.sleep(2000);
  if (await driver.findElement(By.xpath("//*[@id='creditCardList']/table[1]/tbody/tr[3]/td/a[1]")).isDisplayed()) {
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
    console.log("edited the creditcard from payment methods");
  }
  try {
    // click on Add new card
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//input[@name='addANewCard']")));
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//input[@name='addANewCard']")).then(addNewCard => {
      addNewCard.click()
      driver.sleep(2000)
    })
  } catch (err) { }
  await driver.findElement(By.xpath("//*[@id='ccNumber']")).sendKeys("4111111111111111")
  await driver.findElement(By.xpath("//*[@id='eXmonth']")).sendKeys(creditcard.expirationMonth)
  await driver.findElement(By.xpath("//*[@id='eXyear']")).sendKeys(creditcard.expirationYear)
  await driver.findElement(By.xpath("//*[@id='cardholderName']")).sendKeys("visa")
  await driver.findElement(By.xpath("//*[@id='selectAddressList']")).sendKeys("45,vstreet")
  await driver.sleep(3000)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(By.xpath("//input[@value='ADD NEW CARD & SAVE CHANGES']")).click()
    await driver.sleep(2000)
    console.log("user added new credit card succesfully")
    let deletebutton = await driver.findElement(By.xpath("(//img[@name='delete'])[2]"))
    deletebutton.click()
    await driver.sleep(2000)
  } else {
    await driver.findElement(By.xpath("//input[@value='Save']")).click()
    await driver.sleep(2000)
    console.log("user added new credit card succesfully")
    let deletebutton = await driver.findElement(By.xpath("(//*[text()='DELETE'])[2]"))
    deletebutton.click()
    await driver.sleep(2000)
  }

}

export const validateEditShippingAddress = async () => {
  await driver.sleep(10000);
  //click on Address book
  await driver.findElement(By.xpath("//*[@id='page__account']/div/div[1]/nav[2]/ul/li[6]/a")).click();
  let url = await driver.getCurrentUrl();
  if (url.includes("factory")) {
    await driver.sleep(3000);

    if (await driver.findElement(By.xpath("(//img[contains(@src,'btn_edit')])[1]")).isDisplayed()) {
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
  } else {
    if (await driver.findElement(By.xpath("(//*[text()='EDIT'])[1]")).isDisplayed()) {
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

  if (url.includes("factory")) {
    driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//img[contains(@src,'btn_add_new_address')]")));
    driver.sleep(1000)
    await driver.findElement(By.xpath("//img[contains(@src,'btn_add_new_address')]]")).then(addAddress => {
      addAddress.click()
      driver.sleep(3000)
    })
  } else {
    driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[text()='ADD A NEW ADDRESS']")));
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
  if (url.includes("factory")) {
    await driver.findElement(By.xpath("(//img[contains(@src,'btn_delete')])[2]")).click()
    driver.sleep(2000)
  } else {
    await driver.findElement(By.xpath("(//a[text()='DELETE'])[2]")).click()
    driver.sleep(2000)
    driver.actions().sendKeys(Key.ENTER).perform();
    /*Robot robot1 = new Robot();
    robot1.keyPress(KeyEvent.VK_ENTER);
    robot1.keyRelease(KeyEvent.VK_ENTER);
    driver.sleep(2000)*/
  }
}