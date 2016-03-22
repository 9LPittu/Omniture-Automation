@Account
Feature: Sign In Page

  Background:
    Given User is on homepage
    And Handle the Email Capture pop up
    And Goes to sign in page

  Scenario: Error message validation for incorrect email and password
    When Login page is loaded
    And User enters invalid as email
    And Changes focus to password field
    Then An error message saying Please enter a valid email address. should appear
    And Sign in button should be deactivated
    When User enters test@example.org as email
    Then An error message saying Whoops, that's not right... Please try your password again. should appear
    And Sign in button should be deactivated

   #US9890: Account Register and Errors
  #US9890_TC01 to US9890_TC05
  Scenario: Validate create an account section
    Then Breadcrumb should display J.Crew
    And Clicks on J.Crew Breadcrumb
    And Verify user is in homepage
    And User presses back button
    And Registration benefits copy is displayed as Three reasons why you should: Quick checkout, shareable wishlists and easy order tracking.
    And First Name field is displayed in registration section and max 30 characters allowed
    And Last Name field is displayed in registration section and max 30 characters allowed
    And Email field is displayed in registration section and max 75 characters allowed
    And Password field is displayed in registration section and max 40 characters allowed
    When User clicks on create an account button
    Then An error message saying Please enter first name. should appear under first name field
    Then An error message saying Please enter last name. should appear under last name field
    Then An error message saying Please enter a valid email address. should appear under email field
    Then An error message saying Please enter password. should appear under password field

  #US9890_TC04 (step 5)
  Scenario Outline: Validate error message display when invalid or null email address is given
    And Enter tester as First Name in create account section
    And Enter automation as Last Name in create account section
    And Enter <emailinput> as Email in create account section
    And Enter Jcrewtest as Password in create account section
    Then Error message <errmsg> is displayed

   Examples:
    |emailinput|errmsg|
    |invalid   |Please enter a valid email address.|
    |          |Please enter a valid email address.|


  Scenario: Validate error message display when existing email address is given
    And Enter tester as First Name in create account section
    And Enter automation as Last Name in create account section
    And Enter test2@test.com as Email in create account section
    And Enter Jcrewtest as Password in create account section
    And User clicks on create an account button
    Then Error message This email id is already registered with us. Please sign-in. is displayed


  US9890_TC06, US9890_TC07,US9890_TC08,US9890_TC09
  Scenario: User registration section should have country selection listbox
    And Country selection list box is displayed
    And United States is selected as default value
    And User can choose any country from the country list box
    #further implementation needed for below line---check box is not displaying for all of the countries at present
    And Verify opt checkbox not displayed for USA
    # to do later once it is ready----JCSC-878
    #And Verify opt checkbox is displayed for non USA and not checked by default

  #US9890_TC10
  Scenario: create new account from home page
    And Enter random first name as First Name in create account section
    And Enter random last name as Last Name in create account section
    And Enter random email as Email in create account section
    And Enter random password as Password in create account section
    And selects any country from the country list
    And User clicks on create an account button
    And Verify user is in homepage
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    And User clicks on hamburger menu
    Then My Account link is present

   #US9890_TC11
  #Not working as expected at present----in reference to JCSC-864
  Scenario: create new account from pdp page
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    And User clicks on hamburger menu
    And Goes to sign in page
    And Enter random first name as First Name in create account section
    And Enter random last name as Last Name in create account section
    And Enter random email as Email in create account section
    And Enter random password as Password in create account section
    And selects any country from the country list
    And User clicks on create an account button
    And User is in product detail page
    And User clicks on hamburger menu
    And Selects Women Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    And User clicks on hamburger menu
    Then My Account link is present














