import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/MobileMethods';

const { Builder, By, Key } = require('selenium-webdriver')

const contextMenu = By.className("nc-mobile-nav__button hamburger");
const closeIcon = By.xpath("//*[@id='global__email-capture']/section/div[3]/span");
const shipToCountry = By.xpath("//span[text()='Ship to']/../a");
const usAndCanadaLink = By.xpath("//*[text()='UNITED STATES & CANADA']");
//const canadaLink = By.xpath("//span[text()='" + contextchooser +"']");
const buyAGiftCard = By.xpath("//li[text()='Buy a Gift Card']");
const orderStatus = By.xpath("//li[text()='Order Status?']");
const ordNumSearch = By.name("ORDER_SEARCH<>orderNum_search");
const billingZIPCode = By.name("ORDER_SEARCH<>postal");
const findNewStore = By.xpath("//li[text()='Find a J.Crew Store']");
const jcrewGiftCard = By.xpath("//div[text()='The J.Crew Gift Card']");

 
export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
};

export const closeEmailCapturePopUp = async () => {
  try{
    await driver.findElement(closeIcon).click();
  }catch(err){}
};

export const scrollAndClickOnContextChooser = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 1000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(shipToCountry));
  await waitSeconds(1);
  await driver.findElement(shipToCountry).click();
  };

  export const selectAndVerifyCountry = async(contextchooser) => {
  expect(driver.findElement(usAndCanadaLink)).toBeTruthy();
  await driver.findElement(usAndCanadaLink).click();
  await driver.findElement(By.xpath("//span[text()='" + contextchooser +"']")).click();
  let url = await driver.getCurrentUrl();
  if (url.indexOf("ca") > -1) {
    console.log('user is on Canada context')
  }
};

export const selectAndVerifyOrderStatus = async () => {
    await driver.findElement(contextMenu).click();
    await driver.executeScript('window.scrollTo(0, 2000)');
    await waitSeconds(1);
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(orderStatus));
    await waitSeconds(1);
    await driver.findElement(orderStatus).click();
    await waitSeconds(1);
    let url = await driver.getCurrentUrl();
    if(url.includes("order_status")){
      expect(driver.findElement(ordNumSearch)).toBeTruthy();
      expect(driver.findElement(billingZIPCode)).toBeTruthy();
      console.log("User is able to navigated to Order status page");
    }
};

export const selectAndVerifyStoreLocator = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 2000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(findNewStore));
  await waitSeconds(1);
  await driver.findElement(findNewStore).click();
  await waitSeconds(1);
  let url = await driver.getCurrentUrl();
  if(url.includes("stores.")){
    console.log("User is able to navigated to Store Located page");
  }
};

export const goToGiftCardsPage = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 2000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(buyAGiftCard));
  await waitSeconds(1);
  await driver.findElement(buyAGiftCard).click();
  await waitSeconds(1);
  await closeEmailCapturePopUp();
  let url = await driver.getCurrentUrl();
  if(url.includes("gift_card")){
    console.log("User is able to navigated to gift card page");
  }
};

export const goToMyDetailsAndValidate = async () =>{
  await driver.findElement(contextMenu).click();
  await driver.findElement(By.xpath("//div[@class='hamburger-account']/li")).click()
  await waitSeconds(2)
  await driver.findElement(By.xpath("//li[text()='Account']")).click()
  await waitSeconds(2)
  let url = await driver.getCurrentUrl()
  expect(url.indexOf("rewards")).toBeGreaterThan(-1)
  await driver.findElement(By.xpath("//nav[@class='rewards--select']")).click()
  await waitSeconds(2)
  await driver.findElement(By.xpath("(//a[text()='My Details'])[1]")).click()
  await waitSeconds(2)
  url = await driver.getCurrentUrl()
  expect(url.indexOf("details")).toBeGreaterThan(-1)
  let firstNameTxt = await driver.findElement(By.id('my-details-form__first-name'))
  expect(firstNameTxt).toBeTruthy()
  console.log("Logged in user first name is::"+await firstNameTxt.getAttribute("value"))
  let lastNameTxt = await driver.findElement(By.id('my-details-form__last-name'))
  expect(lastNameTxt).toBeTruthy()
  console.log("Logged in user Last name is::"+await lastNameTxt.getAttribute("value"))
  let emailTxt = await driver.findElement(By.xpath("(//input[@type='email'])[1]"))
  expect(emailTxt).toBeTruthy()
  console.log("Logged in email is::"+await emailTxt.getAttribute("value"))
}


