@Account
Feature: Sign In Page

  Background:
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And Goes to sign in page

  @HighLevel
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


  #US9890_TC06, US9890_TC07,US9890_TC08,US9890_TC09
  Scenario: User registration section should have country selection listbox
    And Country selection list box is displayed
    And United States is selected as default value
    And User can choose top10 countries from the country list box
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
    And user selects any category from hamburger menu
	And user selects any subcategory
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    Then My Account link is present

   #US9890_TC11
  #Not working as expected at present----in reference to JCSC-864
  # changed the implementation to verify we land in home page after creating account
  Scenario: create new account from pdp page
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    And Goes to sign in page from menu
    And Enter random first name as First Name in create account section
    And Enter random last name as Last Name in create account section
    And Enter random email as Email in create account section
    And Enter random password as Password in create account section
    And selects any country from the country list
    And User clicks on create an account button
    And Verify user is in homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
    And Selects the first product from product grid list
    And User is in product detail page
    And User clicks on hamburger menu
    And User clicks on back link
    Then My Account link is present

  #Commenting it out as this field is not  available yet.
  #  Scenario: Birthday field validation in create an account form
#  	And user appends ?showBirthDateOnRegistration=true to the url in the browser and navigate to the page
#  	And Enter random first name as First Name in create account section
#    And Enter random last name as Last Name in create account section
#    And Enter random email as Email in create account section
#    And Enter random password as Password in create account section
#    And user should see birthday section after password field
#    And user should see label text as 'Add your birthday & receive a lorem ipsum dolar offer!' in create account form
#    And Month dropdown should have value as 'Month'
#    And Day dropdown should have value as 'Day'
#    And month dropdown should have options same as calendar
#    And day dropdown should have options same as calendar
#    And user selects random value from month dropdown
#    Then user should see the error message as 'Please enter Day.'
#    And user selects Month value from month dropdown
#    And user selects random value from day dropdown
#    Then user should see the error message as 'Please enter Month.'
#    And user selects April value from month dropdown
#    And user selects 31 value from day dropdown
#    Then user should see the error message as 'Please choose a valid date.'
#    And user selects 30 value from day dropdown
#    And Month dropdown should have value as 'April'
#    And Day dropdown should have value as '30'
#    And selects any country from the country list
#    And Month dropdown should have value as 'April'
#    And Day dropdown should have value as '30'