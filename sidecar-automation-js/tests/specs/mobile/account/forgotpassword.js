import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { clickOnForgotPasswordLink, clickSendMeNewPasswordButton } from '../../../mobilepageobjects/mloginpageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verifying forgot password functionality', async () => {
  await clickOnForgotPasswordLink();
  await clickSendMeNewPasswordButton();
})

afterAll(async () => {
  await driver.quit()
})
