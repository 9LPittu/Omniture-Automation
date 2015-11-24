@Wishlist
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
    And Enters A1447 to the search field
    And Clicks on search button for input field
    And Color BLACK is selected by user
    And Size 6 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is BLACK size is 6 and quantity is 1 in wishlist page
    Then Edit wishlist for product Marlie dress in classic faille
    Given User is in product detail page
    Then Verify color BLACK is selected
    And Verify size 6 is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    Then Color SEA SPRAY is selected by user
    And Size 8 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is SEA SPRAY size is 8 and quantity is 1 in wishlist page
    Then Edit wishlist for product Marlie dress in classic faille
    Then User is in product detail page
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'
