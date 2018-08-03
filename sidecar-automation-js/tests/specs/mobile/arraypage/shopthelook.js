import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('shop the look validation', async () => {
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(2000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await driver.sleep(2000)
      try {
        await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).click();
        driver.sleep(1000)
     } catch (err) {
     }

    await driver.sleep(2000)
    const arraypage = await driver.findElement(By.className('c-product__list'))
    expect(arraypage).toBeTruthy()

    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    productimage.click()

    /*for(var i=0;i<=4;i++){
        await driver.sleep(1000)
        await driver.executeScript('window.scrollTo(0, 500)')
        try{
          await driver.sleep(1000)
          expext(driver.findElement(By.xpath("//h3[text()='Shop The Look']"))).toBeTruthy()
          await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
          }catch (err){
        }
    }*/
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 1000)')
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")));
    //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h3[text()='Shop The Look']")))
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 200)')
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("(//li/figure/img)[1]"))).toBeTruthy()
    driver.findElement(By.xpath("(//li/figure/img)[1]")).click()
    await driver.sleep(3000)
    //expect(await driver.findElement(By.xpath("//*[@class='c-product c-quickshop__page']"))).toBeTruthy()
    //await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 300)')
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//ul[@class='product__colors colors-list']"))).toBeTruthy()
    expect(await driver.findElement(By.xpath("//div[@class='c-sizes-list']"))).toBeTruthy()
    await driver.sleep(1000)
    await driver.executeScript('window.scrollTo(0, 300)')
    await driver.sleep(1000)
    expect(await driver.findElement(By.xpath("//button[text()='Add to Bag']"))).toBeTruthy()
    expect(await driver.findElement(By.xpath("//span[text()='Add To Wishlist']"))).toBeTruthy()

     })
