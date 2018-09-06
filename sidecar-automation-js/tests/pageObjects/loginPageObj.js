import { driver, defaultTimeout } from '../helpers';
import { globals } from '../jestJcrewQaConfig';

const { Builder, By, Key, until } = require('selenium-webdriver')


export const loginFromHomePage = async (username,password) =>{
  let url = await driver.getCurrentUrl()
  if(url.indexOf("https://factory.jcrew.com") > -1){
  await driver.findElement(By.xpath(".//span[text()='sign in']")).click()
}else{
  await driver.findElement(By.xpath("//a[text()='Sign In']")).click()
}
await driver.sleep(2000)
await driver.findElement(By.id("sidecarUser")).sendKeys(username)
await driver.findElement(By.id("sidecarPassword")).sendKeys(password)
//await driver.sleep(1000)
await driver.findElement(By.xpath("//button[@class='btn--primary btn--signin js-button-submit']")).click()
console.log("Login success")
driver.sleep(2000)
};

export const loginInAfterCheckoutPage = async (username,password)=>{
  let inputEmail =   await driver.findElement(By.id("loginUser"))
  let inputPassword =   await driver.findElement(By.id("loginPassword"))
  let signInHere = await driver.findElement(By.xpath("//a[@class='button-general button-submit']"))
  inputEmail.sendKeys(username)//"testuser1@example.org")
  inputPassword.sendKeys(password)//"test1234")
  signInHere.click()
  //expect(await driver.getTitle()).toMatch('J.Crew - Sign In')
  console.log('sign in Done')
}

export const clearBagItems = async ()=> {

  let url = await driver.getCurrentUrl();
  await driver.sleep(5000)
  if (url.indexOf("www.jcrew.com") > -1) {
    await driver.get("https://www.jcrew.com/CleanPersistentCart.jsp")
  }else if(url.indexOf("or.jcrew.com") > -1){
    await driver.get("https://or.jcrew.com/CleanPersistentCart.jsp")
  }else if(url.indexOf("or.factory.jcrew.com") > -1){
    await driver.get("https://or.factory.jcrew.com/CleanPersistentCart.jsp")
  }else if(url.indexOf("https://factory.jcrew.com") > -1){
    await driver.get("https://factory.jcrew.com/CleanPersistentCart.jsp")
  }

}

export const createNewAccount = async ()=> {
var x = Math.floor((Math.random() * 1000000) + 1);
 let userName = "AutomationTest"+x
 let email = "AutomationTest"+x+"@gmail.com"
    await driver.navigate().to(globals.__baseUrl__+"/r/login")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterFirstName']")).sendKeys(userName)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterLastName']")).sendKeys("tester")
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterEmail']")).sendKeys(email)
    await driver.findElement(By.xpath("//*[@id='sidecarRegisterPassword']")).sendKeys("nft123")
    await driver.findElement(By.xpath("//*[@id='page__signin']/article/section[2]/div/form/button")).click()
    await driver.sleep(10000)
    let url = await driver.getCurrentUrl()
    if(url.includes("factory")){
    const loggedInUser = await driver.findElement(By.id("c-header__userpanelrecognized"))
    expect(loggedInUser).toBeTruthy()
  }else{
    const loggedInUser = await driver.findElement(By.xpath("//a[@class='nc-nav__account_button']"))
    expect(loggedInUser).toBeTruthy()
  }
    console.log("User is able to create new account")
    console.log ("User created >> " + email)
}
