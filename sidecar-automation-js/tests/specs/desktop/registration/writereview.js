import { driver, defaultTimeout } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {createNewAccount} from '../../../pageObjects/loginpageObj';
import {productArrayPage} from '../../../pageObjects/arraypage';



const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});


test('verify write a review functionality', async () => {
  await productArrayPage()
  driver.sleep(1000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[3]")).click()
  driver.sleep(1000)
  await driver.sleep(1000)
  if(await driver.findElement(By.xpath("//span[@class='BVCustomNoReviewText']")).isDisplayed()){
    console.log("No reviews are displaying")
    await driver.findElement(By.xpath("//a[text()='Write a Review']")).click();
    await driver.sleep(2000)
    await createNewAccount()
    console.log("writing the review")
    await driver.findElement(By.xpath("//a[@id='star_link_rating_5']")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//a[@id='star_link_rating_Quality_5']")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("(//label[text()='True to Size'])[1]")).click()
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//input[@id='BVFieldTitleID']")).sendKeys("Automation testing")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//textarea[@id='BVFieldReviewtextID']")).sendKeys("hi this is automation tester testing write review functionality")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//input[@id='BVFieldAdditionalfieldSizePurchasedID']")).sendKeys("small")
    await driver.sleep(1000);
    await driver.findElement(By.xpath("//button/span[text()='Preview']")).click()
    await driver.sleep(3000);
    await driver.executeScript('window.scrollTo(0, -100)')
    await driver.sleep(1000);
    const previewHeader = await driver.findElement(By.xpath("//span[text()='Preview Your Review']"));
    expect(previewHeader).toBeTruthy()
    console.log("Preview your review header is displaying")
    const submit = await driver.findElement(By.xpath("(//span[text()='Submit'])[2]"));
    expect(submit).toBeTruthy()
    submit.click()
    await driver.sleep(3000);
    const thankyouText = await driver.findElement(By.xpath("//span[text()='Thank you!']"))
    expect(thankyouText).toBeTruthy()
  }else{
    console.log("Reviews are displaying")
    await driver.findElement(By.xpath("(//a[text()='write a review'])[3]")).click();
    await driver.sleep(2000)
    
  }

});

afterAll(async () => {
  await driver.quit()
})