@Wishlist @HighLevel
Feature: Add items to Wishlist from PDP and Shoppable Tray

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
    #Open related Jira: SC-696
    When User clicks on hamburger menu
    And user selects WOMEN category from hamburger menu
    And user selects NEW ARRIVALS subcategory
    And Selects the first product with available colors from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And in wishlist page, user should see the color selected on the PDP page
    And in wishlist page, user should see the size selected on the PDP page
    And in wishlist page, user should see the quantity as 1
    And edit item from wishlist
    And User is in product detail page
    Then user should see that previously selected color is retained
    And user should see that previously selected size is retained
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    And user selects a new color
    And user selects a new size
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And in wishlist page, user should see the color selected on the PDP page
    And in wishlist page, user should see the size selected on the PDP page
    And in wishlist page, user should see the quantity as 1
    And edit item from wishlist
    And user selects a new color
    And user selects a new size
    Then User is in product detail page
    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Deletes browser cookies
