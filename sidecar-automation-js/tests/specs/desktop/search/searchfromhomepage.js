import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage } from '../../../pageObjects/arraypage';

const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('verifying search functionality', async () => {
  await productArrayPage()
  await closeIconInPAP()
  await driver.sleep(2000)
  expect(await driver.findElement(By.id("page__search")).isDisplayed()).toBeTruthy();
  console.log("search functionality is working")
 });

 afterAll(async () => {
  await driver.quit()
})
