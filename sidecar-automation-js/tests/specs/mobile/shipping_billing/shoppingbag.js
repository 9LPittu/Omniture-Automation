import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { selectCategory, selectItemAddToBag, verifyBag } from '../../../mobilepageobjects/marraypageobj';
import { waitSeconds } from '../../../util/MobileMethods';
import { clickOnCheckOutNow } from '../../../mobilepageobjects/singlePageCheckout';

const { By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await waitSeconds(2)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Go to shoppingBag page,select product and add to bag', async () => {
  await selectCategory()
  await selectItemAddToBag()
  await verifyBag()
});

test('click on login button as Guest ', async () =>{
  await waitSeconds(1)
  await clickOnCheckOutNow()
  await driver.findElement(By.xpath("//form[@id='frmGuestCheckOut']/a")).click();
});
