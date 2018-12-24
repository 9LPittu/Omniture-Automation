import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/commonutils';

const each = require('jest-each')
const { Builder, By, Key } = require('selenium-webdriver')


//Factory
const contextMenu_Factory = By.xpath("//span[text()='menu']");
const menCat_Factory = By.xpath("(//a[@data-department='men'])[1]");
const newArrivals_Factory = By.xpath("(//a[@data-department='new arrivals'])[2]");

//Jcrew
const contextMenu_Jcrew = By.className("nc-mobile-nav__button hamburger");
const womenCat_Jcrew = By.xpath("//li[@class='hamburger-item']/h2[text()='Women']");
const newArrivals_Jcrew = By.xpath("//li[text()='New Arrivals']");


const arraypage = By.xpath("//div[@class='product__list']");
const productimage = By.xpath("(//div[@class='c-product__photos'])[1]");
const producttitle = By.xpath("(//span[contains(@class,'tile__detail--name')])[1]");
const productprice = By.xpath("(//span[contains(@class,'tile__detail--price')])[1]");
const colorSwatchProduct = By.xpath("//span[text()='available in']/../span[@class='tile__detail tile__detail--name']");
const colorSwatch = By.xpath("(//img[@class='colors-list__image'])[2]");
const customersAlsoLove = By.xpath("//h3[text()='Customers Also Love']");
const productRecommendations = By.xpath("//li[contains(@class,'c-product-recommendations-tile')]");
const alsoIn = By.xpath("(//span[@class='tile__detail--alsoin'])[1]");
const variationsList = By.className("variations-list-wrap");
const filter = By.xpath("//button[text()='Filter']");
const sortBy = By.xpath("//span[text()='Sort By']");
const lowToHigh = By.xpath("//li[text()='Price: Low to High']");
const sortResult = By.xpath("//span[text()='Price: Low to High']");
const filterBy = By.xpath("//h5[text()='Filter By']");
const category = By.xpath("//span[text()='Category']");
const categoryOption = By.xpath("(//span[@class='refinement--label__checkbox'])[1]");
const selectedOption = By.xpath("//div[text()='1 selected']");
const clearAll = By.id("btn__c-filters--clear");
const done = By.xpath("//button[text()='Done']");
const justIn = By.xpath("//div[@class='c-filters__breadcrumb btn btn--round is-capitalized']/span[1]");
const clearFilteredOption = By.xpath("//span[text()='Clear All']");


// for selecting the Category
export const selectCategory = async () => {
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com")) {
    // Menu icon
    await driver.findElement(contextMenu_Factory).click();
    await driver.findElement(menCat_Factory).click();
    await driver.findElement(newArrivals_Factory).click();
  } else {
    // Menu icon
    await driver.findElement(contextMenu_Jcrew).click();
    // Category link
    await driver.findElement(womenCat_Jcrew).click();
    // New arrivals
    await driver.findElement(newArrivals_Jcrew).click();
};
export const validateAyyayPage = async () => {
    expect(driver.findElement(arraypage)).toBeTruthy();
    expect(driver.findElement(productimage)).toBeTruthy();
    expect(driver.findElement(producttitle)).toBeTruthy();
    expect(driver.findElement(productprice)).toBeTruthy();
};

export const verifyColorSwatch = async () => {
    expect(driver.findElement(colorSwatchProduct)).toBeTruthy();
    await driver.findElement(colorSwatchProduct).click();
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(colorSwatch));
    expect(driver.findElement(colorSwatch)).toBeTruthy();
};

export const verifyProductRecommendations = async () => {
    await driver.findElement(producttitle).click();
    await driver.executeScript('window.scrollTo(0, 1000)');
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(customersAlsoLove));
    expect(customersalsolike).toBeTruthy();
    expect(driver.findElement(productRecommendations)).toBeTruthy();
};

export const verifyExtendedSize = async () => {
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(alsoIn));
    expect(driver.findElement(alsoIn)).toBeTruthy();
    await driver.findElement(alsoIn).click();
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(variationsList));
    expect(driver.findElement(variationsList)).toBeTruthy();
};

export const verifyFilterAndSortFunction = async => {
      expect(driver.findElement(filter)).toBeTruthy();
      expect(driver.findElement(sortBy)).toBeTruthy();
      console.log("Verifying sort by functionality")
      await driver.findElement(sortBy).click();
      expect(driver.findElement(lowToHigh)).toBeTruthy();
      await driver.findElement(lowToHigh).click();
      expect(driver.findElement(sortResult)).toBeTruthy();
      console.log("User is able to sort the items")
      await driver.findElement(filter).click();
      expect(driver.findElement(filterBy)).toBeTruthy();
      expect(driver.findElement(category)).toBeTruthy();
      await driver.findElement(categoryOption).click();
      await waitSeconds(5);
      expect(driver.findElement(selectedOption)).toBeTruthy();
      expect(driver.findElement(clearAll)).toBeTruthy();
      await driver.findElement(clearAll).click();
      await driver.findElement(categoryOption).click();
      await driver.findElement(done).click();
      expect(driver.findElement(justIn)).toBeTruthy();
      expect(driver.findElement(clearFilteredOption)).toBeTruthy()
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


};
