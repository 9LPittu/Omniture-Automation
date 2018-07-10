import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';

const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };

export const closeIcon = () => driver.findElement(jcrewCloseicon);

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
//  await driver.wait(until.elementLocated(closeIcon), defaultTimeout);
};
