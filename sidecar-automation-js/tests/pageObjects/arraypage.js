import { driver, defaultTimeout } from '../helpers';
import { globals } from '../jestJcrewQaConfig';


const { Builder, By, Key, until } = require('selenium-webdriver');

export const productArrayPage = async () =>{
  try {
    await driver.findElement(By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']")).then(privacyPolicyClose => {
    // console.log("inside merge page")
     privacyPolicyClose.click()
     driver.sleep(3000)
   })
   } catch (err)
  { }
  let url = await driver.getCurrentUrl();
  await driver.findElement(By.xpath("//span[text()='search']")).click()
  await driver.sleep(1000)
  if (url.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(By.xpath("//input[@class='js-primary-nav__input--search']")).sendKeys("shirts")
  }else{
    await driver.findElement(By.xpath("//input[@class='js-primary-nav__input--search']")).sendKeys("pants")
  }
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//span[text()='search']")).click()
    await driver.sleep(2000)
    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()
  };
  export const addProductToBag = async () =>{
    driver.sleep(1000)
    await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
    driver.sleep(1000)
    const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
    productsize.click()
    await driver.sleep(1000)
    await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(1000)
    };
  export const verifyAndClickOnBag = async () =>{
      await driver.sleep(1000)
      let bagsize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log("=======Bag size "+bagsize)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(1000)
    };
