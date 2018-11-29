import { until } from 'selenium-webdriver';
import { driver, defaultTimeout_short } from '../helpers';
import { globals } from '../jestJcrewQaConfig';

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
const createPassword = By.xpath("//input[@placeholder='CREATE YOUR PASSWORD']");
const joinNowLink = By.xpath("//*[text()='Join Now']");
const myAccountLink = By.xpath("//*[contains(text(),'My Account')][1]");
const myAccountText = By.xpath("//span[text()='My Account']");
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
  await driver.sleep(1000)
  let footerElement = await driver.findElement(footer_country_link);
  await driver.executeScript("arguments[0].scrollIntoView(true);", footerElement);
  await driver.sleep(1000)
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
  await driver.executeScript('window.scrollTo(0, 20000)')
  await driver.sleep(2000)
  await driver.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(orderStatus));
  await driver.sleep(2000)
  expect(driver.findElement(orderStatus)).toBeTruthy()
  await driver.findElement(orderStatus).click()
  await driver.sleep(2000)
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
  await driver.sleep(2000)
}

export const loyaltyFQAValidation = async () => {
  await driver.executeScript('window.scrollTo(0, 50000)')
  await driver.sleep(2000)
  const rewardsLink = await driver.findElement(rewards)
  await driver.executeScript("arguments[0].scrollIntoView(true);", rewardsLink);
  await driver.sleep(2000)
  expect(rewardsLink).toBeTruthy()
  //verify sign up now link
  const signUpNowLink = await driver.findElement(signUpNow);
  expect(signUpNowLink).toBeTruthy()
  await signUpNowLink.click()
  await driver.sleep(2000)
  let loginurl = await driver.getCurrentUrl();
  expect(loginurl.includes("/r/login")).toBeTruthy()
  console.log("user navigated to login page");
  await driver.navigate().back()
  await driver.sleep(2000)
  const FAQs = await driver.findElement(FAQLink);
  expect(FAQs).toBeTruthy()
  FAQs.click()
  await driver.sleep(4000)
  let faqurl = await driver.getCurrentUrl();
  expect(faqurl.includes("/l/rewards")).toBeTruthy()
  console.log("user navigated to FAQs page");
}

export const loyaltyLightBoxValidation = async () => {
  var x = Math.floor((Math.random() * 99900000) + 1);
  let userName = "LoyaltyTest" + x
  let email = userName + x + "@gmail.com"
  await driver.findElement(globalEmail).sendKeys(email)
  await driver.findElement(globalEmailSendBtn).click();
  driver.sleep(2000)
  let currentUrl = await driver.getCurrentUrl();
  if (currentUrl.indexOf("factory.jcrew.com") > -1) {
    await driver.findElement(createPassword).sendKeys("123456Ab");
  } else {
    await driver.findElement(createPassword).sendKeys("123456Ab");
  }
  driver.sleep(1000)
  await driver.findElement(joinNowLink).click();
  driver.sleep(15000)
  console.log("Loyalty User created from lightbox >> " + email)
  const myaccount = await driver.findElement(myAccountLink)
  expect(myaccount).toBeTruthy()
}

export const loyaltyMyAccountPageValidation = async () => {
  await driver.sleep(10000)
  let url = await driver.getCurrentUrl();
  if (url.includes("l/account/rewards")) {
    expect(url.includes("l/account/rewards")).toBeTruthy()
    console.log("User navigated to my account page")
  }
  //verify my account link
  const myaccount = await driver.findElement(myAccountText)
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

export const loyaltyPointsRedeemValidation = async (currentUrl) => {
  await driver.sleep(1000)
  await driver.navigate().to(globals.__baseUrl__ + "/checkout2/shoppingbag.jsp?sidecar=true")
  await driver.sleep(1000)
  try {
    await driver.findElement(redeemPoints).click()
    await driver.sleep(2000)
  } catch (err) { }
  await driver.findElement(buttonCheckout).click()
  //TODO - need to add the redeem assertion logic
  await driver.sleep(5000)
  await mergeButton();
  await driver.sleep(2000)
  let subTotalOnReview = await driver.findElement(summary_subtotal_clearfix).getText();
  let shippingOnReview = await driver.findElement(summary_shipping_clearfix).getText();
  let taxOnReview = await driver.findElement(summary_item_clearfix).getText();
  let totalOnReview = await driver.findElement(summary_total_clearfix).getText();
}

export const mergeButton = async () =>{
  try {
    await driver.findElement(mergeBtnEle).then(mergebtn => {
    // console.log("inside merge page")
     mergebtn.click()
     driver.sleep(2000)
     driver.findElement(buttonCheckout).click()
     })
   } catch (err)
  { }
}

export const searchBoxValidation = async () =>{
  expect(await driver.findElement(pageSearch).isDisplayed()).toBeTruthy();
}
 