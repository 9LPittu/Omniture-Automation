import { driver, defaultTimeout } from '../../../helpers';
import { load, closeIconInPAP } from '../../../pageObjects/jcrewdesktoppageobj';
import { createNewAccount } from '../../../pageObjects/loginpageObj';
import { productArrayPage } from '../../../pageObjects/arraypage';
import { logindetails } from '../../../testdata/jcrewTestData';
import { loginFromHomePage } from '../../../pageObjects/loginPageObj'
import { doesNotReject } from 'assert';


const { Builder, By, Key, until } = require('selenium-webdriver');

beforeAll(async () => {
   await load();
   console.log('Home page loaded proprely')

});


test('verify write a review functionality', async () => {
  await loginFromHomePage(logindetails.loyaltyuser, logindetails.password);
  await productArrayPage()
  await closeIconInPAP()
  await driver.sleep(4000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[3]")).click()
  await driver.sleep(3000)
  if(await driver.findElement(By.xpath("//*[@id='BVRRRatingOverall_Rating_Summary_1']/div[2]")).isDisplayed()){
    console.log("Reviews are displaying")
    await driver.sleep(3000)
    await driver.executeScript('window.scrollTo(0, 800)')
    await driver.sleep(5000)
    await driver.findElement(By.xpath("//*[@id='BVRRRatingSummaryLinkWriteID']/a")).click();
    await driver.sleep(2000)
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

    let currentUrl = await driver.getCurrentUrl();

    if (currentUrl.indexOf("https://or.") > -1) {
    const submit = await driver.findElement(By.xpath("(//span[text()='Submit'])[2]"));
    expect(submit).toBeTruthy()
    submit.click()
    await driver.sleep(3000);
    const thankyouText = await driver.findElement(By.xpath("//span[text()='Thank you!']"))
    expect(thankyouText).toBeTruthy()
    }
  }else{
    console.log("Reviews are displaying")
    await driver.findElement(By.xpath("(//a[text()='write a review'])[3]")).click();
    await driver.sleep(2000)

  }

});

afterAll(async () => {
  await driver.quit()
})
