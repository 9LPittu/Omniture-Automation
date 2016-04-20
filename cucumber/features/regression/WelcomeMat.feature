@WelcomeMat
Feature: International: Welcome Mat for ROW
#US15294_TC01,US15294_TC03,US15294_TC04,US15294_TC05
Scenario Outline: Welcome mat display validation for international home page
  Given User is on clean session international <country_group> page
  And Welcome mat page is displayed
  And Welcome mat header message is displayed as Hello, Canada for canada, Around the World for the ROW
  And JCrew Logo is present on the welcome mat
  And Corresponding country name and flag is displayed
  And Welcome mat content is displayed
  And User clicks on START SHOPPING on welcome mat
  And user should land on country specific home page
  And user should see selected country in the footer
  When User clicks on hamburger menu
  And user selects any category from hamburger menu
  And user selects any subcategory
  And user should see selected country in the footer
  And user selects any item from array page, select any color and size
  And User is in product detail page
  And user should see selected country in the footer
  And Add to cart button is pressed
  And User clicks on item bag
  Then User should be in shopping bag page
  And user should see selected country in the footer
  And Clicks on checkout
  And user should see selected country in the footer
  And Selects to checkout as guest
  And user should see selected country in the footer
  When user fills selcted country shipping address
  And Presses continue button on shipping address
  And Verifies is in shipping method page
  And user should see selected country in the footer
  And Uses default value for shipping method
  And Clicks continue button on shipping method page
  And Verify user is in billing page
  And user should see selected country in the footer
  And Fills required payment data in billing page
  And Submits payment data in billing page
  And user should see selected country in the footer
  And Clicks on place your order
  And User should be in order confirmation page
  And user should see selected country in the footer

  Examples:
  |country_group|
  |PRICEBOOK    |
  |NON-PRICEBOOK|




#US15294_TC02, (for US, welcome mat should not be displayed.for canada, it will but with diff message)
Scenario: Welcome mat display validation for US
  Given User is on homepage with clean session
  And Welcome mat page is not displayed


#US15294_TC08
Scenario Outline: Validation of Take me to the U.S. site. link functionality
  Given User is on clean session international <country_group> page
  And User clicks on Take me to the U.S. site. on welcome mat
  And Verify user is in homepage
  And user should see "United States" in footer
  When User clicks on hamburger menu
  And user selects any category from hamburger menu
  And user selects any subcategory
  And user should see "United States" in footer
  And user selects any item from array page, select any color and size
  And User is in product detail page
  And user should see "United States" in footer
  And Add to cart button is pressed
  And User clicks on item bag
  Then User should be in shopping bag page
  And user should see "United States" in footer
  And Clicks on checkout
  And user should see "United States" in footer
  And Selects to checkout as guest
  And user should see "United States" in footer
  And enter first name on shipping address page
  And enter last name on shipping address page
  And enter address line1 as "<shipping_address1>" on shipping address page
  And enter address line2 as "<shipping_address2>" on shipping address page
  And enter zip code as "<shipping_zipcode>" on shipping address page
  And enter phone number on shipping address page
  And Presses continue button on shipping address
  Then user should see QAS verification in the shipping address page
  And click on 'USE ADDRESS AS ENTERED' button in the shipping address page
  And Verifies is in shipping method page
  And user should see "United States" in footer
  And select shipping method on shipping & gift options page
  And Clicks continue button on shipping method page
  And Verify user is in billing page
  And user should see "United States" in footer
  And enter "Visa_Card" details on billing page
  And enter email address as "jcrewcolab@gmail.com"
  And click on 'ADD NEW BILLING ADDRESS' on Billing page
  And select country as "<billing_country>" in the Add New Billing Address form
  And enter first name in the Add New Billing Address form
  And enter last name in the Add New Billing Address form
  And enter "<billing_address1>" as address line1 in the Add New Billing Address form
  And enter "<billing_address2>" as address line2 in the Add New Billing Address form
  And enter "<billing_zipcode>" as zipcode in the Add New Billing Address form
  And enter phone number in the Add New Billing Address form
  Then click on 'SAVE' button in the Add New Billing Address form
  Then user should see QAS verification in the Billing page
  And click on 'USE ADDRESS AS ENTERED' button in the Billing page
  And Submits payment data in billing page
  And User is on internal /checkout2/billing.jsp page
  And user should see "United States" in footer
  And product name and price on review page should be displayed correctly
  Then Clicks on place your order
  Then User should be in order confirmation page
  And user should see "United States" in footer

  Examples:
  |country_group|shipping_address1|shipping_address2|shipping_zipcode|billing_country|billing_address1|billing_address2|billing_zipcode|
  |PRICEBOOK    |107-12 Continental Avenue||11375|United States|1 Pennsylvania Plaza||10119|
  |NON-PRICEBOOK|107-12 Continental Avenue||11375|United States|1 Pennsylvania Plaza||10119|








