import { until } from 'selenium-webdriver';
import { driver, defaultTimeout_short } from '../helpers';
import { globals } from '../jestJcrewQaConfig';

const { Builder, By, Key } = require('selenium-webdriver')
const closeIcon = { xpath: "(//span[@class='icon-close'])[1]" };
const privacyPolicyClose = { xpath: "//div[@class='mt-close-lb-slide privacyPolicyClose']" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };
const footer_country_link = By.xpath("//a[@class='footer__country-context__link']");
const footer_page_international = By.xpath("//*[@id='page__international']/article/section[1]/div/div[2]/div/div[1]/ul/li[2]/button");

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
  await closeIconInPAP();
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

export const differentRegionsValidation = async () => {
  let result = false;
  await driver.executeScript('window.scrollTo(0, 10000)')
  await driver.sleep(1000)
  let footerElement = await driver.findElement(footer_country_link);
  await driver.executeScript("arguments[0].scrollIntoView(true);", footerElement );
  await driver.sleep(1000)
  await footerElement.click()
  await closeIconInPAP()
  await driver.findElement(footer_page_international).click()
  let url = await driver.getCurrentUrl();
  if (url.indexOf("ca") > -1) {
    console.log('user is on Canada context')
    result = true;
  }
  return result;
};
