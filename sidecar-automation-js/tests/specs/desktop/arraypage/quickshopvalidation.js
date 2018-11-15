import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import {productArrayPage,quickShopValidations} from '../../../pageObjects/arraypage';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('quickshop validation', async () => {
      console.log("verifying Quick shop")
      await driver.manage().timeouts().implicitlyWait(30000)
      await productArrayPage()
      await closeIconInPAP()
      let result = await quickShopValidations()
      expect(result).toBeTruthy()
   })


   afterAll(async () => {
    await driver.quit()
  })
