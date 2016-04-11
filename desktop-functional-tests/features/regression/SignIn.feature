@SignIn

Feature: Sign In

  Scenario: User is able to sign in
    Given User goes to homepage
    When User clicks on sign in using header
    And User fills user data and signs in
    Then Verify user is in My Account main page
    When User hovers over My Account
    And Dropdown should welcome user by first name
    And User goes to My Details using My Account menu
    Then Known user information should match My Details page
    When User goes to Wishlist using header
    Then Verify user is in wishlist page
    When User goes to Order History using header
    Then Verify user is in Order History page
    When User goes to My Details using header
    Then Verify user is in My Account main page
    When User signs out using header
    Then Verify user is in homepage
    And Verify header contains Sign In visible
