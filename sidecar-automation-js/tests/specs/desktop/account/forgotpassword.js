import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { forgotPassword } from '../../../pageObjects/loginpageobj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify User is not able to login with invalid user credentials, display error message', async () => {
  let url = await driver.getCurrentUrl();
  let result = forgotPassword(url);
  expect(result).toBeTruthy()
  console.log("new password sent to user email")
})

afterAll(async () => {
  await driver.quit()
})
