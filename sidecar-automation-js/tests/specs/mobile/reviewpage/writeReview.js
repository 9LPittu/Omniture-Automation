import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard, logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

 test('Verify Write Review feature', async () => {
     await driver.sleep(2000)
     await driver.findElement(By.xpath("//span[text()='menu']")).click()
     await driver.sleep(1000)
     await driver.findElement(By.xpath("//a[@data-department='men']")).click()
       await	driver.sleep(2000);
       await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
       await driver.sleep(2000)
       try {
         await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
         closeIcon.click()
         driver.sleep(3000)
        })
        } catch (err)
       { }

       await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[1]")).click()
       await driver.sleep(2000)
       //await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")));
       await driver.executeScript('window.scrollTo(0, 700)')
       await driver.sleep(2000)
       await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//p[@class='intro']"))); 
       await driver.sleep(2000)
       // clicking on write  review exapnding
       await driver.findElement(By.xpath("//*[@id='BVRRRatingSummaryLinkWriteFirstID']/span[1]")).click()
       // clicking on write  review link
       await driver.findElement(By.xpath("//*[@id='BVRRDisplayContentNoReviewsID']/a")).click()
       await driver.sleep(2000)
       await driver.executeScript('window.scrollTo(0, 200)')
       // Overall rating selection
       await driver.findElement(By.xpath("//*[@id='star_link_rating_5']")).click()
       await driver.executeScript('window.scrollTo(0, 200)')
       // Style
       await driver.findElement(By.xpath("//*[@id='star_link_rating_Style_5']")).click()
       // Quality
       await driver.findElement(By.xpath("//*[@id='star_link_rating_Quality_5']")).click()
       // Recommand
       await driver.findElement(By.xpath("//*[@id='BVFieldRecommendYesID']")).click()
       // Review Title
       await driver.findElement(By.xpath("//*[@id='BVFieldTitleID']")).sendKeys("Good Product")
       // Review
       await driver.findElement(By.xpath("//*[@id='BVFieldReviewtextID']")).sendKeys("This is nice Product and Quality is good. This is nice Product and Quality is good. nice Product and Quality is good.")
       // Nickname
       await driver.findElement(By.xpath("(//*[@id='BVFieldUsernicknameID'])[1]")).sendKeys("John")
       // Email
       await driver.findElement(By.xpath("//*[@id='BVFieldUseremailID']")).sendKeys("John@example.org")
       // Preview button
       await driver.findElement(By.xpath("//*[@id='BVButtonPreviewID']/button")).click()
       // Submit button
       await driver.findElement(By.xpath("//*[@id='BVButtonSubmitID']/button")).click()
       // Thank you text
       const thankU = await driver.findElement(By.xpath("//*[@id='BVSubmissionContainer']/div[1]/div[1]/span/span"))
       expect(thankU).toBeTruthy()

})

afterAll(async () => {
  await driver.quit()
})