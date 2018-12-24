import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory,verifyColorSwatch } from '../../../mobilepageobjects/marraypageobj';


const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('verify color swatch is Displayed on hovering the product', async () => {
    await selectCategory();
    await verifyColorSwatch();
    console.log('color swatch is Displayed')
});

afterAll(async () => {
  await driver.quit()
})
