import { driver } from '../../../helpersMobile';
import { load,goToMyDetailsAndValidate} from '../../../mobilepageobjects/mhomepageobj';
import { loginFromHomePage } from '../../../mobilepageobjects/mloginpageobj';
import { logindetails} from '../../../testdata/jcrewTestData';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Validate Mydetails of Logged in user', async () => {
  await loginFromHomePage(logindetails.username, logindetails.password)
  await goToMyDetailsAndValidate()
})

afterAll(async () => {
  await driver.quit()
})
