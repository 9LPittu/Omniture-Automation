import { driver, defaultTimeout } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';
import { guestuser, logindetails, creditcard, zipCode } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
 })


  test('Adding single / multiple items from single PDP', async () => {

    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//a[@data-department='women']")).click()
      await	driver.sleep(1000);
        await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//a[@data-department='accessories'])[1]")));
        await driver.sleep(1000)
        await driver.findElement(By.xpath("(//a[@data-department='accessories'])[1]")).click()
        await driver.sleep(1000)
        try {
          await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
          closeIcon.click()
          driver.sleep(1000)
         })
         } catch (err)
        { }
      await driver.sleep(1000)
      await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
      await driver.sleep(1000)
      await driver.executeScript('window.scrollTo(0, 600)')
      await driver.sleep(2000)
      const preselectedsize = await driver.findElement(By.xpath("//li[contains(@class,'js-product__size sizes-list__item btn') and (contains(@class,'is-selected'))]"))
      expect(preselectedsize).toBeTruthy()
      await driver.sleep(1000)
      console.log("pre selected size chip is displayed")

   })

   afterAll(async () => {
    await driver.quit()
  })