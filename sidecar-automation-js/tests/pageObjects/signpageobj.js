import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpers';

const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };

export const closeIcon = () => driver.findElement(jcrewCloseicon);
export const signIn = () => driver.findElement({ xpath: "//*[@id='c-header__userpanel']/a/span[2]" });

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
  await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
};

export const userPannel = () => driver.findElement({ xpath: "//*[@id='c-header__userpanelrecognized']" });

export const signIn = () => driver.findElement({ xpath: "//*[@id='c-header__userpanel']/a/span[2]" });
