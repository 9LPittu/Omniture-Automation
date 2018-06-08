import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress,clickOnCheckout} from '../../../pageObjects/ShoppingBagObj';
import {editAdress} from '../../../pageObjects/shippingAddressObj';



test('navigate to home page', async () => {
  await driver.manage().window().maximize()
   await load();
   console.log('Home page loaded proprely')

});
test('select product id and goto shoppingbag page', async () =>{
  await goToShoppingBag();
  console.log('product selection done')
  await clickOnCheckout();
});

test('click on login as Guest button', async () =>{


  await loginAsGuestButton();
  console.log('login as Guest succesfully')

});


test('Add the address information', async () =>{

await addAddress();
console.log('Added the address and clicked on continue')

});
