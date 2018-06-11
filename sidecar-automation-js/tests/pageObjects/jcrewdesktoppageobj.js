import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpers';

const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };

export const closeIcon = () => driver.findElement(jcrewCloseicon);
export const signIn = () => driver.findElement({ xpath: "//*[@id='c-header__userpanel']/a/span[2]" });

export const load = async () => {
  try {
  await driver.get(`${__baseUrl__}/`);
  await driver.wait(until.elementLocated(closeIcon), defaultTimeout).click();
//  await driver.manage().window().maximize();
} catch (err)
{
  
}
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
export const caualshirt = () => driver.findElement({ xpath: "//span[text()='casual shirts']" });
export const quickshopbutton = () => driver.findElement({ xpath: "//button[@class='c-product-tile__quickshop js-product-tile-quickshop']/div" });
