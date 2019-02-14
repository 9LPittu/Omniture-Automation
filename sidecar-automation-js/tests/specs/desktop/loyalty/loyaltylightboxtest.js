import { driver } from '../../../helpers';
import { load, loyaltyLightBoxValidation } from '../../../pageObjects/jcrewdesktoppageobj';

beforeAll(async () => {
  // await load();
  await driver.get(`${__baseUrl__}/`);
})

test('Verify Loyalty Lightbox is comming every time and able to login via loyalty Light Box', async () => {
  await driver.manage().timeouts().implicitlyWait(20000)
  await loyaltyLightBoxValidation()
  console.log("Logged in to application using loyalty lightbox successfully")
})

afterAll(async () => {
  await driver.quit()
})
