import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')


export const loginFromHomePage = async (username, password) =>{
  let currentUrl = await driver.getCurrentUrl();
  if(currentUrl.includes("factory")){
    await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
  }else{
    await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
    driver.sleep(2000)
    await driver.findElement(By.xpath("//h3[text()='Sign in']")).click()
  }
  driver.sleep(2000)
  await driver.findElement(By.id("sidecarUser")).sendKeys(username)
  await driver.findElement(By.id("sidecarPassword")).sendKeys(password)
  await driver.findElement(By.className("btn--primary btn--signin js-button-submit")).click()
  console.log("Login success")
  driver.sleep(5000)
};

export const loginInAfterCheckoutPage = async (username, password)=>{
  let inputEmail =   await driver.findElement(By.id("loginUser"))
  let inputPassword =   await driver.findElement(By.id("loginPassword"))
  let signInHere = await driver.findElement(By.xpath("//a[@class='button-general button-submit']"))
  inputEmail.sendKeys(username)//"testuser1@example.org")
  inputPassword.sendKeys(password)//"test1234")
  signInHere.click()
  expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
}

export const clearBagItems = async ()=> {
  await driver.sleep(2000)
  await driver.get(globals.__baseUrl__+"/CleanPersistentCart.jsp")
}



export const signOutFromApplication = async ()=> {
let currentUrl = await driver.getCurrentUrl();
try {
  await driver.findElement(By.xpath("//*[@id='global__email-capture']/section/div[3]/span")).then(closeIcon => {
  closeIcon.click()
  driver.sleep(1000)
 })
 } catch (err)
{ }
if (currentUrl.indexOf("factory.jcrew.com") > -1) {
await driver.sleep(10000)
// Rewards
await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav")).click()
await driver.sleep(2000)
const signOut = await driver.findElement(By.xpath("//*[@id='page__account']/div/div[2]/nav/ul/li[9]/a"))
expect(signOut).toBeTruthy()
signOut.click()
await driver.sleep(1000)
await driver.executeScript('window.scrollTo(0, 1000)')
await driver.sleep(3000)
const signIn = await driver.findElement(By.xpath(".//span[text()='sign in']"))
expect(signIn).toBeTruthy()
} else {
await driver.sleep(10000)
await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
driver.sleep(2000)
const myaccount = await driver.findElement(By.xpath("//button[@class='hamburger-item']/h3"))
expect(myaccount).toBeTruthy()
await driver.sleep(1000)
// Rewards
myaccount.click()
await driver.sleep(2000)
const signOutJ = await driver.findElement(By.xpath("//li[text()='Sign Out']"))
expect(signOutJ).toBeTruthy()
signOutJ.click()
await driver.sleep(1000)
await driver.findElement(By.xpath("//button[@class='nc-mobile-nav__button hamburger']")).click()
await driver.sleep(2000)
const signIn = await driver.findElement(By.xpath("//h3[text()='Sign in']"))
expect(signIn).toBeTruthy()
console.log("user is able to sign out")
}

};
