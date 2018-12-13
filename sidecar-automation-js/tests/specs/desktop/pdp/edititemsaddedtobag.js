import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, addProductToBag, verifyAndClickOnBag } from '../../../pageObjects/arraypage';
import { editItemAddedToBag } from '../../../pageObjects/pdpPageObj';

beforeAll(async () => {
  await load();
  expect(await driver.getTitle()).toMatch('J.Crew')
})
test('Editing item added to bag', async () => {
  console.log("Editing item added to bag")
  await driver.manage().timeouts().implicitlyWait(20000)
  await productArrayPage()
  await closeIconInPAP()
  await addProductToBag()
  await verifyAndClickOnBag()
  await driver.sleep(3000)
  let result = await editItemAddedToBag();
  expect(result).toBeTruthy()
 /* await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  let colrNameBeforeEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
  await driver.findElement(By.xpath("//a[@class='item-edit']")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(//li[contains(@class,'js-product__color colors-list__item') and not(contains(@class,'is-unavailable'))])[2]")).click()
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//button[contains(text(),'Update Bag')]")).click()
  await driver.sleep(2000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  let colorNameAfterEdit = await driver.findElement(By.xpath("//ul[@class='item-description']/li[3]/span")).getText()
  await driver.sleep(2000)
  if (!(colrNameBeforeEdit == colorNameAfterEdit)) {
    console.log("Color displayed in chip box after edited the item color >> ")
  } */
})

afterAll(async () => {
  await driver.quit()
})
