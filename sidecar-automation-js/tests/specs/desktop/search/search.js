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
       console.log("toBeTruthy() is executed");
        await headerSearch.click()
        await driver.sleep(2000)
       // headerSearch.click();
        const searchInput = headerSearch.findElement(By.xpath(".//input[contains(@class,'js-primary-nav__input--search')]"))
        expect(searchInput).toBeTruthy()
      //  await driver.findElement(By.xpath("//*[@class='icon-header icon-header-search icon-search']")).click();
      // await driver.findElement(By.xpath("//span[@class='primary-nav__text primary-nav__text--search']")).sendKeys("H9135");
      let url = await driver.getCurrentUrl();
      if((url.indexOf("or.factory.jcrew.com") > -1 )){
        searchInput.sendKeys("H7057"); }
        else {
          searchInput.sendKeys("H9135");
        }
        await driver.findElement(By.xpath("//*[@class='primary-nav__text primary-nav__text--search primary-nav__text--move-right']")).click();

    });
