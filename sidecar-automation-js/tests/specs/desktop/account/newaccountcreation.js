import { driver } from '../../../helpers';
import { load, loadLoginUrl } from '../../../pageObjects/jcrewdesktoppageobj';
import { createNewAccount, newAccountCreateValidation } from '../../../pageObjects/loginPageObj';

beforeEach(async () => {
  await driver.manage().timeouts().implicitlyWait(30000);
  await loadLoginUrl()
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('test creation of new account', async () => {
  await createNewAccount()
  let url = await driver.getCurrentUrl()
  await driver.sleep(20000)
  let result = await newAccountCreateValidation(url)
  expect(result).toBeTruthy()
})

afterAll(async () => {
  await driver.quit()
})
