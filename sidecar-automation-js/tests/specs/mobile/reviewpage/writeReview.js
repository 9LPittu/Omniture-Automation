import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { writeReviewValidation } from '../../../mobilepageobjects/marraypageobj';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Verify Write Review feature', async () => {
  await writeReviewValidation()
})

afterAll(async () => {
  await driver.quit()
})