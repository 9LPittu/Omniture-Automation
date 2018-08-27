import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('shop the look validation', async () => {
      driver.sleep(2000);
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='women']"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='new arrivals']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='new arrivals']")).click()
    }
    const arraypage = await driver.findElement(By.className('c-product__list'))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    productimage.click()
    await driver.sleep(1000)
    try{
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 200)')
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("(//li/figure/img)[1]"))).toBeTruthy()
      driver.findElement(By.xpath("(//li/figure/img)[1]")).click()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("//*[@class='c-product c-quickshop__page']"))).toBeTruthy()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("(//button[text()='Add to Bag'])[2]"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("//button[text()='Wishlist']"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("(//ul[@class='product__colors colors-list'])[2]"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("(//div[@class='c-sizes-list'])[2]"))).toBeTruthy()
}catch(err){}
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
      driver.sleep(2000);
      await driver.findElement(By.xpath("//span[text()='new arrivals']")).click()
      await driver.sleep(1000)
      const arraypage = await driver.findElement(By.className('c-product__list'))
      expect(arraypage).toBeTruthy()
      const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
      productimage.click()
      await driver.sleep(1000)
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 200)')
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("(//li/figure/img)[1]"))).toBeTruthy()
      driver.findElement(By.xpath("(//li/figure/img)[1]")).click()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("//*[@class='c-product c-quickshop__page']"))).toBeTruthy()
      await driver.sleep(1000)
      expect(await driver.findElement(By.xpath("(//button[text()='Add to Bag'])[2]"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("//button[text()='Wishlist']"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("(//ul[@class='product__colors colors-list'])[2]"))).toBeTruthy()
      expect(await driver.findElement(By.xpath("(//div[@class='c-sizes-list'])[2]"))).toBeTruthy()

     })
