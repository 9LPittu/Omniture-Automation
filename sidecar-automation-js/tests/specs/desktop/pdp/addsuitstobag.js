import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { selectSuitsFromCategory, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { addSuitToBag } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
})


test('Adding single / multiple Suit items to Bag', async () => {
  console.log("Adding single / multiple Suit items to Bag")
  await driver.manage().timeouts().implicitlyWait(20000)
  await selectSuitsFromCategory()
  await addSuitToBag()
  /* await driver.sleep(3000)
   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")));
   await driver.sleep(3000)
   await driver.findElement(By.xpath("(//div[@class='plus_product  plusGrid3N TheSuitShop Ludlow '])[1]")).click()
   await driver.sleep(3000)
   await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//div[@class='product__price']")));
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
   await driver.sleep(3000)
   const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
   productaddtobag.click() */
  await verifyAndClickOnBag()
})

afterAll(async () => {
  await driver.quit()
})
