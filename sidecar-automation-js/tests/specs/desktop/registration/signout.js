import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});
test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
    console.log('user login succesfully')
  }else if((url.indexOf("or.jcrew.com") > -1 )){

  await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("or.factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_gold.username,factory_gold.password)
  console.log('user login succesfully')
  }else if((url.indexOf("https://factory.jcrew.com") > -1 )){

  await loginFromHomePage(factory_prod.username,factory_prod.password)
  console.log('user login succesfully')
  }
});

test('click signout button in MyAccout', async () => {
  let url = await driver.getCurrentUrl()
  if(url.includes("factory")){
  const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
  expect(loggedInUser).toBeTruthy()
  await driver.actions().mouseMove(loggedInUser).perform();
  await driver.sleep(2000)
  const signOut = await driver.findElement(By.xpath("//a[@class='js-signout__link']"))
  expect(signOut).toBeTruthy()
  signOut.click()
  await driver.sleep(5000)
  expect(await driver.findElement(By.xpath(".//span[text()='sign in']")).isDisplayed()).toBeTruthy();
  }else{
  await driver.sleep(8000)
  const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
  expect(loggedInUser).toBeTruthy()
  await driver.actions().mouseMove(loggedInUser).perform();
  await driver.sleep(2000)
  const signOut = await driver.findElement(By.xpath("//li[5]/a[text()='Sign Out']"))
  expect(signOut).toBeTruthy()
  signOut.click()
  await driver.sleep(5000)
  expect(await driver.findElement(By.xpath(".//a[text()='Sign In']")).isDisplayed()).toBeTruthy();
  }

});

  afterAll(async () => {
    await driver.quit()
  })
