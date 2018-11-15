import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { loginFromHomePage } from '../../../pageObjects/loginpageobj';
import { validateSignOut } from '../../../pageObjects/arraypage';
import { jcrew_gold, jcrew_prod, factory_gold, factory_prod } from '../../../testdata/jcrewTestData';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  console.log('Home page loaded proprely')
});

test('Login with given username and password', async () => {
  let url = await driver.getCurrentUrl();

  if (url.indexOf("www.jcrew.com") > -1) {

    await loginFromHomePage(jcrew_prod.username, jcrew_prod.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("or.jcrew.com") > -1)) {

    await loginFromHomePage(jcrew_gold.username, jcrew_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("or.factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_gold.username, factory_gold.password)
    console.log('user login succesfully')
  } else if ((url.indexOf("https://factory.jcrew.com") > -1)) {

    await loginFromHomePage(factory_prod.username, factory_prod.password)
    console.log('user login succesfully')
  }
});

test('click signout button in MyAccout', async () => {
  let url = await driver.getCurrentUrl()
  await validateSignOut(url)
  console.log("click signout button in MyAccout")
});

afterAll(async () => {
  await driver.quit()
})
