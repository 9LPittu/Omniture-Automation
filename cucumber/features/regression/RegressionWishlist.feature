
@Wishlist
Feature: Ability to link to PDP from Wishlist

  Background:
    Given User is on homepage
    And Goes to sign in page
    And User provides login information
    And Hits sign in button
    When User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    Then User should be in wishlist page
    And Deletes all previous wishlist items from the list

  Scenario: Wishlist should update properly
    When User presses search button
    And Enters A1447 to the search field
    And Clicks on search button for input field
    And Color BLACK is selected by user
    And Size 6 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is BLACK size is 6 and quantity is 1 in wishlist page
    When Edit wishlist for product Marlie dress in classic faille
    And User is in product detail page
    And Verify color BLACK is selected
    And Verify size 6 is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    When Color SEA SPRAY is selected by user
    And Size 8 is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Marlie dress in classic faille color is SEA SPRAY size is 8 and quantity is 1 in wishlist page
    When Edit wishlist for product Marlie dress in classic faille
    Then User is in product detail page
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
