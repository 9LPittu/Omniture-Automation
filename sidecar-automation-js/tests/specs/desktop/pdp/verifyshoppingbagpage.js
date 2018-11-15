import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { validateShoppingBagPage } from '../../../pageObjects/pdpPageObj'

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('verifying shoppingbag page', async () => {
  console.log("verifying shoppingbag page")
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await validateShoppingBagPage()
  console.log("links are displaying")
  //await driver.sleep(3000)
  /* await driver.navigate().to(globals.__baseUrl__+"/checkout2/shoppingbag.jsp?sidecar=true")
   await driver.sleep(1000)
   expect(await driver.findElement(By.id("zipcode"))).toBeTruthy()
    console.log("Zip code text box is displaying")
    expect(await driver.findElement(By.xpath("//a[@class='button-general button-paypal']"))).toBeTruthy()
    console.log("Paypal payment method is displaying")
    expect(await driver.findElement(By.id("summary-promo-header"))).toBeTruthy()
    await driver.findElement(By.id("summary-promo-header")).click()
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//input[@data-textboxid='promoCode']"))).toBeTruthy()
    console.log("Promo code text box is displaying")
    await driver.sleep(1000)
    expect(await driver.findElement(By.id("summary-gift-card-header"))).toBeTruthy()
    await driver.findElement(By.id("summary-gift-card-header")).click()
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//input[@data-textboxid='giftCard']"))).toBeTruthy()
    console.log("Gift card payment method is displaying")
    expect(await driver.findElement(By.xpath("//a[text()='What is your return policy?']"))).toBeTruthy()
    expect(await driver.findElement(By.xpath("//a[text()='When can I expect my order?']"))).toBeTruthy()
*/
})

afterAll(async () => {
  await driver.quit()
})
