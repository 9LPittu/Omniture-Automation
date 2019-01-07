import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';

const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Validate contactList footer elements', async () => {
  console.log("Validate contactList elements")
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//h4[text()='How can we help?']")));
  await driver.sleep(1000)
  let contactList = await driver.findElement(By.xpath("//ul[@class='contact-list']"))
  let contactItems = await contactList.findElements(By.tagName("li"))
  let contactCount = contactItems.length;
  console.log("Atleast two contacts should be present along with twitter and phone number, number of contacts present are ::" + contactCount)
  expect(contactCount).toBeGreaterThanOrEqual(2)
  for (let i = 0; i < contactCount; i++) {
    switch (i) {
      case 0:
        expect(await contactItems[i].getText()).toContain("@jcrew_help")
        console.log("Twitter contact is present")
        break;
      case 1:
        expect(await contactItems[i].getText()).toContain("1 800 562 0258")
        console.log("contact number is present")
        break;
    }
  }
})

test('Order Status is visible and url direct to right url', async () => {
  const orderstatuslink = driver.findElement(By.xpath("//li[text()='Order Status?']"))
  expect(orderstatuslink).toBeTruthy()
  await orderstatuslink.click()
  await driver.sleep(2000)
  await driver.getCurrentUrl(url => {
    expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
  })
  await driver.navigate().back()
  await driver.sleep(2000)
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
    await change.click()
    await driver.sleep(1000)
  } else {
    await driver.sleep(2000)
    await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
    await driver.sleep(2000)
    await driver.executeScript('window.scrollTo(0, 10000)')
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//div[@class='country-info']/a")));
    await driver.sleep(1000)
    const change = await driver.findElement(By.xpath("//div[@class='country-info']/a"))
    await expect(change).toBeTruthy()
    await change.click()
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
