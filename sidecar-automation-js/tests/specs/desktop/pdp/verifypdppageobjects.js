import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage } from '../../../pageObjects/arraypage';
import { validatePDPPageObjects } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('verify pdp pageObjects', async () => {
  console.log("verify pdp pageObjects")
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await validatePDPPageObjects()
  console.log("validating pdp page objects is successful")
  /* await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
   driver.sleep(1000)
   await driver.sleep(2000)
   const productcolor = await driver.findElement(By.xpath("//div[@class='c-product__colors']"))
   expect(productcolor).toBeTruthy()
   const productsize= await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]"))
   expect(productsize).toBeTruthy()
   await driver.sleep(1000)
   const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
   expect(productaddtobag).toBeTruthy()
   const productaddtowishlist= await driver.findElement(By.id("btn__wishlist-wide"))
   expect(productaddtowishlist).toBeTruthy()*/

})

afterAll(async () => {
  await driver.quit()
})
