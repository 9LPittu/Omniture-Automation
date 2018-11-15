import { driver } from '../../../helpers';
import { load , loyaltyFQAValidation} from '../../../pageObjects/jcrewdesktoppageobj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify Loyalty footer Faq links', async () => {
      await driver.manage().timeouts().implicitlyWait(20000)
      await loyaltyFQAValidation()
      console.log("Verify Loyalty footer Faq links validation")
})

afterAll(async () => {
  await driver.quit()
})