import { driver } from '../../../helpersMobile';
import { load, selectAndVerifyStoreLocator } from '../../../mobilepageobjects/mhomepageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Store Locator link should navigate to store search page', async () => {
  await selectAndVerifyStoreLocator();
})

afterAll(async () => {
  await driver.quit()
})
