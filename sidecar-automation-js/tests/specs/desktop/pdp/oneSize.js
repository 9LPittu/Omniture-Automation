import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';


const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('One Size items should have it pre - selected in the size Chip', async () => {
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='women']"))).perform();
      driver.sleep(2000);
      await driver.findElement(By.xpath("//span[text()='Jewelry']")).click()
   } else {
   await driver.actions().mouseMove(await driver.findElement(By.xpath("//a[text()='Women']"))).perform();
   driver.sleep(2000);
   await driver.findElement(By.xpath("(//a[text()='jewelry'])[1]")).click()
   }
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
   await driver.sleep(5000)
   // expect(productimage).toBeTruthy()
   const oneSize = await driver.findElement(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and (contains(@class,'is-selected'))]"))
   expect(oneSize).toBeTruthy()
   await driver.sleep(1000)
   console.log("pre selected size chip is displayed")

})

afterAll(async () => {
  await driver.quit()
})
