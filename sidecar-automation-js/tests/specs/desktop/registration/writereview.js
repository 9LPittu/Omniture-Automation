import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {editAdress} from '../../../pageObjects/shippingaddresspageobj';
import { guestuser,jcrew_gold,jcrew_prod,factory_gold,factory_prod,logindetails } from '../../../testdata/jcrewTestData';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';


const { Builder, By, Key, until } = require('selenium-webdriver');



test('navigate to home page', async () => {
   await load();
   console.log('Home page loaded proprely')
});

test('verify write a review functionality', async () => {
  //await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
  await selectProduct();
  await driver.sleep(8000);
  if(await driver.findElement(By.css("#BVRRSecondarySummaryContainer")).isDisplayed()){
    await driver.findElement(By.css("#BVRRSecondarySummaryContainer")).click();
  }
await driver.sleep(2000);
//await driver.executeScript('window.scrollTo(0, 1000)')
//await driver.sleep(4000);
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//p[@class='intro']"))); 
await driver.sleep(5000);
await driver.findElement(By.xpath("//a[@id='BVRRDisplayContentNoReviewsID']/a")).click();
await driver.sleep(2000);
await driver.findElement(By.css("#star_link_rating_5")).click();
await driver.findElement(By.css("#BVFieldRecommendYesID")).click();
await driver.findElement(By.css("#BVFieldRatingFit4LabelID")).click();
await driver.sleep(2000);
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css("#star_link_rating_5"))); 
//await driver.sleep(2000);
//await driver.findElement(By.css("#star_link_rating_Style_4")).click();
//await driver.findElement(By.css("#star_link_rating_Quality_4")).click();
await driver.sleep(1000);
await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.id("BVModuleReviewSubmissionTipsID"))); 
await driver.sleep(1000);
await driver.findElement(By.css("#BVFieldTitleID")).sendKeys("Automation review title");
await driver.findElement(By.css("#BVFieldReviewtextID")).sendKeys("Automation reviewAutomation reviewAutomation reviewAutomation reviewAutomation reviewAutomation reviewAutomation reviewAutomation review");
await driver.findElement(By.css("#BVFieldUsernicknameID")).sendKeys("nicknamefield");
await driver.findElement(By.css("#BVFieldUseremailID")).sendKeys("usermailID@example.com");
await driver.findElement(By.xpath("//button[@class='BVButton']/span[text()='Preview']")).click();
await driver.findElement(By.css("#BVFieldButtonHiddenInputSubmitID")).isDisplayed();

});


export const addProductTobag = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  ////FirstProduct
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='Sport']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()

  await driver.sleep(2000)
  await driver.navigate().refresh()
  await driver.sleep(3000)
  await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
  await driver.sleep(3000)
  await driver.findElement(By.id("btn__add-to-bag-wide")).click()
  await driver.sleep(3000)

}

  export const selectProduct = async () => {
  await driver.findElement(By.xpath("//span[text()='sign in']")).click()
  await driver.sleep(1000)
  await driver.findElement(By.id("sidecarUser")).sendKeys(logindetails.username1)
  await driver.sleep(1000)
  await driver.findElement(By.id("sidecarPassword")).sendKeys(logindetails.password1)
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//button[text()='Sign In Here']")).click()
  await driver.sleep(1000)
  await driver.navigate().refresh()
  await driver.sleep(1000)
  let currentUrl = await driver.getCurrentUrl();
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
  await driver.sleep(3000)

}
