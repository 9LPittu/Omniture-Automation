import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import {clickOnContinue} from '../../../pageObjects/shippingaddresspageobj';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')

});

test('verify search functionality is working properly or not', async () => {
  await driver.findElement(By.xpath("//*[@class='icon-header icon-header-search icon-search']")).click();
  await driver.findElement(By.xpath("//*[@id='inputSearchDesktop']")).sendKeys("shirts");
  await   driver.findElement(By.xpath("//*[@class='primary-nav__text primary-nav__text--search primary-nav__text--move-right']")).click();
  await driver.sleep(4000);
  expect(await driver.findElement(By.id("page__search")).isDisplayed()).toBeTruthy();

 });
