import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import { guestuser, logindetails, creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../pageObjects/loginpageobj';
import { jcrew_gold,jcrew_prod,factory_gold,factory_prod } from '../../../testdata/jcrewTestData';

const { Builder, By, Key, until } = require('selenium-webdriver')

 beforeAll(async () => {
   await load();
   await driver.sleep(2000)
    expect(await driver.getTitle()).toMatch('J.Crew')
  })

test('Verify User is able to login with valid user credentials', async () => {
    let url = await driver.getCurrentUrl();
    if (url.indexOf("www.jcrew.com") > -1) {

      await loginFromHomePage(jcrew_prod.username,jcrew_prod.password)
      console.log('user login succesfully')
    }
    else if((url.indexOf("or.jcrew.com") > -1 )){
    await loginFromHomePage(jcrew_gold.username,jcrew_gold.password)
    await driver.sleep(3000)
    console.log('user login succesfully')
    }
    else if((url.indexOf("or.factory.jcrew.com") > -1 )){
      await loginFromHomePage(factory_gold.username,factory_gold.password)
      await driver.sleep(5000)

    console.log('user login succesfully')
    }
    else if((url.indexOf("https://factory.jcrew.com") > -1 )){

    await loginFromHomePage(factory_prod.username,factory_prod.password)
    console.log('user login succesfully')
    }

  await driver.actions().mouseMove(await driver.findElement(By.id("c-header__userpanelrecognized"))).perform();
  //class js-signout__link --button
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//a[@class='js-signout__link']")).click();

})

   test('Verify User is not able to login with invalid user credentials, display error message', async () => {
       await driver.sleep(3000)
       await driver.navigate().to(globals.__baseUrl__)
       await driver.findElement(By.xpath(".//*[text()='sign in']")).click()
       await driver.sleep(4000)
       await driver.findElement(By.xpath("//*[@id='sidecarUser']")).sendKeys("dummyemail@gmail.com")
       await driver.findElement(By.xpath("//*[@id='sidecarPassword']")).sendKeys("123456ab")
       await driver.sleep(2000)
       await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[1]/div/form/button")).click()
       await driver.sleep(2000)
       const errorMsg = await driver.findElement(By.className('js-invalid-msg is-important'))
       expect(errorMsg).toBeTruthy()
      console.log('login with Invalid details and display error message');
        })

         afterAll(async () => {
           await driver.quit()
         })
