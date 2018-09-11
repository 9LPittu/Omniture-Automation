import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';

const jcrewCloseicon = { xpath: "(//span[@class='icon-close'])[1]" };
const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')
export const closeIcon = () => driver.findElement(jcrewCloseicon);

export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
  await driver.sleep(1000)
//  await driver.navigate().refresh()
  //await driver.sleep(2000)
//  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()
//  driver.sleep(3000)
};

// for selecting the Category
export const selectCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(5000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("(//a[@data-department='men'])[1]")).click()
    await	driver.sleep(3000);
    await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
  } else {
  await driver.sleep(5000)
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  // Category link
  await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[1]")).click()
  await	driver.sleep(2000);
  // New arrivals
  await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
  }
  try {
  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
  console.log("Email capture page")
  emailCapture.click()
  driver.sleep(3000)
   })
   } catch (err)
     { }
  await driver.sleep(2000)
};

// Select Item and add it to Bag
export const selectItemAddToBag = async () => {
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
  await driver.sleep(1000)
  await driver.executeScript('window.scrollTo(0, 700)')
  await driver.sleep(3000)
  const size = await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"));
  size.click()
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click()
  await driver.sleep(3000)
};

// Verify Shopping bag
export const verifyBag = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
    await driver.sleep(3000)
    const itemsF = await driver.findElement(By.xpath("//*[@id='js-header__cart']/span[2]/span")).getText();
    expect(itemsF).toBeTruthy()
    console.log("numbers of items in the Bag are.. "+itemsF)
    await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
    await driver.sleep(2000)
} else {
  // clicking on Bag icon
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button bag']")));
  let bagIcon = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button bag']"))
  expect(bagIcon).toBeTruthy()
  // item count on shopping bag
  const items = await driver.findElement(By.xpath("//span[@class='cart-badge']")).getText();
  expect(items).toBeTruthy()
  //let bagCount = items.get ()
  console.log("numbers of items in the Bag are.. "+items)
  bagIcon.click()
  await driver.sleep(2000)
  }
};
