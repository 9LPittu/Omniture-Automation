import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('verifying search functionality', async () => {

  const searchlink = await driver.findElement(By.xpath("//span[text()='search']"))
  expect(searchlink).toBeTruthy()
  searchlink.click()
  await driver.sleep(1000)
  const inputsearchText = await driver.findElement(By.xpath("//input[@class='js-primary-nav__input--search']"))
  expect(inputsearchText).toBeTruthy()
  inputsearchText.sendKeys("shirts")
  const searchButton = await driver.findElement(By.xpath("//span[text()='search']"))
  expect(searchButton).toBeTruthy()
  searchButton.click()
  await driver.sleep(2000)
  expect(await driver.findElement(By.id("page__search")).isDisplayed()).toBeTruthy();
  console.log("search functionality is working")
 });
