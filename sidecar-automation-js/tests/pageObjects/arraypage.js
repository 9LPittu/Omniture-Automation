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
  if (url.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//span[text()='search']")).click()
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//input[@class='js-primary-nav__input--search']")).sendKeys("shirts")
    await driver.sleep(1000)
    await driver.findElement(By.xpath("//span[text()='search']")).click()
    await driver.sleep(2000)
  }else{
    var searchText = await driver.findElement(By.xpath("//input[@class='nc-nav__search__input']"));
    searchText.sendKeys("pants")
    await driver.sleep(1000)
    driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
  }
    await driver.sleep(1000)
    const productimage =await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]"))
    expect(productimage).toBeTruthy()
  };
  export const addProductToBag = async () =>{
    driver.sleep(1000)
    await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
    driver.sleep(2000)
    const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
    productsize.click()
    await driver.sleep(1000)
    await driver.findElement(By.id("btn__add-to-bag-wide")).click()
    await driver.sleep(1000)
    };
  export const verifyAndClickOnBag = async () =>{
    let url = await driver.getCurrentUrl();
    if (url.indexOf("factory.jcrew.com") > -1) {
      await driver.sleep(1000)
      let bagsize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
      console.log("=======Bag size "+bagsize)
      await driver.findElement(By.id("js-header__cart")).click()
      await driver.sleep(1000)
    }else{
      await driver.sleep(1000)
      try{
      let bagsize = await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__count']")).getText()
      expect(bagsize).toBeTruthy()
      console.log("=======Bag size "+bagsize)
    }catch(err){}
      await driver.findElement(By.xpath("//div[@class='nc-nav__bag-button__icon']")).click()
      await driver.sleep(1000)
    }
    };

    export const addMultiLineItems = async () =>{
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
        driver.sleep(1000)
        await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
        driver.sleep(1000)
        const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
        productsize.click()
        await driver.sleep(1000)
        await driver.findElement(By.id("btn__add-to-bag-wide")).click()
        await driver.sleep(1000)
        await driver.findElement(By.id("btn__add-to-bag-wide")).click()
        await driver.sleep(1000)
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
          const productimage2 = await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]"))
          expect(productimage2).toBeTruthy()
          driver.sleep(1000)
          await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[2]")).click()
          driver.sleep(1000)
          const productsize2 = await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
          productsize2.click()
          await driver.sleep(1000)
          await driver.findElement(By.id("btn__add-to-bag-wide")).click()
          await driver.sleep(1000)
          await driver.findElement(By.id("btn__add-to-bag-wide")).click()
          await driver.sleep(3000)
      };

      export const addsaleitemsToBag = async ()=> {
        driver.sleep(2000);
        let currentUrl = await driver.getCurrentUrl();
         if (currentUrl.indexOf("factory.jcrew.com") > -1) {
           await driver.actions().mouseMove(await driver.findElement(By.xpath("//span[text()='Clearance']"))).perform();
           driver.sleep(2000);
           await driver.findElement(By.xpath("//span[text()='women']")).click()
           driver.sleep(2000);
        } else {
        await driver.findElement(By.xpath("//span[text()='sale']")).click()
        driver.sleep(2000);
        await driver.findElement(By.xpath("(//div[@class='c-sale-recommendation-item'])[1]")).click()
      }
          await driver.sleep(2000)
          await driver.findElement(By.xpath("(//div[@class='product-tile--info'])[2]")).click()
          driver.sleep(6000)
          const productsize= await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"))
          productsize.click()
          driver.sleep(3000)
          const productaddtobag= await driver.findElement(By.id("btn__add-to-bag-wide"))
          productaddtobag.click()
          await driver.sleep(3000)
          productaddtobag.click()
          await driver.sleep(3000)
          let bagSize = await driver.findElement(By.xpath("//span[@class='js-cart-size']")).getText()
          console.log ("Bag Size >> " + bagSize)
          expect(bagSize).toBeTruthy()
      }
