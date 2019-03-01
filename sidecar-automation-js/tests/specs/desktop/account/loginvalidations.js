import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import { loginFromHomePage, invalidUserValidation, loggedInUserValidation } from '../../../pageObjects/loginpageobj';
import { jcrew_gold, jcrew_prod, factory_gold, factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  driver.manage().timeouts().implicitlyWait(30000);
  await load();
})

test('Verify User is able to login with valid user credentials', async () => {
  let url = await driver.getCurrentUrl();
  if (url.indexOf("www.jcrew.com") > -1) {
    await loginFromHomePage(jcrew_prod.username, jcrew_prod.password)
    console.log('user login succesfully')
  }
  else if ((url.indexOf("uat.jcrew.com") > -1)) {
    await loginFromHomePage(jcrew_gold.username, jcrew_gold.password)
    console.log('user login succesfully')
  }
  else if ((url.indexOf("uat.factory.jcrew.com") > -1)) {
    await loginFromHomePage(factory_gold.username, factory_gold.password)
    console.log('user login succesfully')
  }
  else if ((url.indexOf("https://factory.jcrew.com") > -1)) {
    await loginFromHomePage(factory_prod.username, factory_prod.password)
    console.log('user login succesfully')
  }
  let result = await loggedInUserValidation(url);
  expect(result).toBeTruthy()
})

test('Verify User is not able to login with invalid user credentials, display error message', async () => {
  let url = await driver.getCurrentUrl();
  let result = await invalidUserValidation(url);
  expect(result).toBeTruthy()
  console.log('login with Invalid details and display error message');
})

afterAll(async () => {
  await driver.quit()
})
