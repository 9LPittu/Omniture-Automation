import { driver, defaultTimeout } from '../helpersMobile';
import { ExpressUser,NonExpressUser} from '../testdata/SanityP0';
import { globals } from '../jestJcrewQaConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')


export const loginFromHomePage = async () =>{
  await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
  await driver.findElement(By.id("sidecarUser")).sendKeys(NonExpressUser.username)
  await driver.findElement(By.id("sidecarPassword")).sendKeys(NonExpressUser.password)
  await driver.findElement(By.className("btn--primary btn--signin js-button-submit")).click()
  console.log("Login success")
  driver.sleep(5000)
};

export const loginInAfterCheckoutPage = async ()=>{
  let inputEmail =   await driver.findElement(By.id("loginUser"))
  let inputPassword =   await driver.findElement(By.id("loginPassword"))
  let signInHere = await driver.findElement(By.xpath("//a[@class='button-general button-submit']"))
  inputEmail.sendKeys(NonExpressUser.username)//"testuser1@example.org")
  inputPassword.sendKeys(NonExpressUser.password)//"test1234")
  signInHere.click()
  expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
}

export const clearBagItems = async ()=> {
  await driver.sleep(5000)
  await driver.get(globals.__baseUrl__+"/CleanPersistentCart.jsp")
}
