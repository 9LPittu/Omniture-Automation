import { driver, defaultTimeout } from '../helpersMobile';
import { globals } from '../jestJcrewQaMobileConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')


export const loginFromHomePage = async (username, password) =>{
  await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
  await driver.findElement(By.id("sidecarUser")).sendKeys(username)
  await driver.findElement(By.id("sidecarPassword")).sendKeys(password)
  await driver.findElement(By.className("btn--primary btn--signin js-button-submit")).click()
  console.log("Login success")
  driver.sleep(5000)
};

//*[@id="c-header__userpanel"]/a/span[1]

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
  await driver.sleep(5000)
  await driver.get(globals.__baseUrl__+"/CleanPersistentCart.jsp")
}
