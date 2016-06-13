@SignIn

Feature: Sign In

  Scenario: User is able to sign in
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    When User clicks on sign in using header
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User hovers over My Account
    Then Dropdown should welcome user by first name

    When User goes to My Details using My Account menu
    Then Known user information should match My Details page

    When User goes to Wishlist using header
    Then Verify user is in wishlist page

    When User goes to Order History using header
    Then Verify user is in Order History page

    When User goes to My Details using header
    Then Verify user is in My Account main page

    When User clicks JCrew logo
    Then Verify user is in homepage
    When User hovers over My Account
    Then Dropdown should welcome user by first name

    When User navigates to a subcategory from main category
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User navigates to a pdp
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User searches for a random search term
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User navigates to a random sale page
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User signs out using header
    Then Verify user is in homepage
    And Verify header contains Sign In visible
