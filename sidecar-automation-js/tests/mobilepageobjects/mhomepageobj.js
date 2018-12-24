import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/commonutils';

const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')




const contextLink = By.xpath("//a[@class='footer__country-context__link']");
const usAndCanadaLink = By.xpath("//*[text()='UNITED STATES & CANADA']");
const canadaLink = By.xpath("//span[text()='Canada']");
const letUsHelpLink = By.xpath("//h6[text()='Let Us Help You']");
const orderStatus = By.xpath("//div[text()='Order Status']");
const ordNumSearch = By.name("ORDER_SEARCH<>orderNum_search");
const billingZIPCode = By.name("ORDER_SEARCH<>postal");
const ourStores = By.xpath("//h6[text()='Our Stores']");
const storeLocator = By.xpath("//div[text()='Store Locator']");


 
export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
};

export const scrollAndClickOnContextChooser = async () => {
  await driver.executeScript('window.scrollTo(0, 15000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(contextLink));
  await waitSeconds(1);
  await driver.findElement(contextLink).click();
  };
 
export const selectCanadaCountry = async () => {
  expect(driver.findElement(usAndCanadaLink)).toBeTruthy();
  await driver.findElement(usAndCanadaLink).click();
  await driver.findElement(canadaLink).click();
};

export const verifyforSelectedCountry = async () => {
  let url = await driver.getCurrentUrl();
  if (url.indexOf("ca") > -1) {
    console.log('user is on Canada context')
  }
};

export const selectAndVerifyOrderStatus = async () => {
    await driver.executeScript('window.scrollTo(0, 10000)');
    await waitSeconds(1);
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(letUsHelpLink));
    await waitSeconds(1);
    await driver.findElement(letUsHelpLink).click();
    await waitSeconds(1);
    await driver.findElement(orderStatus).click();
    let url = await driver.getCurrentUrl();
    if(url.includes("order_status")){
      expect(driver.findElement(ordNumSearch)).toBeTruthy();
      expect(driver.findElement(billingZIPCode)).toBeTruthy();
      console.log("User is able to navigated to Order status page");
    }
};

export const selectAndVerifyStoreLocator = async () => {
  await driver.executeScript('window.scrollTo(0, 10000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(ourStores));
  await waitSeconds(1);
  await driver.findElement(ourStores).click();
  await waitSeconds(1);
  await driver.findElement(storeLocator).click();
  let url = await driver.getCurrentUrl();
  if(url.includes("stores.")){
    console.log("User is able to navigated to Store Located page");
  }
};

