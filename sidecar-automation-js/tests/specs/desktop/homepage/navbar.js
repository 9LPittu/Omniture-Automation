import { driver } from '../../../helpers';
import { load, navBarVisible, globalPromoVisibleValidation, storeVisibilityValidation, searchInputValidation, mouseHoverSubCategory,allNavLinksValidation } from '../../../pageObjects/jcrewdesktoppageobj';

const each = require('jest-each')
/*const { Builder, By, Key, until } = require('selenium-webdriver')
const dep_nav_list = By.xpath("//ul[@class='department-nav__list']");
const nav_dep = By.xpath("//ul[@class='nc-nav__departments']");
const global_promo = By.id("global__promo");
const nc_promo = By.className("nc-promo");
const primary_nav = By.className("primary-nav__text primary-nav__text--stores");
const store_locator = By.xpath("//div[text()='Store Locator']");
const search = By.xpath("//span[contains(text(),'search')]");
const search_input = By.xpath("//input[@class='nc-nav__search__input']");
const men = By.xpath("//a[text()='Men']");
const nav_fliyout_list = By.xpath("//li[@class='nc-nav__flyout-link__wrapper nc-nav__list-item']");*/

let currentUrl;

test('title is correct', async () => {
  await load();
  currentUrl = await driver.getCurrentUrl();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

afterAll(async () => {
  await driver.quit()
})

test('Navbar is visible', async () => {
  currentUrl = await driver.getCurrentUrl();
  await navBarVisible(currentUrl)
})

test('Global promo is visible and links correctly', async () => {
  currentUrl = await driver.getCurrentUrl();
  await globalPromoVisibleValidation(currentUrl)
})

test('Stores is visible and url direct to right URL', async () => {
  currentUrl = await driver.getCurrentUrl();
  await storeVisibilityValidation(currentUrl)
})

test('Search allows you to input a search term', async () => {
  currentUrl = await driver.getCurrentUrl();
  await searchInputValidation(currentUrl)
})

describe('Verify Nav mouse over shows the sub-category items', () => {
  each([
    ['New'],
    ['Women'],
    ['Men'],
    ['kids'],
    ['Brands We Love'],
    ['sale'],
  ]).test('%s mouse over shows the sub category items  ', async () => {
    await mouseHoverSubCategory(currentUrl)
  })
})


describe('All main nav links work', () => {
  afterEach(async () => {
    await driver.navigate().back()
  })
  each([
    ['Women'],
    ['Men'],
    // ['kids']
    //  ['Girls'],
    //  ['Boys'],
  ]).test('%s links to correct page', async link => {
    currentUrl = await driver.getCurrentUrl();
    await allNavLinksValidation(currentUrl, link)
  })
})

afterAll(async () => {
  await driver.quit()
})