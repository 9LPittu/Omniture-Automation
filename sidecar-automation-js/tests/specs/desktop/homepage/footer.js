import { driver } from '../../../helpers';
import { load , orderStatusValidation, locationUrlsValidation} from '../../../pageObjects/jcrewdesktoppageobj';

beforeAll(async () => {
  await load();
  await driver.manage().timeouts().implicitlyWait(20000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('Order Status is visible and url direct to right url', async () => {
  await orderStatusValidation()
  console.log("Order Status is visible and url direct to right url")
  await driver.navigate().back()
})

test('Location change is visible and links to correct place', async () => {
  await driver.sleep(2000)
  await locationUrlsValidation()
  console.log("user is able to select the country from context chooser")
})

afterAll(async () => {
  await driver.quit()
})

      