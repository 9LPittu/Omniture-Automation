import { driver } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import {goToShoppingBag} from '../pageObjects/shoppingbagobj';
import {login,loginInAfterCheckoutPage} from '../pageObjects/loginpageobj';
import { Billing } from '../testdata/jcrewTestData';


const { Builder, By, Key, until } = require('selenium-webdriver');

const paypalRadio = () => driver.findElement({ css: "#paypalPayment" });
const paypalButton = () => driver.findElement({ xpath: "//div[@data-funding-source='paypal']" });
const loginButton = () => driver.findElement({ xpath: "//a[text()='Log In']" });
const email = () => driver.findElement({ css: "#email" });
const next = () => driver.findElement({ css: "#btnNext" });
const password = () => driver.findElement({ css: "#password" });
const btnLogin =() => driver.findElement({css:"#btnLogin" });
const continueButton = () => driver.findElement({ css: "#confirmButtonTop" });
const placeMyOrder = () => driver.findElement({css:"#button-submitorder"});
const orderPlaced = () => driver.findElement({xpath:"//img[@src='https://images.bizrateinsights.com/eval/survey/invite_template/custom/jcrew_714.jpg']"})
const closeOrderPlaced = () => driver.findElement({xpath : "//div[@class='brdialog-close']"});
const verifyOrderNumber = () => driver.findElement({xpath: "//span[@id='confirmation-number']/span[2]"})
////input[@type='submit']
const submitButton = () => driver.findElement({xpath: "//input[@type='submit']"})

export const paymentMethod = async(paymentType) =>{

    if(paymentType == "Credit/Debit_Card"){
      console.log(await driver.findElement(By.css("#creditDebitPayment")).isSelected())
        if(await driver.findElement(By.css("#creditDebitPayment")).isSelected()){

        let url = await driver.getCurrentUrl();
        if ((url.indexOf("www.jcrew.com") > -1) || (url.indexOf("factory.jcrew.com") > -1)) {
          await driver.findElement(By.css("#creditDebitPayment")).isDisplayed();
        }else if ((url.indexOf("or.jcrew.com") > -1 ) || (url.indexOf("or.factory.jcrew.com") > -1 )){
            let card = "//span[text()='"+Billing.Credit_Debit_Card.Name_On_Card+"']/parent::label/input[@class='address-radio']";
            console.log(card);
            await driver.findElement(By.xpath(card)).click();
            await driver.findElement(By.css("#order-summary__button-continue")).click();
            //#securityCode
            await driver.findElement(By.css("#securityCode")).sendKeys(Billing.Credit_Debit_Card.Security_Code);
            //button-submitorder
            let url = await driver.getCurrentUrl();
            console.log(url);
            await driver.findElement(By.css("#button-submitorder")).click();
            try {
            await driver.findElement(By.xpath("//img[@src='https://images.bizrateinsights.com/eval/survey/invite_template/custom/jcrew_714.jpg']")).isDisplayed();
            await driver.findElement(By.xpath("//div[@class='brdialog-close']")).click();
          } catch (err) {

          }
            let orderNumer = await driver.findElement(By.xpath("//span[@id='confirmation-number']/span[2]")).getText();
            console.log("Order Number is : "+orderNumer)
          }

        }else{
          await driver.findElement(By.css("#creditDebitPayment")).click();
        }
    }else if(paymentType == "Paypal"){
            //paypalPayment
          //  await driver.findElement(By.css("#paypalPayment")).click();
            //const css = { css: "#paypalPayment" };
            //*[@id="nav-billing"]/a
            await driver.findElement(By.xpath("//*[@id='nav-billing']/a")).click()
            var parent = driver.getWindowHandle();
            driver.sleep(6000)
            await driver.wait(until.elementLocated(paypalRadio), 50000).click();
            await driver.switchTo().frame(await driver.findElement(By.xpath("//iframe[@class='xcomponent-component-frame xcomponent-visible']")))
          //  await driver.findElement(By.xpath("//div[@data-funding-source='paypal']")).click();

          let url = await driver.getCurrentUrl();
          console.log(url);
          if (url.indexOf("www.jcrew.com") > -1) {
              await driver.wait(until.elementLocated(paypalButton), 50000).isDisplayed();
          }else{


            await driver.wait(until.elementLocated(paypalButton), 50000).click();
            await driver.switchTo().defaultContent();

            await driver.getAllWindowHandles().then(function gotWindowHandles(allhandles) {
                driver.switchTo().window(allhandles[allhandles.length - 1]);
            });

            console.log("paypal window")
            await driver.sleep(15000)
            await driver.wait(until.elementLocated(loginButton), 50000).click();
            console.log("clicked login")
            await driver.sleep(15000)
            //await driver.wait(until.elementLocated(driver.findElement(By.id('email'))), 50e3).clear();
            await driver.wait(until.elementLocated(email), 50000).sendKeys(Billing.Paypal.Email);
            console.log("entered uname")

            await driver.wait(until.elementLocated(next), 50000).click();
            console.log("clicked next")
            await driver.sleep(10000)
            await driver.wait(until.elementLocated(password),50000).sendKeys(Billing.Paypal.Password);
            console.log("entered password")
            await driver.sleep(10000)
            await driver.wait(until.elementLocated(btnLogin),2000).click();
            await driver.sleep(10000)

            //await driver.findElement(submitButton).click()
            await driver.wait(until.elementLocated(submitButton), 50000).click();
            console.log("clicked continue")
            await driver.sleep(10000)

            await driver.switchTo().window(parent)
            await driver.sleep(10000)
            await driver.wait(until.elementLocated(placeMyOrder),5000).click();
            await driver.sleep(10000)
             try {
            await driver.wait(until.elementLocated(orderPlaced),5000).isDisplayed();
            await driver.wait(until.elementLocated(closeOrderPlaced),5000).click();
          } catch (err) {

          }
            let orderNumer = await driver.wait(until.elementLocated(verifyOrderNumber),5000).getText();
            console.log("Order Nubmber is: "+orderNumer)
             expect(orderNumer).toBeTruthy()
          }


    }else{

    }
}
export const addNewCard = async() =>{
  await driver.findElement(By.css("#address-entry-new")).click();
}


export const clickOnCreditOrDebitCard = async() =>{
  await driver.findElement(By.css("#creditDebitPayment")).click();
}

export const clikOnPayPal = async() =>{
  await driver.findElement(By.css("#paypalPayment")).click();
}
function testMethod(a,b){
  console.log(a+b);
}
exports.testMethod= testMethod;
