import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage } from '../../../pageObjects/arraypage';


const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});
test('select product id and goto array page ', async () =>{
  await productArrayPage()
});

test('verify color swatch is Displayed in pdp page on hovering the product', async () =>{
  await driver.actions().mouseMove(await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))).perform();
  await driver.sleep(2000);
   expect(await driver.findElement(By.xpath("//ul[@class='product__colors colors-list']/li/img")).isDisplayed()).toBeTruthy();
    console.log('color swatch is Displayed')
});

afterAll(async () => {
  await driver.quit()
})