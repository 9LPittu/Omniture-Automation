import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory,verifyExtendedSize } from '../../../mobilepageobjects/marraypageobj';

test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('extended sizes validation', async () => {
    await selectCategory();
    await verifyExtendedSize();
    console.log("Extended sizes are available for product selected")
 })

afterAll(async () => {
   await driver.quit();
})

