import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


beforeAll(async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Gender Navigation Verification', async () => {
   let currentUrl = await driver.getCurrentUrl();
   if(currentUrl.includes("factory")){
   await driver.sleep(1000)
   // Women
   let menuw = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuw).toBeTruthy()
   menuw.click()
   await driver.sleep(5000)
   await driver.findElement(By.xpath("(//a[@data-department='women'])[1]")).click()
   await  driver.sleep(2000);
   await driver.findElement(By.xpath("//*[@id='global__nav']/div/div[2]/div[3]/ul/div[1]/li[2]/a")).click()
   await  driver.sleep(4000);
   let womenNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']"))
   expect(womenNewArrivals).toBeTruthy()

   // Men
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(5000)
   let menuM = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuM).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   //*[@id="global__nav"]/div/div[2]/div[1]/span[2] Back button
   await driver.sleep(3000)
   await driver.findElement(By.xpath("(//a[@data-department='men'])[1]")).click()
   await  driver.sleep(2000);
   await driver.findElement(By.xpath("//*[@id='global__nav']/div/div[2]/div[4]/ul/div[1]/li[2]/a")).click()
   await  driver.sleep(4000);
   let menNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']"))
   expect(menNewArrivals).toBeTruthy()

   // Girls
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(5000)
   let menuG = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuG).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("(//a[@data-department='girls'])[1]")).click()
   await  driver.sleep(2000);
   await driver.findElement(By.xpath("//*[@id='global__nav']/div/div[2]/div[5]/ul/div[1]/li[2]/a")).click()
  await driver.sleep(3000)
   let girlsNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']"))
   expect(girlsNewArrivals).toBeTruthy()

   // Boys
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(5000)
   let menuB = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuB).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("(//a[@data-department='boys'])[1]")).click()
   await  driver.sleep(2000);
   await driver.findElement(By.xpath("//*[@id='global__nav']/div/div[2]/div[6]/ul/div[1]/li[2]/a")).click()
   await driver.sleep(3000)
   let boysNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']"))
   expect(boysNewArrivals).toBeTruthy()
 }else{
   let url = null;
   //women
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[1]")).click()
   await	driver.sleep(2000);
   await driver.executeScript('window.scrollTo(0, 200)')
   await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
   url = await driver.getCurrentUrl();
   if(url.includes("womens_feature/newarrivals")){
     console.log("User navigated to women category")
   }
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(1000)
   //men
   await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
   await	driver.sleep(2000);
   await driver.executeScript('window.scrollTo(0, 200)')
   await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
   url = await driver.getCurrentUrl();
   if(url.includes("mens_feature/newarrivals")){
     console.log("User navigated to men category")
   }
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(1000)
   //Girls
   await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[3]")).click()
   await	driver.sleep(2000);
   await driver.executeScript('window.scrollTo(0, 200)')
   await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
   url = await driver.getCurrentUrl();
   if(url.includes("girls_feature/newarrivals")){
     console.log("User navigated to Girls category")
   }
   await driver.navigate().to(globals.__baseUrl__)
   await driver.sleep(1000)
   //Boys
   await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
   await driver.sleep(2000)
   await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[4]")).click()
   await	driver.sleep(2000);
   await driver.executeScript('window.scrollTo(0, 200)')
   await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
   currentUrl = await driver.getCurrentUrl();
   if(currentUrl.includes("boys_feature/newarrivals")){
     console.log("User navigated to Boys category")
   }
 }

})


afterAll(async () => {
  await driver.quit()
})
