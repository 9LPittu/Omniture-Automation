import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')

test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
   expect(await driver.getTitle()).toMatch('J.Crew')
 })

  test('Wishlist Registered User functionality', async () => {
      await driver.actions().mouseMove(await driver.findElement(By.xpath("//li[@data-department='men']"))).perform();
      //await driver.actions().mouseMove(await driver.findElement(By.xpath("//*[@id='global__header']/div/div[2]/section/div/div[3]/div/ul/li[3]/a/span"))).perform();
  		driver.sleep(2000);
         let currentUrl = await driver.getCurrentUrl();
       if (currentUrl.indexOf("factory.jcrew.com") > -1) {
        await driver.findElement(By.xpath("//span[text()='Shirts']")).click()
      } else {
		  await driver.findElement(By.xpath("//span[text()='casual shirts']")).click()
    }
      await driver.sleep(3000)
      await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
      //let producttitle = await driver.findElement(By.xpath("(//span[contains(@class,'tile__detail--name')])[1]")).getText()
      await driver.sleep(2000)
      await driver.navigate().refresh()
      await driver.sleep(3000)
      //await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))]")).click()
      await driver.findElement(By.xpath(".//li[contains(@class,'js-product__size sizes-list__item btn')]")).click()
      await driver.sleep(3000)
      await driver.findElement(By.id("btn__wishlist-wide")).click()
      await driver.sleep(6000)

      await driver.findElement(By.xpath("//input[@type='email']")).sendKeys(logindetails.username)
      await driver.findElement(By.xpath("//input[@type='password']")).sendKeys(logindetails.password)
      await driver.sleep(2000)
      await driver.findElement(By.xpath("//button[text()='Sign In Here']")).click()

      await driver.sleep(3000)
      const userPannel =   await driver.findElement(By.xpath("//*[@id='c-header__userpanelrecognized']"))
      expect(userPannel).toBeTruthy()

      //await driver.navigate().refresh()
        userPannel.click()
       //await driver.findElement(By.xpath("//*[@id='c-nav__userpanel']/dl/div/dd[1]/a")).click()
       await driver.sleep(3000)
       await driver.findElement(By.xpath("//*[@id='nav__ul']/li[text()='My Wishlist']")).click()
       await driver.sleep(3000)


  const wishlistitems = driver.findElement(By.id("wishlistName"))
  await driver.wait(until.elementIsVisible(wishlistitems), 5000)
  expect(wishlistitems).toBeTruthy()
  var elementsCount = wishlistitems.length;
  console.log("wishlistitems"+ elementsCount)
  console.log("product name"+ wishlistitems)
  //  wishlistitems.each(function(e){
  //  e.getText(function(err, result){
  //     elementsCount --;
  //     if ( !!err ) { logger.error(err); /** async handles this much better **/  }
  //   //  if ( isThisTheElement(result) ) { deferred.resolve(result); }
  //   console.log("product name"+ result)
  //     if ( elementsCount == 0 ){ // in case we ran through all elements and didn't find any
  //           deferred.resolve(null); // since deferred is resolved only once, calling this again if we found the item will have no effect
  //     }
  //   })
  // })
   })
