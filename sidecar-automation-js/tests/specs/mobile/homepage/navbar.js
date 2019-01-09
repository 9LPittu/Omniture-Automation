import { driver } from '../../../helpersMobile';
import { load, navBarValidation ,navBarValidationEachSection} from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('Navbar is visible', async () => {
  console.log("verifying navigation bar ")
  await navBarValidation()
})

test('Global promo is visible and links correctly', async () => {
    /*  console.log("verifying global__promo")
      console.log("Global__promo is removed from Jcrew brand") */

      /*const promo = driver.findElement(By.id("global__promo"))
      expect(promo).toBeTruthy()
      await promo.click()
      await driver.sleep(2000)
      await driver.getCurrentUrl(url => {
        expect(url.match('womens_feature/newarrivals')).toBeTruthy()
        console.log("verified global__promo")
      })
      await driver.navigate().back()*/
})


describe('All main nav links work', () => {
  each([
    ['Women'],
    ['MEN'],
    ['BOYS'],
    ['GIRLS'],
    ['SALE']
  //      ['FACTORY'],
  ]).test('%s links to correct page', async link => {
    await navBarValidationEachSection(link)
  })
})

afterAll(async () => {
  await driver.quit()
})