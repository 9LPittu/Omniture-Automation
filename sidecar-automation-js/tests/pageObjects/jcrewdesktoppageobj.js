import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpers';

const { Builder, By, Key } = require('selenium-webdriver')
const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };
const privacyPolicy = { xpath: "//div[@class='mt-close-lb-slide privacyPolicyClose']" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };

export const closeIcon = () => driver.findElement(jcrewCloseicon);
export const privacyPolicyClose = () => driver.findElement(privacyPolicy);
export const signIn = () => driver.findElement({ xpath: "//*[@id='c-header__userpanel']/a/span[2]" });
//export const privacyPolicyClose = () => driver.findElement({ xpath: "//div[@class='mt-close-lb-slide privacyPolicyClose']" });

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
  await driver.sleep(1000)
  //await driver.manage().window().maximize();
  //await driver.sleep(1000)
  try {
  await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
  } catch (err) {

  }
  await driver.sleep(3000)
  try {
//  console.log("email capture closed")
    await driver.wait(until.elementLocated(privacyPolicyClose), defaultTimeout).click();
    driver.sleep(1000)
//    console.log("privacy policy icon closed")
 } catch (err) {}
  };

  export const closeIconInPAP = async () => {
 try {
  await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
  } catch (err) {

  }
  await driver.sleep(3000)
  try {
//  console.log("email capture closed")
    await driver.wait(until.elementLocated(privacyPolicyClose), defaultTimeout).click();
    driver.sleep(1000)
//    console.log("privacy policy icon closed")
 } catch (err) {}
};

// home page Elements:


//singin page Elements
export const userPannel = () => driver.findElement({ xpath: "//*[@id='c-header__userpanelrecognized']" });
export const firstname = () => driver.findElement({ xpath: "//*[@id='sidecarRegisterFirstName']" });
export const lastname = () => driver.findElement({ xpath: "//*[@id='sidecarRegisterLastName']" });
export const email = () => driver.findElement({ xpath: "//*[@id='sidecarRegisterEmail']" });
export const password = () => driver.findElement({ xpath: "//*[@id='sidecarRegisterPassword']" });
export const submit = () => driver.findElement({ xpath: "//*[@id='page__signin']/article/section[2]/div/form/button" });

export const categorymen = () => driver.findElement({ xpath: "//li[@data-department='men']" });
export const caualshirt = () => driver.findElement({ xpath: "//span[text()='shirts']" });
export const quickshopbutton = () => driver.findElement({ xpath: "//button[@class='c-product-tile__quickshop js-product-tile-quickshop']/div" });
export const amount200 = () => driver.findElement({ xpath: "//*[@id='amount200']" });
