import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage } from '../../../pageObjects/arraypage';


const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('verifying sort and filter functionality', async () => {
  await productArrayPage()
  await driver.sleep(2000)
  const hidefilters = await driver.findElement(By.css("#c-filters__header-item--toggle"));
  expect(hidefilters.isDisplayed()).toBeTruthy()
  console.log("filter header is displaying")
  await driver.sleep(3000)
  const gender = await driver.findElement(By.xpath("//button[@data-label='Women']"))
  expect(gender).toBeTruthy()
  gender.click()
  console.log("filter header is displaying")
  await driver.sleep(5000)
  const filteredCategory = await driver.findElement(By.xpath("(//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span)[1]"));
  expect(filteredCategory.isDisplayed()).toBeTruthy()
  const clearAll = await driver.findElement(By.xpath("//span[text()='Clear All']"));
  expect(clearAll.isDisplayed()).toBeTruthy()
  clearAll.click()
  await driver.sleep(3000)
  const sortBy = await driver.findElement(By.xpath("//span[text()='Sort By']"));
  expect(sortBy.isDisplayed()).toBeTruthy()
  sortBy.click()
  await driver.sleep(1000)
  const lowToHigh = await driver.findElement(By.xpath("//li[text()='Price: Low to High']"));
  expect(lowToHigh.isDisplayed()).toBeTruthy()
  lowToHigh.click()
  await driver.sleep(3000)
  const sortApplied = await driver.findElement(By.xpath("//span[text()='Price: Low to High']"));
  expect(sortApplied.isDisplayed()).toBeTruthy()
  console.log("Sort functionality is working")

});

afterAll(async () => {
  await driver.quit()
})