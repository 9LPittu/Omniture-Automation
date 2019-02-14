import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { addGiftCardToBag } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
})

test('Adding gift card items to bag', async () => {
  console.log("add gift card items to bag")
  await driver.manage().timeouts().implicitlyWait(20000)
  await addGiftCardToBag();
  console.log("done adding gift to bag")
})

afterAll(async () => {
  await driver.quit()
})
