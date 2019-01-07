import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';
import { waitSeconds } from '../util/MobileMethods';
import { logindetails } from '../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

const nav_userName = By.id("sidecarUser");
const nav_password = By.id("sidecarPassword");
const nav_signInButton = By.className("btn--primary btn--signin js-button-submit");
const invalidUser_errorMsg = By.className('js-invalid-msg is-important');

const sidecar_userName = By.id("loginUser");
const sidecar_password = By.id("loginPassword");
const sidecar_signInButton = By.xpath("//a[@class='button-general button-submit']");

const createNewAccount_firstName = By.xpath("//*[@id='sidecarRegisterFirstName']");
const createNewAccount_lastName = By.xpath("//*[@id='sidecarRegisterLastName']");
const createNewAccount_email = By.xpath("//*[@id='sidecarRegisterEmail']");
const createNewAccount_password = By.xpath("//*[@id='sidecarRegisterPassword']");
const createNewAccount_signMeUp = By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button");

const forgotPasswordLink = By.xpath("//a[text()='I forgot my password!']");
const sendMeNewPasswordButton = By.xpath("//a[text()='Send Me a New Password']");
const confirmationText = By.xpath("//p[@class='page-msg']");

//Factory
const contextMenu_Factory = By.xpath("//span[text()='menu']");
const loggedInUser_Factory = By.id("c-header__userpanelrecognized");
const signOut_Factory = By.xpath("//dd[contains(@class,'c-my-account-menu-item')]/a[text()='Sign Out']");
const signInIcon_Factory = By.xpath("//span[text()='sign in']");

//Jcrew
//const contextMenu_Jcrew = By.className("nc-mobile-nav__button hamburger");
const contextMenu_Jcrew = By.xpath("//button[@class='nc-mobile-nav__button hamburger']")
const loggedInUserName_Jcrew = By.xpath("//button[@class='hamburger-item']/h3");
const signOut_Jcrew = By.xpath("//li[text()='Sign Out']");
const signInLink_Jcrew = By.xpath("//h3[text()='Sign in']");



export const loginFromHomePage = async (username, password) => {
  await driver.navigate().to(globals.__baseUrl__ + "/r/login");
  await driver.findElement(nav_userName).sendKeys(username);
  await driver.findElement(nav_password).sendKeys(password);
  await driver.findElement(nav_signInButton).click();
  await waitSeconds(3)
  console.log("Login success")
};

export const loginInAfterCheckoutPage = async (username, password) => {
  await driver.findElement(sidecar_userName).sendKeys(username);
  await driver.findElement(sidecar_password).sendKeys(password);
  await driver.findElement(sidecar_signInButton).click();
  await waitSeconds(3)
  expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
};

export const clearBagItems = async () => {
  await waitSeconds(2);
  await driver.get(globals.__baseUrl__ + "/CleanPersistentCart.jsp")
};

export const createNewAccount = async () => {
  await driver.navigate().to(globals.__baseUrl__ + "/r/login");
  var x = Math.floor((Math.random() * 1000000) + 1);
  let userName = "AutomationTest" + x
  let email = "AutomationTest" + x + "@gmail.com"
  await driver.findElement(createNewAccount_firstName).sendKeys(userName);
  await driver.findElement(createNewAccount_lastName).sendKeys("tester");
  await driver.findElement(createNewAccount_email).sendKeys(email);
  await driver.findElement(createNewAccount_password).sendKeys("Password@123")
  await waitSeconds(2);
  await driver.findElement(createNewAccount_signMeUp).click()
  await waitSeconds(15);
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    expect(driver.findElement(loggedInUser_Factory)).toBeTruthy();
  } else {
    await driver.findElement(contextMenu_Jcrew).click();
    expect(driver.findElement(loggedInUserName_Jcrew)).toBeTruthy();
  }
  console.log("User created >> " + userName)
};

export const signOutFromApplication = async () => {
  let currentUrl = await driver.getCurrentUrl();
  console.log("current url::"+currentUrl)
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    expect(driver.findElement(loggedInUser_Factory)).toBeTruthy();
    await driver.findElement(contextMenu_Factory).click();
    await driver.executeScript('window.scrollTo(0, 500)');
    await waitSeconds(1);
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(signOut_Factory));
    expect(driver.findElement(signOut_Factory)).toBeTruthy();
    await driver.findElement(signOut_Factory).click();
    await waitSeconds(1);
    expect(driver.findElement(signInIcon_Factory_Factory)).toBeTruthy();
    console.log("user sign out from application successfully")
  } else {
    await waitSeconds(2)
    let contextMenu = await driver.findElement(contextMenu_Jcrew)
    await contextMenu.click();
    await waitSeconds(2)
    expect(await driver.findElement(loggedInUserName_Jcrew)).toBeTruthy();
    await driver.findElement(loggedInUserName_Jcrew).click();
    await waitSeconds(2)
    expect(driver.findElement(signOut_Jcrew)).toBeTruthy();
    await driver.findElement(signOut_Jcrew).click();
    await waitSeconds(2);
    await driver.findElement(contextMenu_Jcrew).click()
    await waitSeconds(1);
    expect(driver.findElement(signInLink_Jcrew)).toBeTruthy();
    console.log("user sign out from application successfully")
  }
};

export const clickOnForgotPasswordLink = async () => {
  await driver.navigate().to(globals.__baseUrl__ + "/r/login");
  await driver.findElement(forgotPasswordLink).click();
};

export const clickSendMeNewPasswordButton = async () => {
  await driver.findElement(sidecar_userName).sendKeys(logindetails.forgotPwdUser);
  await driver.findElement(sendMeNewPasswordButton).click();
  await waitSeconds(1);
  expect(driver.findElement(confirmationText)).toBeTruthy();
  let getConfText = await driver.findElement(confirmationText).getText();
  if (getConfText.includes(logindetails.forgotPwdUser)) {
    console.log("new password has been sent to user entered email id")
  }
};

export const loginUsingInvalidCredentials = async () => {
  await driver.navigate().to(globals.__baseUrl__ + "/r/login");
  await driver.findElement(nav_userName).sendKeys("dummyuser@gmail.com");
  await driver.findElement(nav_password).sendKeys("12345678");
  await driver.findElement(nav_signInButton).click();
  await waitSeconds(2);
  expect(driver.findElement(invalidUser_errorMsg)).toBeTruthy();
};