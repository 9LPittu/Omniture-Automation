import { driver, defaultTimeout } from '../helpers';
import { globals } from '../jestJcrewQaConfig';

const { Builder, By, Key, until } = require('selenium-webdriver');
/**

**/
export const goToShoppingBag = async () => {
   await waitSeconds(5)
  await driver.findElement(By.xpath("//*[@class='icon-header icon-header-search icon-search']")).click();
  await driver.findElement(By.xpath("//*[@id='inputSearchDesktop']")).sendKeys("shirts");
  await driver.findElement(By.xpath("//*[@class='primary-nav__text primary-nav__text--search primary-nav__text--move-right']")).click();
  //   await   driver.findElement(By.xpath("//span[@class='btn__label' and text()='SMALL']")).click();
   await waitSeconds(2)
  await driver.findElement(By.xpath("(//div[@class='c-product__photos'])[1]")).click()
   await waitSeconds(5)
  await driver.findElement(By.xpath("(.//li[contains(@class,'js-product__size sizes-list__item btn') and not(contains(@class,'is-unavailable'))])[1]")).click()
  //await   driver.findElement(By.xpath("//span[@class='btn__label' and text()='6']")).click();
   await waitSeconds(2)
  await driver.findElement(By.xpath("//button[@id='btn__add-to-bag-wide']")).click();
   await waitSeconds(5);
  await driver.findElement(By.xpath("//a[@id='js-header__cart']")).click();
   await waitSeconds(3);
  expect(await driver.findElement(By.xpath("//*[@id='summary-promo-header']")).isDisplayed()).toBeTruthy();
  // await   driver.findElement(By.xpath("//*[@id='summary-promo-header']")).isDisplayed();
  await driver.findElement(By.xpath("//*[@id='summary-promo-header']")).click();


  expect(await driver.findElement(By.xpath("//*[@id='summary-gift-card-header']")).isDisplayed()).toBeTruthy();
  //   await driver.findElement(By.xpath("//*[@id='summary-gift-card-header']")).isDisplayed();
  await driver.findElement(By.xpath("//*[@id='summary-gift-card-header']")).click();

  expect(await driver.findElement(By.xpath("//*[@class='help-modules clearfix']")).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(By.partialLinkText('what is your return policy?')).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(By.partialLinkText('when can i expect my order?')).isDisplayed()).toBeTruthy();
  expect(await driver.findElement(By.xpath("//*[@class='c-paypal-button']")).isDisplayed()).toBeTruthy();
  // await driver.findElement(By.xpath("//*[@class='help-modules clearfix']")).isDisplayed();
  //
  // // await driver.findElement(By.partialLinkText('what is your return policy?')).isDisplayed();
  // // await driver.findElement(By.partialLinkText('when can i expect my order?')).isDisplayed();
  // // await driver.findElement(By.xpath("//*[@class='c-paypal-button']")).isDisplayed();
  //     await driver.findElement(By.id("button-checkout")).click();



};
/**

**/
export const clickOnCheckout = async () => {
  await driver.findElement(By.id("button-checkout")).click();

}
/**

**/
export const loginInAfterCheckoutPage = async () => {
  let inputEmail = await driver.findElement(By.id("loginUser"))
  let inputPassword = await driver.findElement(By.id("loginPassword"))
  let signInHere = await driver.findElement(By.partialLinkText("Sign In & Check Out"));//a[@class='button-general button-submit']"))
  inputEmail.sendKeys(loginDetails.username)//"testuser1@example.org")
  inputPassword.sendKeys(loginDetails.password)//"test1234")
  signInHere.click()
  //expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
};


export const loginAsGuestButton = async () => {
  //Check Out as a Guest
  await driver.findElement(By.xpath("//form[@id='frmGuestCheckOut']/a")).click();
}
export const addAddress = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#firstNameSA")).sendKeys("Auto Tester1 FN");
  await driver.findElement(By.css("#lastNameSA")).sendKeys("Auto Tester1 LN");
  await driver.findElement(By.css("#address3")).sendKeys("Nothing");
  await driver.findElement(By.css("#address1")).sendKeys("44 building-lvl 45");
  await driver.findElement(By.css("#zipcode")).sendKeys("50009");
  await driver.findElement(By.css("#phoneNumSA")).sendKeys("9658742361");
  let temp = await driver.findElement(By.css(".form-value")).getText();
  console.log(temp);
  await driver.findElement(By.css("#sameBillShip")).click();
}
export const guestUserFirstName = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#firstNameSA")).sendKeys("Auto Tester1 FN");
}
export const guestUserLastName = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#lastNameSA")).sendKeys("Auto Tester1 LN");
}
export const guestUserCompany = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#address3")).sendKeys("Nothing");
}
export const guestUserAddress = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#address1")).sendKeys("44 building-lvl 45");
}
export const guestUserZipCode = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#zipcode")).sendKeys("50009");
}
//phoneNumSA

export const guestUserPhoneNumber = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#phoneNumSA")).sendKeys("9658742361");
}
export const guestUserCountry = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css(".form-value")).getText();
}
export const guestUserBillingAddressAlsoCheckbox = async () => {
  //Check Out as a Guest
  await driver.findElement(By.css("#sameBillShip")).click();
}

export const mergeButton = async () => {
  try {
    await driver.findElement(By.xpath("//*[@id='mergedCartActionTop']/a[1]")).then(mergebutton => {
      console.log("inside merge page")
      mergebutton.click()
      driver.sleep(1000)
      driver.findElement(By.xpath("//*[@id='button-checkout']")).click()
      console.log("inside merge page2")
    })
  } catch (err) {
     console.log("in merge button error::::::::"+err)
   }
}
