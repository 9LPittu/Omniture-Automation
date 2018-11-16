import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';

const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')

//const shipTo =  driver.findElement(By.xpath("//div[@class='country-info']/span"))
//const shipToCountry =  driver.findElement(By.xpath("//div[@class='country-info']/span/../a"))
//const countries =  driver.findElement(By.xpath("//*[contains(@class,'accordian__header')]/text()"))

//const jcrewCloseicon = ;
//const buyGiftCard = driver.findElement(By.xpath("//li[text()='Buy a Gift Card']"));
//const classicCard = driver.findElement(By.xpath("//a[@id='classicGiftCard']/img"));
//const eCard = driver.findElement(By.xpath("//a[@id='eGiftCard']/img"));

export const closeIcon = async() => {
  try {
  await driver.sleep(1000)
  driver.findElement(By.xpath("(//span[@class='icon-close'])[1]")).click();  // close the popups
  await driver.sleep(2000)
  } catch (err){}
}

export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
  //await driver.sleep(1000)

};

// for selecting the Category
export const selectCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(6000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("(//a[@data-department='men'])[1]")).click()
    await	driver.sleep(3000);
    await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
  } else {
  await driver.sleep(5000)
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  // Category link
  await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
  await	driver.sleep(2000);
  //await driver.executeScript('window.scrollTo(0, 200)')

  // New arrivals
  await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
  }
  try {
  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
  console.log("Email capture page")
  emailCapture.click()
  driver.sleep(3000)
   })
   } catch (err)
     { }
  await driver.sleep(2000)
};

// Select Item and add it to Bag
export const selectItemAddToBag = async () => {
  await driver.sleep(2000)
  await driver.findElement(By.xpath("(//a[@class='product-tile__link']/img)[2]")).click()
  await driver.sleep(1000)
  await driver.executeScript('window.scrollTo(0, 700)')
  await driver.sleep(3000)
  const size = await driver.findElement(By.xpath("(//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]"));
  size.click()
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click()
  await driver.sleep(3000)
};

// Verify Shopping bag
export const verifyBag = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(1000)
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.css(".js-cart-size")));
    await driver.sleep(3000)
    const itemsF = await driver.findElement(By.xpath("//*[@id='js-header__cart']/span[2]/span")).getText();
    expect(itemsF).toBeTruthy()
    console.log("numbers of items in the Bag are.. "+itemsF)
    await driver.executeScript("arguments[0].click();",driver.findElement(By.css(".js-cart-size")))
    await driver.sleep(2000)
    await driver.findElement(By.css(".js-cart-size")).click()
} else {
  // clicking on Bag icon
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button bag']")));
  let bagIcon = await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button bag']"))
  expect(bagIcon).toBeTruthy()
  // item count on shopping bag
  const items = await driver.findElement(By.xpath("//span[@class='cart-badge']")).getText();
  expect(items).toBeTruthy()
  //let bagCount = items.get ()
  console.log("numbers of items in the Bag are.. "+items)
  bagIcon.click()
  await driver.sleep(2000)
  }
};

export const goToGiftCardPage = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//h6[text()='Contact Us']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//h6[text()='Let Us Help You']")).click()
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//div[text()='The J.Crew Gift Card']")).click()
  await driver.sleep(1000)
}else{
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(1000)
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  const seeAllTopics = await driver.findElement(By.xpath("//li[text()='See all help topics']"))
  await driver.executeScript("arguments[0].scrollIntoView(true);",seeAllTopics);
  await driver.sleep(1000)
  seeAllTopics.click()
  await driver.sleep(1000)
  const giftCard = await driver.findElement(By.xpath("//a[text()='J.CREW GIFT CARD']"));
  expect(giftCard).toBeTruthy()
  giftCard.click()
  await driver.sleep(1000)
}
  try {
    await driver.findElement(By.xpath("//div[@class='email-capture--close modal-capture--close js-email-capture--close']")).then(emailCapture => {
    emailCapture.click()
    driver.sleep(1000)
   })
 } catch (err)   { }

};

export const selectSaleItems = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
   driver.sleep(2000);
   await driver.findElement(By.xpath("//span[text()='menu']")).click()
   await driver.sleep(1000)
   await driver.findElement(By.xpath("//a[@data-department='clearance']")).click()
   await	driver.sleep(2000);
   await driver.findElement(By.xpath("//a[text()='men']")).click()
   await driver.sleep(2000)
  } else {
  await driver.sleep(5000)
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  // Category link
  await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[6]")).click()
  await	driver.sleep(1000);
  await driver.findElement(By.xpath("(//div[@class='c-sale-recommendation-image'])[2]")).click()
  await	driver.sleep(1000);
  // New arrival  await driver.findElement(By.xpath("//li[text()='New Arrivals']")).click()
  }
  try {
  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(emailCapture => {
  console.log("Email capture page")
  emailCapture.click()
  driver.sleep(3000)
   })
   } catch (err)
     { }
  await driver.sleep(2000)
};

export const selectSuits = async () => {
    let currentUrl = await driver.getCurrentUrl();
    if (currentUrl.indexOf("factory.jcrew.com") > -1) {
      console.log(">>>.. inside factory" + currentUrl.indexOf("factory.jcrew.com"))
      await driver.findElement(By.xpath("//span[text()='menu']")).click()
      await driver.sleep(1000)
      await driver.findElement(By.xpath("//a[@data-department='men']")).click()
        await	driver.sleep(1000);
      await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//a[@data-department='thompson suits & blazers']")));
      await	driver.sleep(1000);
        await driver.findElement(By.xpath("//a[@data-department='thompson suits & blazers']")).click()
        await driver.sleep(1000)
   } else {
     await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
     await driver.sleep(2000)
     // Category link
     await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
     await	driver.sleep(4000);
     await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//li[text()='suits & tuxedos']")));
     await	driver.sleep(1000);
     driver.findElement(By.xpath("//li[text()='suits & tuxedos']")).click()
     await driver.sleep(1000)
 }
  try {
          await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
          closeIcon.click()
          driver.sleep(1000)
         })
         } catch (err)
        { }
  };

export const selectExtendSizeCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(6000)
    await driver.findElement(By.xpath("//span[text()='menu']")).click()
    await driver.sleep(3000)
    await driver.findElement(By.xpath("(//a[@data-department='men'])[1]")).click()
    await	driver.sleep(3000);
    await driver.findElement(By.xpath("(//a[@data-department='new arrivals'])[2]")).click()
  } else {
  await driver.sleep(5000)
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  // Category link
  await driver.findElement(By.xpath("(//li[@class='hamburger-item'])[2]")).click()
  await	driver.sleep(2000);
  await driver.findElement(By.xpath("//li[text()='All Clothing']")).click()
  await	driver.sleep(2000);
  await driver.findElement(By.xpath("(//span[@class='tile__detail--alsoin'])[1]")).click()
}
};

export const addGiftCardToBag = async (giftcardName) => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    //Footer links are not displaying
  } else {
  await driver.sleep(2000)
  // Menu icon
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(By.xpath("//li[text()='Buy a Gift Card']")));
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//li[text()='Buy a Gift Card']")).click();
  await driver.sleep(1000)
  await closeIcon()
  await driver.sleep(1000)
  if(giftcardName=="classicGiftCard"){
    await driver.findElement(By.xpath("//a[@id='classicGiftCard']/img")).click();
  }else if(giftcardName=="eGiftCard"){
    await driver.findElement(By.xpath("//a[@id='eGiftCard']/img")).click();
  }
  await driver.sleep(2000)
  await driver.findElement(By.xpath("//*[@id='amount25']")).click()
  await driver.findElement(By.xpath("//*[@id='senderName']")).sendKeys("test")
  await driver.findElement(By.xpath("//*[@id='RecipientName']")).sendKeys("recipient test")
  await driver.findElement(By.xpath("//*[@id='text1Message']")).sendKeys("line 1")
  await driver.findElement(By.xpath("//*[@id='text2Message']")).sendKeys("line 2")
  //*[@id="submitClassic"]
  await driver.findElement(By.id("submitClassic")).click()
  await driver.sleep(1000)
  await driver.findElement(By.className("nc-mobile-nav__button bag")).click()
  await driver.sleep(1000)
}

};

export const selectCountry = async (contextchooser) => {
  await driver.sleep(1000)
  await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
  await driver.sleep(2000)
  await driver.executeScript('window.scrollTo(0, 5000)')
  await driver.sleep(1000)
  await driver.executeScript("arguments[0].scrollIntoView(true);",seeAllTopics);
};
