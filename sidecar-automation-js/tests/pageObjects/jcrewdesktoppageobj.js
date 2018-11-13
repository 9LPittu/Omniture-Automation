import { until } from 'selenium-webdriver';
import { driver, defaultTimeout_short } from '../helpers';
import { globals } from '../jestJcrewQaConfig';

const { Builder, By, Key } = require('selenium-webdriver')
const closeIcon = { xpath: "(//span[@class='icon-close'])[1]" };
const privacyPolicyClose = { xpath: "//div[@class='mt-close-lb-slide privacyPolicyClose']" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
  //await closeIconInPAP();
};

export const loadLoginUrl = async () => {
  await driver.get(globals.__baseUrl__ + "/r/login")
};

export const closeIconInPAP = async () => {
  try {
    await driver.wait(until.elementLocated(closeIcon), defaultTimeout_short).click();
  } catch (err) {
    console.log("no close icon" + err);
  }
  try {
    await driver.wait(until.elementLocated(privacyPolicyClose), defaultTimeout_short).click();
    console.log("privacy policy icon closed")
  } catch (err) {
    console.log("in catch email popup close");
  }
};
