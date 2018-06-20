import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { creditcard, guestuser} from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

    test('Search  is working', async () => {
    //  await driver.executeScript('window.scrollTo(0, 20000)')
        const currentUrl = await driver.get(`${__baseUrl__}/`);
        await driver.sleep(2000)
       const headerSearch = await driver.findElement(By.xpath("//div[contains(@class,'js-primary-nav__link--search')]"))

       expect(headerSearch).toBeTruthy()
       await headerSearch.click()
       await driver.sleep(2000)
      // headerSearch.click();
       const searchInput = headerSearch.findElement(By.xpath(".//input[contains(@class,'js-primary-nav__input--search')]"))
       expect(searchInput).toBeTruthy()
       await searchInput.clear();
       await searchInput.sendKeys("dress");
       searchInput.sendKeys(Key.ENTER);
       await driver.wait(not (until.urlIs(currentUrl)),defaultTimeout)
     })
