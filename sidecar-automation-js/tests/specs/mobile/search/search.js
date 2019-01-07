import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { Product_details} from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})


test('Search  is working', async () => {
  //  await driver.executeScript('window.scrollTo(0, 20000)')
  const currentUrl = await driver.get(`${__baseUrl__}/`)
  try {
    await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
      console.log("Email capture page")
      emailCapture.click()
      driver.sleep(3000)
    })
  } catch (err) { }
  let currentUrlF = await driver.getCurrentUrl();
  if (currentUrlF.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(2000)
    const headerSearchF = await driver.findElement(By.xpath("//span[@class='icon-header icon-header-search icon-search']"))
    expect(headerSearchF).toBeTruthy()
    await headerSearchF.click()
    await driver.sleep(2000)
    // headerSearch.click();
    const searchInputF = await driver.findElement(By.xpath("//input[@class='js-header__search__input header__search__input']"))
    expect(searchInputF).toBeTruthy()
    await searchInputF.clear();
    await driver.sleep(2000)
    await searchInputF.sendKeys("dress")
    searchInputF.sendKeys(Key.ENTER)
    let getCurrentUrl = await driver.getCurrentUrl()
    if (getCurrentUrl.indexOf("jcrew.com/r/search/") > -1) {
      console.log("Search results page is displayed")
    }
  }
  else {
    await driver.sleep(2000)
    const headerSearch = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button search']"))
    expect(headerSearch).toBeTruthy()
    await headerSearch.click()
    await driver.sleep(2000)
    // headerSearch.click();
    const searchInput = await driver.findElement(By.xpath("//input[@placeholder='Search J.Crew']"))
    expect(searchInput).toBeTruthy()
    await searchInput.clear();
    await driver.sleep(2000)
    await searchInput.sendKeys("dress")
    searchInput.sendKeys(Key.ENTER)
    let getCurrentUrl = await driver.getCurrentUrl()
    if (getCurrentUrl.indexOf("jcrew.com/r/search/") > -1) {
      console.log("Search results page is displayed")
    }
  } 
})

test('Search with Item number and validate', async () => {
  await driver.sleep(2000)
  const headerSearch = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button search']"))
  expect(headerSearch).toBeTruthy()
  await headerSearch.click()
  await driver.sleep(2000)
  const searchInput = await driver.findElement(By.xpath("//input[@placeholder='Search J.Crew']"))
  expect(searchInput).toBeTruthy()
  await searchInput.clear();
  await driver.sleep(2000)
  await searchInput.sendKeys(Product_details.item_number)
  await searchInput.sendKeys(Key.ENTER)
  await driver.sleep(2000)
  //validations
  let itemUrl = await driver.getCurrentUrl()
  expect(itemUrl.indexOf(Product_details.item_number)).toBeGreaterThan(-1)
  let sizeDescription = await driver.findElement(By.xpath("//div[@class='product__size-fit product__description']"))
  expect(sizeDescription).toBeTruthy()
  let productDetailsElement = await driver.findElement(By.xpath("//div[@class='product__details product__description']"))
  expect(productDetailsElement).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);",sizeDescription);
  await driver.sleep(2000)
  await productDetailsElement.click()
  await driver.sleep(1000)
  let itemDesc = await driver.findElement(By.xpath("//div[@id='c-product__description']//following::ul[@class='bullet-list']"))
  expect(itemDesc).toBeTruthy()
  let itemDescText = await itemDesc.getText()
  expect(itemDescText.indexOf(Product_details.item_number)).toBeGreaterThan(-1)
})

afterAll(async () => {
  await driver.quit()
})
