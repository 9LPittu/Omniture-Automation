import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';


export const click = async(byLocator) =>{
await driver.executeScript("arguments[0].click();",driver.findElement(byLocator));
}
export const scroll = async(byLocator) =>{
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(byLocator));
}
