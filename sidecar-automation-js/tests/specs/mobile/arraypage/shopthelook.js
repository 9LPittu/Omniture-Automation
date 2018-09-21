import { driver } from '../../../helpersMobile';
import { load, selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('shop the look validation', async () => {
    await selectCategory();

    await driver.sleep(2000)
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      const arraypage = await driver.findElement(By.className('c-product__list'))
      expect(arraypage).toBeTruthy()
      const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]"))
      productimage.click()
      await driver.sleep(3000)
      console.log("for Factory, Shop the look is unavailable")
    }
    else {
    const arraypage = await driver.findElement(By.className('c-product__list'))
    expect(arraypage).toBeTruthy()
    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]"))
    productimage.click()
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 1000)')
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 200)')
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("(//li/figure/img)[1]"))).toBeTruthy()
    driver.findElement(By.xpath("(//li/figure/img)[1]")).click()
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 300)')
    await driver.sleep(2000)
    expect(await driver.findElement(By.xpath("//ul[@class='product__colors colors-list']//li[1]"))).toBeTruthy()
    expect(await driver.findElement(By.xpath("//div[@class='c-sizes-list']"))).toBeTruthy()
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 300)')
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//button[text()='Add to Bag']"))).toBeTruthy()
    expect(await driver.findElement(By.xpath("//span[text()='Add To Wishlist']"))).toBeTruthy()
  }
     })
