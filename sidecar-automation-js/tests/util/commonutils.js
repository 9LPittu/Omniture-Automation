import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';

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
