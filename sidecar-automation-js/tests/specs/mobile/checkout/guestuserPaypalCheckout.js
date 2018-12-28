import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaConfig';
import { load,selectCategory,selectItemAddToBag,verifyBag } from '../../../mobilepageobjects/mhomepageobj';
import {clickOnPaypalPayment} from '../../../mobilepageobjects/singlePageCheckout';
import { doPaypalPayment } from '../../../mobilepageobjects/shippingBillingObj';

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('test Guest checkout', async () => {
    await selectCategory()
    await selectItemAddToBag()
    await verifyBag() 
    await clickOnPaypalPayment()
    await doPaypalPayment()
  })

  afterAll(async () => {
    await driver.quit()
  })