import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory } from '../../../mobilepageobjects/marraypageobj';
import { doPaypalPayment } from '../../../mobilepageobjects/shippingBillingObj';
import { addSingleLineItemToBag, clickOnBagIcon } from '../../../mobilepageobjects/mpdppage';
import { clickOnPaypalPayment } from '../../../mobilepageobjects/singlePageCheckout';

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('test Guest checkout', async () => {
  await selectCategory()
  await addSingleLineItemToBag()
  await clickOnBagIcon();
  await clickOnPaypalPayment();
  await doPaypalPayment()
})

afterAll(async () => {
  await driver.quit()
})