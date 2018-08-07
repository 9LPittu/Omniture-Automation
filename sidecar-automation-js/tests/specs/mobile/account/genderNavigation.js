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
   await driver.sleep(5000)
   // Women
   let menuw = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuw).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='women']")).click()
   await	driver.sleep(2000);
   await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
   await	driver.sleep(4000);
   let womenNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']/div/h1"))
   expect(womenNewArrivals).toBeTruthy()

   // Men
   await driver.sleep(5000)
   let menuM = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuM).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   //*[@id="global__nav"]/div/div[2]/div[1]/span[2] Back button
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='men']")).click()
   await	driver.sleep(2000);
   await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
   let menNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']/div/h1"))
   expect(menNewArrivals).toBeTruthy()

   // Girls
   await driver.sleep(5000)
   let menuG = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuG).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='girls']")).click()
   await	driver.sleep(2000);
   await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
   let girlsNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']/div/h1"))
   expect(girlsNewArrivals).toBeTruthy()

   // Boys
   await driver.sleep(5000)
   let menuB = await driver.findElement(By.xpath("//span[text()='menu']"))
   expect(menuB).toBeTruthy()
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(3000)
   await driver.findElement(By.xpath("//a[@data-department='boys']")).click()
   await	driver.sleep(2000);
   await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
   let boysNewArrivals = await driver.findElement(By.xpath("//*[@id='c-category__page-title']/div/h1"))
   expect(boysNewArrivals).toBeTruthy()

})
