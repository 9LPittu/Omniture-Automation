import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';

const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Order Status is visible and url direct to right url', async () => {
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h4[text()='How can we help?']")));
  await driver.sleep(1000)
  const orderstatuslink = driver.findElement(By.xpath("//li[text()='Order Status?']"))
  expect(orderstatuslink).toBeTruthy()
  await orderstatuslink.click()
  await driver.sleep(2000)
  await driver.getCurrentUrl(url => {
    expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
  })
  await driver.navigate().back()
  await driver.sleep(1000)
})

test('Location change is visible and links to correct place', async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 10000)')
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[text()='change']")));
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).click()
    await driver.sleep(1000)
    const change = await driver.findElement(By.xpath("//a[text()='change']"))
    await expect(change).toBeTruthy()
    change.click()
    await driver.sleep(1000)
  } else {
    await driver.sleep(2000)
    await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
    await driver.sleep(2000)
    await driver.executeScript('window.scrollTo(0, 10000)')
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//a[@class='footer__country-context__link']")));
    await driver.sleep(1000)
    const change = await driver.findElement(By.xpath("//a[@class='footer__country-context__link']"))
    await expect(change).toBeTruthy()
    change.click()
    await driver.sleep(1000)
  }
  await driver.getCurrentUrl(url => {
    expect(url).toMatch('r/context-chooser')
  })
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//h5[text()='UNITED STATES & CANADA']")).click()
  try {
    await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).click()
  } catch (err) {

  }
  await driver.findElement(By.xpath("//span[text()='Canada']")).click()
  await driver.sleep(2000)
})
