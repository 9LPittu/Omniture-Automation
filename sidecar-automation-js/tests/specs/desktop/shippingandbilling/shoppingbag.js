import { driver } from '../../../helpers';
import { load } from '../../../pageObjects/jcrewdesktoppageobj';
import { globals } from '../../../jestJcrewQaConfig';
import {goToShoppingBag,loginAsGuestButton,addAddress} from '../../../pageObjects/ShoppingBagObj';
import {editAdress} from '../../../pageObjects/shippingAddressObj';

import {} from '../../../pageObjects/shippingAddressObj';

test('navigate to home page', async () => {
   await load();

});
test('select product id and goto shoppingbag page', async () =>{
  await goToShoppingBag();

});

test('click on login as Guest button', async () =>{

  //await clickOnCheckout();

  //await loginInAfterCheckoutPage();
  await loginAsGuestButton();

});


test('Add the address information', async () =>{

await addAddress();

});
