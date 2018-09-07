import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {productArrayPage} from '../../../pageObjects/arraypage';
const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Product arraypage validation', async () => {
    console.log("Array page is being verified ")
    await productArrayPage();

    const arraypage = await driver.findElement(By.className('c-product__list'))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()

    const producttitle = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--name')])[1]"))
    expect(producttitle).toBeTruthy()

    const productprice = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--price--list')])[1]"))
    expect(productprice).toBeTruthy()

    await driver.executeScript('window.scrollTo(0, 2000)')
    await driver.sleep(2000)

     })

     afterAll(async () => {
      await driver.quit()
    })
