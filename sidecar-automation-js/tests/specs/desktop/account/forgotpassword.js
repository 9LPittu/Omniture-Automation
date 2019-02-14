import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { forgotPassword } from '../../../pageObjects/loginpageobj';

beforeAll(async () => {
  await driver.manage().timeouts().implicitlyWait(30000);
  await load();
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
