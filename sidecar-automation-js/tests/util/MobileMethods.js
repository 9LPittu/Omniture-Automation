import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';


export const click = async (byLocator) => {
    await driver.executeScript("arguments[0].click();", driver.findElement(byLocator));
}
export const scroll = async (byLocator) => {
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(byLocator));
}

export const waitSeconds = async (sec) => {
    /*var start = new Date().getTime()
    while (new Date().getTime() < (start + (sec * 1000))) {
    }*/
    await driver.sleep(sec * 1000)
}