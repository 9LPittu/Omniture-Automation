import { driver } from '../../../helpersMobile';
import { load, searchItemNumberValidation, searchFunctionalityValidation } from '../../../mobilepageobjects/mhomepageobj';
import { Product_details} from '../../../testdata/jcrewTestData';

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Search  is working', async () => {
  await searchFunctionalityValidation()
})

test('Search with Item number and validate', async () => {
  await searchItemNumberValidation(Product_details.item_number)
})

afterAll(async () => {
  await driver.quit()
})
