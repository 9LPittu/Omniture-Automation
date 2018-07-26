import { driver } from '../../../helpersMobile';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import element from '../../../util/commonutils';


const { By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

test('Go to shoppingBag page and verify Shopping tongue', async () => {
  await driver.findElement(By.xpath("//span[text()='menu']")).click()
  await driver.sleep(3000)
  await driver.findElement(By.xpath("//a[@data-department='women']")).click()
    await	driver.sleep(1000);
      await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[1]")).click()
      await driver.sleep(1000)
      try {
        await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
        closeIcon.click()
        driver.sleep(3000)
       })
       } catch (err)
      { }
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 700)')
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click();
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click()
      await driver.sleep(1000)
      let checkout = await driver.findElement(By.xpath("//a[text()='Checkout']"))
      expect(checkout).toBeTruthy()

});
