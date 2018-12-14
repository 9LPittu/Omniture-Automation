import { until } from 'selenium-webdriver';
import { driver, defaultTimeout_short } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import { waitSeconds } from '../util/commonutils';

const { Builder, By, Key } = require('selenium-webdriver')
const closeIcon = { xpath: "(//span[@class='icon-close'])[1]" };
const privacyPolicyClose = { xpath: "//div[@class='mt-close-lb-slide privacyPolicyClose']" };
const signInIcon = { xpath: "//*[@id='c-header__userpanel']/a/span[2]" };
const footer_country_link = By.xpath("//a[@class='footer__country-context__link']");
const footer_page_international = By.xpath("//*[@id='page__international']/article/section[1]/div/div[2]/div/div[1]/ul/li[2]/button");

//Footer elements
const orderStatus = By.xpath("//div[text()='Order Status']");
const globalFooter = By.id('global__footer');
const country_contextLink = By.xpath("//a[@class='footer__country-context__link']");
const countryCanada = By.xpath("//span[text()='Canada']");
const rewards = By.xpath("//h6[text()='J.CREW REWARDS']");
const signUpNow = By.xpath("//div[text()='Sign Up Now']");
const FAQLink = By.xpath("//div[text()='FAQs']");
const globalEmail = By.xpath("//*[@id='global__email-capture']/section/div[1]/form/fieldset/input[1]");
const globalEmailSendBtn = By.xpath("//*[@id='global__email-capture']/section/div[1]/form/fieldset/button/span[2]");
const shopNowLink = By.xpath("(//BUTTON[@type='button'][text()='Shop now'])[1]");
const createPassword = By.xpath("//input[@placeholder='CREATE YOUR PASSWORD']");
const joinNowLink = By.xpath("//*[text()='Join Now']");
const myAccountLink = By.xpath("//*[contains(text(),'My Account')][1]");
const myAccountFactoryText = By.xpath("//span[text()='My Account']");
const myAccountJcrewText = By.xpath("//*[@class='nc-nav__account_button']");
const myRewardsLink = By.xpath("//li/a[text()='My Rewards']");
const yourJcrewRewards = By.xpath("//div[text()='Your J.Crew Rewards']");
const yourBenifits = By.xpath("//div[text()='Your Benefits']");
const yourActivity = By.xpath("//div[text()='Your Activity']");
const redeemPoints = By.xpath("//*[@id='btn-redeem-points']");
const buttonCheckout = By.xpath("//*[@id='button-checkout']");
const summary_subtotal_clearfix = By.xpath("//ul/li[@class='summary-item summary-subtotal clearfix']/span[2]");
const summary_shipping_clearfix = By.xpath("//ul/li[@class='summary-item summary-shipping clearfix']/span[2]");
const summary_item_clearfix = By.xpath("//ul/li[@class='summary-item clearfix']/span[2]");
const summary_total_clearfix = By.xpath("//ul/li[@class='summary-item summary-total clearfix']/span[2]");
const mergeBtnEle = By.xpath("//*[@id='mergedCartActionTop']/a[1]");
const pageSearch = By.id("page__search");

//Nav bar elements
const dep_nav_list = By.xpath("//ul[@class='department-nav__list']");
const nav_dep = By.xpath("//ul[@class='nc-nav__departments']");
const global_promo = By.id("global__promo");
const nc_promo = By.className("nc-promo");
const primary_nav = By.className("primary-nav__text primary-nav__text--stores");
const store_locator = By.xpath("//div[text()='Store Locator']");
const searchInput = By.id("inputSearchDesktop");
const search_input = By.xpath("//input[@class='nc-nav__search__input']");
const men = By.xpath("//a[text()='Men']");
const nav_fliyout_list = By.xpath("//li[@class='nc-nav__flyout-link__wrapper nc-nav__list-item']");
const navDeptHeader = By.className("c-header__department-nav js-header__department-nav")

export const load = async () => {
  await driver.get(`${__baseUrl__}/`);
  await closeIconInPAP();
};

export const loadLoginUrl = async () => {
  await driver.get(globals.__baseUrl__ + "/r/login")
};

export const closeIconInPAP = async () => {
  try {
    await driver.wait(until.elementLocated(closeIcon), defaultTimeout_short).click();
  } catch (err) {
    console.log("no close icon" + err);
  }
  try {
    await driver.wait(until.elementLocated(privacyPolicyClose), defaultTimeout_short).click();
    console.log("privacy policy icon closed")
  } catch (err) {
    console.log("in catch email popup close");
  }
};

export const differentRegionsValidation = async () => {
  let result = false;
  await driver.executeScript('window.scrollTo(0, 10000)')
  await waitSeconds(1)
  let footerElement = await driver.findElement(footer_country_link);
  await driver.executeScript("arguments[0].scrollIntoView(true);", footerElement);
  await waitSeconds(1)
  await footerElement.click()
  await closeIconInPAP()
  await driver.findElement(footer_page_international).click()
  let url = await driver.getCurrentUrl();
  if (url.indexOf("ca") > -1) {
    console.log('user is on Canada context')
    result = true;
  }
  return result;
};

export const orderStatusValidation = async () => {
  await driver.executeScript('window.scrollTo(0, 15000)')
  await waitSeconds(2)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(orderStatus));
  await waitSeconds(2)
  expect(driver.findElement(orderStatus)).toBeTruthy()
  await driver.findElement(orderStatus).click()
  await waitSeconds(2)
  await driver.getCurrentUrl(url => {
    expect(url.match('/help/order_status.jsp?sidecar=true')).toBeTruthy()
  })
}

export const locationUrlsValidation = async () => {
  const footer = await driver.findElement(globalFooter)
  const location = await footer.findElement(footer_country_link)
  await expect(location).toBeTruthy()
  await location.click()
  await driver.getCurrentUrl(url => {
    expect(url).toMatch('r/context-chooser')
  })

  await driver.findElement(countryCanada).click()
  await waitSeconds(2)
}

export const loyaltyFQAValidation = async () => {
  await driver.executeScript('window.scrollTo(0, 50000)')
  await waitSeconds(2)
  const rewardsLink = await driver.findElement(rewards)
  await driver.executeScript("arguments[0].scrollIntoView(true);", rewardsLink);
  await waitSeconds(2)
  expect(rewardsLink).toBeTruthy()
  //verify sign up now link
  const signUpNowLink = await driver.findElement(signUpNow);
  expect(signUpNowLink).toBeTruthy()
  await signUpNowLink.click()
  await waitSeconds(2)
  let loginurl = await driver.getCurrentUrl();
  expect(loginurl.includes("/r/login")).toBeTruthy()
  console.log("user navigated to login page");
  await driver.navigate().back()
  await waitSeconds(2)
  const FAQs = await driver.findElement(FAQLink);
  expect(FAQs).toBeTruthy()
  FAQs.click()
  await waitSeconds(4)
  let faqurl = await driver.getCurrentUrl();
  expect(faqurl.includes("/l/rewards")).toBeTruthy()
  console.log("user navigated to FAQs page");
}

export const loyaltyLightBoxValidation = async () => {
  var x = Math.floor((Math.random() * 99900000) + 1);
  let userName = "LoyaltyTest" + x
  let email = userName + x + "@gmail.com"
  // await driver.findElement(globalEmail).sendKeys(email)
  //await driver.findElement(globalEmailSendBtn).click();
  await waitSeconds(2)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(globalEmail).sendKeys(email)
    await driver.findElement(globalEmailSendBtn).click();
    await driver.findElement(createPassword).sendKeys("123456Ab");
  } else if (currentUrl.indexOf("jcrew.com") > -1) {
    await driver.findElement(shopNowLink).click();
    await driver.findElement(globalEmail).sendKeys(email)
    await driver.findElement(globalEmailSendBtn).click();
    await driver.findElement(createPassword).sendKeys("123456Ab");
  }
  await waitSeconds(1)
  await driver.findElement(joinNowLink).click();
  await waitSeconds(15)
  console.log("Loyalty User created from lightbox >> " + email)
  const myaccount = await driver.findElement(myAccountLink)
  expect(myaccount).toBeTruthy()
}

export const loyaltyMyAccountPageValidation = async () => {
  await waitSeconds(10)
  let url = await driver.getCurrentUrl();
  if (url.includes("l/account/rewards")) {
    expect(url.includes("l/account/rewards")).toBeTruthy()
    console.log("User navigated to my account page")
  }
  //verify my account link

  if (url.indexOf("factory.jcrew.com") > -1) {
    const myaccount = await driver.findElement(myAccountFactoryText)
    expect(myaccount).toBeTruthy()
    const myrewards = await driver.findElement(myRewardsLink)
    expect(myrewards).toBeTruthy()
    const yourrewardstab = await driver.findElement(yourJcrewRewards)
    expect(yourrewardstab).toBeTruthy()
    const yourbenefitstab = await driver.findElement(yourBenifits)
    expect(yourbenefitstab).toBeTruthy()
    const youractivitytab = await driver.findElement(yourActivity)
    expect(youractivitytab).toBeTruthy()
  }
  else if (url.indexOf("jcrew.com") > -1) {
    const myaccount = await driver.findElement(myAccountJcrewText)
    expect(myaccount).toBeTruthy()
    const myrewards = await driver.findElement(myRewardsLink)
    expect(myrewards).toBeTruthy()
    const yourrewardstab = await driver.findElement(yourJcrewRewards)
    expect(yourrewardstab).toBeTruthy()
    const yourbenefitstab = await driver.findElement(yourBenifits)
    expect(yourbenefitstab).toBeTruthy()
    const youractivitytab = await driver.findElement(yourActivity)
    expect(youractivitytab).toBeTruthy()
  }
}
export const loyaltyPointsRedeemValidation = async (currentUrl) => {
  await waitSeconds(1)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await waitSeconds(1)
  try {
    await driver.findElement(redeemPoints).click()
    await waitSeconds(2)
  } catch (err) { }
  await driver.findElement(buttonCheckout).click()
  //TODO - need to add the redeem assertion logic
  await waitSeconds(5)
  await mergeButton();
  await waitSeconds(2)
  let subTotalOnReview = await driver.findElement(summary_subtotal_clearfix).getText();
  let shippingOnReview = await driver.findElement(summary_shipping_clearfix).getText();
  let taxOnReview = await driver.findElement(summary_item_clearfix).getText();
  let totalOnReview = await driver.findElement(summary_total_clearfix).getText();
}

export const mergeButton = async () => {
  try {
    await driver.findElement(mergeBtnEle).then(mergebtn => {
      // console.log("inside merge page")
      mergebtn.click()
      driver.sleep(3000)
      driver.findElement(buttonCheckout).click()
    })
  } catch (err) { }
}

export const searchBoxValidation = async () => {
  expect(await driver.findElement(pageSearch).isDisplayed()).toBeTruthy();
}

export const navBarVisible = async (currentUrl) => {
  if (currentUrl.includes("factory")) {
    expect(await driver.findElement(dep_nav_list)).toBeTruthy()
    console.log("nav bar is displaying")
  } else {
    expect(await driver.findElement(nav_dep)).toBeTruthy()
    console.log("nav bar is displaying")
  }
}

export const globalPromoVisibleValidation = async (currentUrl) => {
  if (currentUrl.includes("factory")) {
    try {
      const promo = driver.findElement(global_promo)
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    } catch (err) { }
  } else {
    try {
      const promo = driver.findElement(nc_promo)
      expect(promo).toBeTruthy()
      console.log("Global promo is displaying")
    } catch (err) { }
  }
}

export const storeVisibilityValidation = async (currentUrl) => {
  if (currentUrl.includes("factory")) {
    const stores = driver.findElement(primary_nav)
    expect(stores).toBeTruthy()
    await stores.click()
    await driver.sleep(2000)
    await driver.getCurrentUrl(url => {
      expect(url.match('https://stores.jcrew.com')).toBeTruthy()
    })
    await driver.navigate().back()
    await driver.navigate().to(currentUrl)
    console.log("User navigated to stores page")
  } else {
    await driver.executeScript('window.scrollTo(0, 20000)')
    await driver.sleep(2000)
    await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(store_locator));
    await driver.sleep(1000)
    await driver.findElement(store_locator).click()
    await driver.sleep(1000)
    await driver.getCurrentUrl(url => {
      expect(url.match('https://stores.jcrew.com')).toBeTruthy()
    })
    await driver.navigate().back()
    await driver.navigate().to(currentUrl)
    console.log("User navigated to stores page")
  }
}

export const searchInputValidation = async (currentUrl) => {
  if (currentUrl.includes("factory")) {
    const search = await driver.findElement(searchInput)
    await expect(search).toBeTruthy()
    await driver.findElement(search).click()
    await search.sendKeys('shirts', Key.ENTER)
    await driver.sleep(2000)
    await driver.getCurrentUrl().then(url => {
      expect(url).toMatch(new RegExp('/r/search', 'i'))
    })
    await driver.navigate().back()
    await driver.navigate().to(currentUrl)
    console.log("User is able to search for items")
  } else {
    var searchText = await driver.findElement(search_input);
    await searchText.sendKeys("pants")
    await driver.sleep(1000)
    await driver.actions().click(searchText).sendKeys(Key.ENTER).perform();
    await driver.sleep(2000)
    await driver.getCurrentUrl().then(url => {
      expect(url).toMatch(new RegExp('/r/search', 'i'))
    })
    await driver.navigate().back()
    await driver.navigate().to(currentUrl)
    console.log("User is able to search for items")
  }
}

export const mouseHoverSubCategory = async (currentUrl) => {
  if (currentUrl.includes("factory")) {
  } else {
    await driver.actions().mouseMove(await driver.findElement(men)).perform();
    const ele = await driver.findElements(nav_fliyout_list)
    console.log(ele.length)
  }
}

export const allNavLinksValidation = async (currentUrl, link) => {
  if (currentUrl.includes("factory")) {
    try {
      const subnav = await driver.findElement(navDeptHeader)
      await subnav.findElement(By.xpath("//span[contains(@class, 'department-nav__text') and text() = '" + link + "']")).click()
      await driver.sleep(2000)
      await driver.getCurrentUrl().then(url => {
        const reg = new RegExp(link, 'i')
        expect(url.match(reg)).toBeTruthy()
      })
    } catch (err) {
      throw err
    }
  } else {
    await driver.findElement(By.xpath("//a[text()='" + link + "']")).click()
    await driver.sleep(2000)
    await driver.getCurrentUrl().then(url => {
      if (link == 'kids') {
        link = 'Girls';
      }
      const reg = new RegExp(link, 'i')
      expect(url.match(reg)).toBeTruthy()
    })
  }
}