import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { createNewAccount } from '../../../mobilepageobjects/mloginpageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('test creation of new account', async () => {
  await createNewAccount();
})

afterAll(async () => {
  await driver.quit()
})
