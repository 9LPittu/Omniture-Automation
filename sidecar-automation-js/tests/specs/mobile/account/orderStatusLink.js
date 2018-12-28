import { driver } from '../../../helpersMobile';
import { load, selectAndVerifyOrderStatus } from '../../../mobilepageobjects/mhomepageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify Order status Link', async () => {
  await selectAndVerifyOrderStatus();
})

afterAll(async () => {
  await driver.quit()
})
