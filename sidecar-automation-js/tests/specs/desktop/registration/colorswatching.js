import { driver } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { productArrayPage, colorswatchingValidation } from '../../../pageObjects/arraypage';

beforeAll(async () => {
   await load();
   await driver.manage().timeouts().implicitlyWait(20000)
   console.log('Home page loaded proprely')

});
test('select product id and goto array page ', async () =>{
  await productArrayPage()
  await closeIconInPAP()
});

test('verify color swatch is Displayed in pdp page on hovering the product', async () =>{
  /*await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[3]"))).perform();
  await driver.sleep(2000);
   expect(await driver.findElement(By.xpath("//ul[@class='product__colors colors-list']/li/img")).isDisplayed()).toBeTruthy();*/
   await colorswatchingValidation()
   console.log('color swatch is Displayed')
});

afterAll(async () => {
  await driver.quit()
})
