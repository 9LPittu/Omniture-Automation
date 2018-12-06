import { driver, defaultTimeout } from '../helpers';
const { Builder, By, Key, until } = require('selenium-webdriver')

export default class element {
  static defaultTimeOut() { return 30000; }

  //isElement is present OR not

  static async isNotPresent(driver, locator) {
    await driver.wait(() => {
      return driver.findElements(locator).then((elements) => {
        if (elements.length <= 0) {
          return true;
        }
        return false;
      });
    }, element.defaultTimeOut(), 'The element was still present when it should have disappeared.');
  }
}

export const waitSeconds = async (sec) => {
  var start = new Date().getTime()
  while (new Date().getTime() < (start + (sec * 1000))) {
  }
}

export const waitForPageToBeStable = async () => {
  await driver.wait(function () {
    return documentState();
  }, 60000, "document ready in 60 secs")
}

export const documentState = async () => {
  var state = await driver.executeScript("return document.readyState")
  if (state == "complete") {
    return true;
  }
  return false;
}