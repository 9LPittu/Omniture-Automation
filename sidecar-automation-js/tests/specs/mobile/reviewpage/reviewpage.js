import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { jcrew_gold } from '../../../testdata/jcrewTestData';
import { reviewPageValidation } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Review Page Validation', async () => {
  await reviewPageValidation(jcrew_gold.username,jcrew_gold.password)

})

afterAll(async () => {
  await driver.quit()
})