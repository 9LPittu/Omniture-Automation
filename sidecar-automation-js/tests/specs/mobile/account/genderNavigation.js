import { driver } from '../../../helpersMobile';
import { load } from '../../../mobilepageobjects/mhomepageobj';
import { creditcard } from '../../../testdata/jcrewTestData';
import {loginFromHomePage,clearBagItems} from '../../../mobilepageobjects/mloginpageobj';
import { globals } from '../../../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key, until } = require('selenium-webdriver')


test('title is correct', async () => {
  await load();
  await driver.sleep(2000)
  expect(await driver.getTitle()).toMatch('J.Crew')
})

test('Gender Navigation Verification', async () => {
   await driver.sleep(6000)
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

})
