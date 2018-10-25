import { driver, defaultTimeout } from '../../../helpersMobile';
import { load, selectCategory } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, guestuser } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Product arraypage validation', async () => {
    console.log("Array page is being verified ")
    await driver.sleep(2000)
    await selectCategory();
    await driver.sleep(2000)

    const arraypage = await driver.findElement(By.xpath("//div[@class='product__list']"))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()

    const producttitle = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--name')])[1]"))
    expect(producttitle).toBeTruthy()

    const productprice = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--price')])[1]"))
    expect(productprice).toBeTruthy()


     })

     afterAll(async () => {
      await driver.quit()
    })
