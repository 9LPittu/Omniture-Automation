#TODO: Data driven
#@Wishlist
Feature: Ability to link to PDP from Wishlist

  Scenario: Wishlist should update properly
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    When User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    And Deletes all previous wishlist items from the list
    And User presses search button
    And Enters E1600 to the search field
    And Clicks on search button for input field
    And Color BLACK is selected by user
    And Size LARGE is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Jeweled wool back-zip sweater color is BLACK size is LARGE and quantity is 1 in wishlist page
    Then Edit wishlist for product Jeweled wool back-zip sweater
    Given User is in product detail page
    Then Verify color BLACK is selected
    And Verify size LARGE is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    Then Color HTHR STONE is selected by user
    And Size MEDIUM is selected by user
    Then Verify color HTHR STONE is selected
    And Verify size MEDIUM is selected
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Jeweled wool back-zip sweater color is HTHR STONE size is MEDIUM and quantity is 1 in wishlist page
    Then Edit wishlist for product Jeweled wool back-zip sweater
    Then User is in product detail page
    Then Color HTHR STONE is selected by user
    And Size MEDIUM is selected by user
    And Wishlist button is pressed
    Then Verify update message for button wishlist is displayed
    Then Verify update wishlist button is displayed
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'

