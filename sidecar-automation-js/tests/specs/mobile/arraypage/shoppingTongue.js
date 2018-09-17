import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load, selectCategory, selectItemAddToBag } from '../../../mobilepageobjects/mhomepageobj';
import element from '../../../util/commonutils';

const { By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('Go to shoppingBag page and verify Shopping tongue', async () => {

      await selectCategory();
      await selectItemAddToBag();
      let checkout = await driver.findElement(By.xpath("//button[text()='Checkout']"))
      expect(checkout).toBeTruthy()

});

afterAll(async () => {
  await driver.quit()
})