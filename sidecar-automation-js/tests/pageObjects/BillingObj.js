import { driver } from '../helpers';
import { globals } from '../jestJcrewQaConfig';
import {goToShoppingBag} from '../pageObjects/ShoppingBagObj';
import {login,loginInAfterCheckoutPage} from '../pageObjects/loginPageObj';
import { Billing } from '../testdata/Sanity';


const { Builder, By, Key, until } = require('selenium-webdriver');

export const paymentMethod = async() =>{

    if(Billing.PAYMENT_METHOD == "Credit/Debit_Card"){
      console.log(await driver.findElement(By.css("#creditDebitPayment")).isSelected())
        if(await driver.findElement(By.css("#creditDebitPayment")).isSelected()){
            //Need to write code for selecting two cards if more than two cards avilable
            let card = "//span[text()='"+Billing.Credit_Debit_Card.Name_On_Card+"']/parent::label/input[@class='address-radio']";
            console.log(card);
            await driver.findElement(By.xpath(card)).click();
            await driver.findElement(By.css("#order-summary__button-continue")).click();
            //#securityCode
            await driver.findElement(By.css("#securityCode")).sendKeys(Billing.Credit_Debit_Card.Security_Code);
            //button-submitorder
            await driver.findElement(By.css("#button-submitorder")).click();
              ////span[@id='confirmation-number']/span[2]
            let orderNumer = await driver.findElement(By.css("//span[@id='confirmation-number']/span[2]")).getText();
            console.log("Order Number is : "+orderNumer)
        }else{
          await driver.findElement(By.css("#creditDebitPayment")).click();
        }
    } else if(Billing.PAYMENT_METHOD == "Paypal"){
            //paypalPayment
            await driver.findElement(By.css("#paypalPayment")).click();
            await driver.findElement(By.xpath("//div[@data-funding-source='paypal']")).click();
            await driver.findElement(By.css("#order-summary__button-continue")).click();
            await driver.findElement(By.xpah("//a[text()='Log In']")).click();
            //#email
            await driver.findElement(By.css("#email")).sendKeys(Billing.Paypal.Email);
            await driver.findElement(By.css("#btnNext")).click();
            await driver.findElement(By.css("#password")).sendKeys(Billing.Paypal.Password);
            //btnLogin
            await driver.findElement(By.css("#btnLogin")).click();
            //#confirmButtonTop
            await driver.findElement(By.css("#confirmButtonTop")).click();
            await driver.findElement(By.css("#button-submitorder")).click();


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
