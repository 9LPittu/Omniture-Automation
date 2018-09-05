import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { productArrayPage} from '../../../pageObjects/arraypage';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('verify pdp pageObjects', async () => {
      await productArrayPage()
      driver.sleep(1000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
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
      expect(productaddtowishlist).toBeTruthy()

   })
