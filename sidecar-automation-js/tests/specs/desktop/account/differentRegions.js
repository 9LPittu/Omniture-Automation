import { driver } from '../../../helpers';
import { load, closeIconInPAP,differentRegionsValidation} from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await driver.manage().timeouts().implicitlyWait(10000)
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify Context Chooser functionality ', async () => {
  let result = await differentRegionsValidation();
  expect(result).toBeTruthy();
})

afterAll(async () => {
  await driver.quit()
})
