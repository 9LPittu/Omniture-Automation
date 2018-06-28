import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/shoppingbagobj';
import {editAdress} from '../../../pageObjects/shippingaddresspageobj';
import { guestuser,jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';
import {loginFromHomePage} from '../../../pageObjects/loginpageobj';


const { Builder, By, Key, until } = require('selenium-webdriver');



test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});

// test('Login with given username and password', async () => {
//   let url = await driver.getCurrentUrl();
//
//   if (url.indexOf("www.jcrew.com") > -1) {
//
//     await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
//     console.log('user login succesfully')
//   }else if((url.indexOf("or.jcrew.com") > -1 )){
//
//   await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
//   console.log('user login succesfully')
//   }else if((url.indexOf("or.factory.jcrew.com") > -1 )){
//
//   await loginFromHomePage(factory_gold.username,factory_gold.password)
//   console.log('user login succesfully')
//   }else if((url.indexOf("https://factory.jcrew.com") > -1 )){
//
//   await loginFromHomePage(factory_prod.username,factory_prod.password)
//   console.log('user login succesfully')
//   }
//
// });


test('verify write a review functionality', async () => {
  await selectProduct();
  await driver.sleep(8000);
  if(await driver.findElement(By.css("#BVRRSecondarySummaryContainer")).isDisplayed()){
    await driver.findElement(By.css("#BVRRSecondarySummaryContainer")).click();
  }
await driver.sleep(4000);
await driver.executeScript('window.scrollTo(0, 20000)')
//await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[text()='Write a review']"))); 
await driver.findElement(By.xpath("//a[text()='Write a review']")).click();
await driver.sleep(5000);

await driver.findElement(By.css("#star_link_rating_5")).click();
await driver.findElement(By.css("#BVFieldRecommendYesID")).click();
await driver.findElement(By.css("#BVFieldRatingFit4LabelID")).click();
await driver.findElement(By.css("#star_link_rating_Style_4")).click();
await driver.findElement(By.css("#star_link_rating_Style_4")).click();
await driver.findElement(By.css("#star_link_rating_Quality_5")).click();
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
  let currentUrl = await driver.getCurrentUrl();
  await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
  driver.sleep(2000);

  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
  } else {
  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
  }
  await driver.sleep(3000)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
  await driver.sleep(3000)
}
