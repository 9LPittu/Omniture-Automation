import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, verifyFilterAndSortFunction } from '../../../mobilepageobjects/marraypageobj';

test('title is correct', async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verifying filters and sort functionality', async () => {
  await selectCategory();
  await verifyFilterAndSortFunction();
  console.log("Filter functionality verifyed")
})

afterAll(async () => {
  await driver.quit();
})