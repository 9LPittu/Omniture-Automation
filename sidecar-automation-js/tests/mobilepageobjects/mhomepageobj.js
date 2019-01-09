import { until } from 'selenium-webdriver';
import { driver, defaultTimeout } from '../helpersMobile';
import { waitSeconds } from '../util/MobileMethods';
import { Contact_details } from '../testdata/jcrewTestData';

const { Builder, By, Key } = require('selenium-webdriver')

const contextMenu = By.className("nc-mobile-nav__button hamburger");
const closeIcon = By.xpath("//*[@id='global__email-capture']/section/div[3]/span");
const shipToCountry = By.xpath("//span[text()='Ship to']/../a");
const usAndCanadaLink = By.xpath("//*[text()='UNITED STATES & CANADA']");
const buyAGiftCard = By.xpath("//li[text()='Buy a Gift Card']");
const orderStatus = By.xpath("//li[text()='Order Status?']");
const ordNumSearch = By.name("ORDER_SEARCH<>orderNum_search");
const billingZIPCode = By.name("ORDER_SEARCH<>postal");
const findNewStore = By.xpath("//li[text()='Find a J.Crew Store']");
const jcrewGiftCard = By.xpath("//div[text()='The J.Crew Gift Card']");

//My details section
const userAccount = By.xpath("//div[@class='hamburger-account']/li");
const accountLink = By.xpath("//li[text()='Account']");
const myRewardsLink = By.xpath("//nav[@class='rewards--select']");
const myDetailsTxt = By.xpath("(//a[text()='My Details'])[1]");
const myDetailsFirstName = By.id('my-details-form__first-name');
const myDetailsLastName = By.id('my-details-form__last-name');
const myDetailsEmail = By.xpath("(//input[@type='email'])[1]");
const newArrivals = By.xpath("//li[text()='New Arrivals']");

//Location change
//const contextMenu = By.xpath("//button[@class='nc-mobile-nav__button hamburger']");
const canWeHelpLink = By.xpath("//h4[text()='How can we help?']");
const contactListLink = By.xpath("//ul[@class='contact-list']");
const changeLocation = By.xpath("//a[text()='change']");
const closeIcon_privacy = By.xpath("//div[@class='mt-close-lb-slide privacyPolicyClose']");
const countryLink = By.xpath("//div[@class='country-info']/a");
const USCanadaLink = By.xpath("//h5[text()='UNITED STATES & CANADA']");
const canadaTxt = By.xpath("//span[text()='Canada']");
const factory_navBar = By.xpath("//div[@class='header__primary-nav__inner']");
const jcrew_navBar = By.xpath("(//*[@class='mobile-spacer']/div)[1]");
const searchBar = By.xpath("//button[@class='nc-mobile-nav__button search']");
const searchBarPlaceHolder = By.xpath("//input[@placeholder='Search J.Crew']");
const productSizeDesc = By.xpath("//div[@class='product__size-fit product__description']");
const productDetailsDesc = By.xpath("//div[@class='product__details product__description']");
const productDetailsList = By.xpath("//div[@id='c-product__description']//following::ul[@class='bullet-list']");
const searchHeader_factory = By.xpath("//span[@class='icon-header icon-header-search icon-search']");
const searcInput_factory = By.xpath("//input[@class='js-header__search__input header__search__input']");
 
export const load = async () => {
  await driver.get(`${__baseUrl__}/`)
};

export const closeEmailCapturePopUp = async () => {
  try{
    await driver.findElement(closeIcon).click();
  }catch(err){}
};

export const scrollAndClickOnContextChooser = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 1000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(shipToCountry));
  await waitSeconds(1);
  await driver.findElement(shipToCountry).click();
  };

  export const selectAndVerifyCountry = async(contextchooser) => {
  expect(driver.findElement(usAndCanadaLink)).toBeTruthy();
  await driver.findElement(usAndCanadaLink).click();
  await driver.findElement(By.xpath("//span[text()='" + contextchooser +"']")).click();
  let url = await driver.getCurrentUrl();
  if (url.indexOf("ca") > -1) {
    console.log('user is on Canada context')
  }
};

export const selectAndVerifyOrderStatus = async () => {
    await driver.findElement(contextMenu).click();
    await driver.executeScript('window.scrollTo(0, 2000)');
    await waitSeconds(1);
    await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(orderStatus));
    await waitSeconds(1);
    await driver.findElement(orderStatus).click();
    await waitSeconds(1);
    let url = await driver.getCurrentUrl();
    if(url.includes("order_status")){
      expect(driver.findElement(ordNumSearch)).toBeTruthy();
      expect(driver.findElement(billingZIPCode)).toBeTruthy();
      console.log("User is able to navigated to Order status page");
    }
};

export const selectAndVerifyStoreLocator = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 2000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(findNewStore));
  await waitSeconds(1);
  await driver.findElement(findNewStore).click();
  await waitSeconds(1);
  let url = await driver.getCurrentUrl();
  if(url.includes("stores.")){
    console.log("User is able to navigated to Store Located page");
  }
};

export const goToGiftCardsPage = async () => {
  await driver.findElement(contextMenu).click();
  await driver.executeScript('window.scrollTo(0, 2000)');
  await waitSeconds(1);
  await driver.executeScript("arguments[0].scrollIntoView(true);",driver.findElement(buyAGiftCard));
  await waitSeconds(1);
  await driver.findElement(buyAGiftCard).click();
  await waitSeconds(1);
  await closeEmailCapturePopUp();
  let url = await driver.getCurrentUrl();
  if(url.includes("gift_card")){
    console.log("User is able to navigated to gift card page");
  }
};


export const goToMyDetailsAndValidate = async () =>{
  await driver.findElement(contextMenu).click();
  await driver.findElement(userAccount).click()
  await waitSeconds(2)
  await driver.findElement(accountLink).click()
  await waitSeconds(3)
  let url = await driver.getCurrentUrl()
  expect(url.indexOf("rewards")).toBeGreaterThan(-1)
  await driver.findElement(myRewardsLink).click()
  await waitSeconds(2)
  await driver.findElement(myDetailsTxt).click()
  await waitSeconds(2)
  url = await driver.getCurrentUrl()
  expect(url.indexOf("details")).toBeGreaterThan(-1)
  let firstNameTxt = await driver.findElement(myDetailsFirstName)
  expect(firstNameTxt).toBeTruthy()
  console.log("Logged in user first name is::"+await firstNameTxt.getAttribute("value"))
  let lastNameTxt = await driver.findElement(myDetailsLastName)
  expect(lastNameTxt).toBeTruthy()
  console.log("Logged in user Last name is::"+await lastNameTxt.getAttribute("value"))
  let emailTxt = await driver.findElement(myDetailsEmail)
  expect(emailTxt).toBeTruthy()
  console.log("Logged in email is::"+await emailTxt.getAttribute("value"))
}


export const validateContactList = async () =>{
  console.log("Validate contactList elements")
  await driver.findElement(contextMenu).click()
  await waitSeconds(2)
  await driver.executeScript('window.scrollTo(0, 5000)')
  await waitSeconds(1)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(canWeHelpLink));
  await waitSeconds(1)
  let contactList = await driver.findElement(contactListLink)
  let contactItems = await contactList.findElements(By.tagName("li"))
  let contactCount = contactItems.length;
  console.log("Atleast two contacts should be present along with twitter and phone number, number of contacts present are ::" + contactCount)
  expect(contactCount).toBeGreaterThanOrEqual(2)
  for (let i = 0; i < contactCount; i++) {
    switch (i) {
      case 0:
        expect(await contactItems[i].getText()).toContain(Contact_details.twitter)
        console.log("Twitter contact is present")
        break;
      case 1:
        expect(await contactItems[i].getText()).toContain(Contact_details.phoneNo)
        console.log("contact number is present")
        break;
    }
  }
}

export const validateOrderStatus = async () =>{
  const orderstatuslink = await driver.findElement(orderStatus)
  expect(orderstatuslink).toBeTruthy()
  await orderstatuslink.click()
  await waitSeconds(2)
  await driver.getCurrentUrl(url => {
    expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
  })
  await driver.navigate().back()
  await waitSeconds(2)
}

export const locationChangeValidation = async (currentUrl) =>{
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await waitSeconds(1)
    await driver.executeScript('window.scrollTo(0, 10000)')
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(changeLocation));
    await waitSeconds(1)
    await driver.findElement(closeIcon_privacy).click()
    await waitSeconds(1)
    const change = await driver.findElement(changeLocation)
    await expect(change).toBeTruthy()
    await change.click()
    await waitSeconds(1)
  } else {
    await waitSeconds(2)
    await driver.findElement(contextMenu).click()
    await waitSeconds(2)
    await driver.executeScript('window.scrollTo(0, 10000)')
    await waitSeconds(1)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(countryLink));
    await waitSeconds(1)
    const change = await driver.findElement(countryLink)
    await expect(change).toBeTruthy()
    await change.click()
    await waitSeconds(1)
  }
  await driver.getCurrentUrl(url => {
    expect(url).toMatch('r/context-chooser')
  })
  await waitSeconds(1)
  await driver.findElement(USCanadaLink).click()
  try {
    await driver.findElement(closeIcon).click()
  } catch (err) {

  }
  await driver.findElement(canadaTxt).click()
  await waitSeconds(2)
}

export const navBarValidationEachSection = async (link) =>{
  console.log("verifying links in each %s"+link)
  try {
    await load();
    await waitSeconds(2)
    await driver.findElement(contextMenu).click()
    await waitSeconds(2)
    await driver.findElement(By.xpath("//h2[text()='"+link+"']")).click()
    await waitSeconds(2)
    await driver.findElement(newArrivals).click()
    await waitSeconds(2)
    await driver.getCurrentUrl().then(url => {
      const reg = new RegExp(link, 'i')
      expect(url.match(reg)).toBeTruthy()
    })
  } catch (err) {
 
  }
}

export const navBarValidation = async () =>{
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    expect(await driver.findElement(factory_navBar)).toBeTruthy()
    console.log("verified navigation bar ")
  }
  else{
    expect(await driver.findElement(jcrew_navBar)).toBeTruthy()
    console.log("verified navigation bar ")
  }
}

export const searchFunctionalityValidation = async() =>{
  try {
    await driver.findElement(closeIcon).then(emailCapture => {
      console.log("Email capture page")
      emailCapture.click()
      driver.sleep(3000)
    })
  } catch (err) { }
  let currentUrlF = await driver.getCurrentUrl();
  if (currentUrlF.indexOf("factory.jcrew.com") > -1) {
    await driver.sleep(2000)
    const headerSearchF = await driver.findElement(searchHeader_factory)
    expect(headerSearchF).toBeTruthy()
    await headerSearchF.click()
    await driver.sleep(2000)
    // headerSearch.click();
    const searchInputF = await driver.findElement(searcInput_factory)
    expect(searchInputF).toBeTruthy()
    await searchInputF.clear();
    await driver.sleep(2000)
    await searchInputF.sendKeys("dress")
    searchInputF.sendKeys(Key.ENTER)
    let getCurrentUrl = await driver.getCurrentUrl()
    if (getCurrentUrl.indexOf("jcrew.com/r/search/") > -1) {
      console.log("Search results page is displayed")
    }
  }
  else {
    await driver.sleep(2000)
    const headerSearch = await driver.findElement(searchBar)
    expect(headerSearch).toBeTruthy()
    await headerSearch.click()
    await driver.sleep(2000)
    // headerSearch.click();
    const searchInput = await driver.findElement(searchBarPlaceHolder)
    expect(searchInput).toBeTruthy()
    await searchInput.clear();
    await driver.sleep(2000)
    await searchInput.sendKeys("dress")
    searchInput.sendKeys(Key.ENTER)
    let getCurrentUrl = await driver.getCurrentUrl()
    if (getCurrentUrl.indexOf("jcrew.com/r/search/") > -1) {
      console.log("Search results page is displayed")
    }
  } 
}

export const searchItemNumberValidation = async (itemno) =>{
  await waitSeconds(2)
  const headerSearch = await driver.findElement(searchBar)
  expect(headerSearch).toBeTruthy()
  await headerSearch.click()
  await waitSeconds(2)
  const searchInput = await driver.findElement(searchBarPlaceHolder)
  expect(searchInput).toBeTruthy()
  await searchInput.clear();
  await waitSeconds(2)
  await searchInput.sendKeys(itemno)
  await searchInput.sendKeys(Key.ENTER)
  await waitSeconds(2)
  //validations
  let itemUrl = await driver.getCurrentUrl()
  expect(itemUrl.indexOf(itemno)).toBeGreaterThan(-1)
  let sizeDescription = await driver.findElement(productSizeDesc)
  expect(sizeDescription).toBeTruthy()
  let productDetailsElement = await driver.findElement(productDetailsDesc)
  expect(productDetailsElement).toBeTruthy()
  await driver.executeScript("arguments[0].scrollIntoView(true);",sizeDescription);
  await waitSeconds(2)
  await productDetailsElement.click()
  await waitSeconds(1)
  let itemDesc = await driver.findElement(productDetailsList)
  expect(itemDesc).toBeTruthy()
  let itemDescText = await itemDesc.getText()
  expect(itemDescText.indexOf(itemno)).toBeGreaterThan(-1)
  console.log("PDP page opened for searched item number")
}


