import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';

const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };

export const closeIcon = () => driver.findElement(jcrewCloseicon);

export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
  await driver.sleep(1000)
//  await driver.navigate().refresh()
  //await driver.sleep(2000)
//  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()
//  driver.sleep(3000)
};
