@SignIn @HighLevel
Feature: Sign In

  Scenario: User is able to sign in
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills user data and signs in
    Then Verify user is in My Account main page

    When User hovers over My Account
    Then Dropdown should welcome user by first name

    # JCSC-1367 for steel
    When User goes to My Details using header
    Then Verify user is in account details page

    When User goes to Wishlist using header
    Then Verify user is in wishlist page

    When User goes to Order History using header
    Then Verify user is in Order History page

    When User clicks Factory logo
    Then Verify user is in homepage
    When User hovers over My Account
    Then Dropdown should welcome user by first name

    When User navigates to a subcategory from main category
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    And User selects random product from product array
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User searches for a random search term
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User navigates to random clearance page    
    And User hovers over My Account
    Then Dropdown should welcome user by first name

    When User signs out using header
    Then Verify user is in homepage
    And Verify header contains Sign In visible