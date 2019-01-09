import { driver } from '../../../helpersMobile';
import { load,validateContactList,validateOrderStatus,locationChangeValidation } from '../../../mobilepageobjects/mhomepageobj';

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Validate contactList footer elements', async () => {
   await validateContactList()
})

test('Order Status is visible and url direct to right url', async () => {
  await validateOrderStatus()
})

test('Location change is visible and links to correct place', async () => {
  let currentUrl = await driver.getCurrentUrl();
  await locationChangeValidation(currentUrl)
})
