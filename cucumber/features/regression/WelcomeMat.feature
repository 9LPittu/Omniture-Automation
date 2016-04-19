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

##  |/uk/c/womens_category/sweaters|
##  |/uk/p/womens_category/sweaters/pullover/floral-sequinsleeve-sweater/E2983|
##  |/uk/r/search/?N=0&Nloc=en&Ntrm=dresses&Npge=1&Nrpp=60&Nsrt=0             |
##  |/uk/r/search/?N=21+17&Ntrm=&Nsrt=3&Npge=1&Nrpp=60                        |



#US15294_TC02, (for US, welcome mat should not be displayed.for canada, it will but with diff message)
Scenario: Welcome mat display validation for US
  Given User is on homepage with clean session
  And Welcome mat page is not displayed


#US15294_TC08
Scenario Outline: Validation of Take me to the U.S. site. link functionality
  Given User is on clean session international <country_group> page
  And User clicks on Take me to the U.S. site. on welcome mat
  And User is on homepage
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
  When user fills selcted country shipping address
  And Presses continue button on shipping address
  And Verifies is in shipping method page
  And user should see "United States" in footer
  And Uses default value for shipping method
  And Clicks continue button on shipping method page
  And Verify user is in billing page
  And user should see "United States" in footer
  And Fills required payment data in billing page
  And Submits payment data in billing page
  And user should see "United States" in footer
  And Clicks on place your order
  And User should be in order confirmation page
  And user should see "United States" in footer



  Examples:
  |country_group|
  |PRICEBOOK    |
  |NON-PRICEBOOK|








