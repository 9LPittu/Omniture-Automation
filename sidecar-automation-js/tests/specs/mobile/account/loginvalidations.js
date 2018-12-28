import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { logindetails } from '../../../testdata/jcrewTestData';
import { loginFromHomePage, signOutFromApplication, loginUsingInvalidCredentials } from '../../../mobilepageobjects/mloginpageobj';

beforeAll(async () => {
    await load();
    await driver.manage().timeouts().implicitlyWait(20000)
    expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify User is able to login with valid user credentials', async () => {
    await loginFromHomePage(logindetails.mobile_username, logindetails.mobile_password);
    await signOutFromApplication();
})

test('Verify User is not able to login with invalid user credentials, display error message', async () => {
    await loginUsingInvalidCredentials();
})

afterAll(async () => {
    await driver.quit()
})
