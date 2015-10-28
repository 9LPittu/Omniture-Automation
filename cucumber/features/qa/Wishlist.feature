@Wishlist
Feature: Ability to link to PDP from Wishlist

  Background:
    Given User is on homepage
    And Goes to sign in page
    When User enters wishlist@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on WISHLIST link in My Account Page
    And User should be in wishlist page
    And Verify product Jeweled wool back-zip sweater color is black size is large and quantity is 1 in wishlist page
    Then Edit wishlist for product Jeweled wool back-zip sweater

  Scenario: User should see corresponding PDP
    Given User is in product detail page
    Then Verify color black is selected
    And Verify size large is selected

  Scenario: Corresponding PDP should be editable
    Given User is in product detail page
    Then Verify color black is selected
    And Verify size large is selected
    And Verify 1 items are specified as quantity
    And Verify update wishlist button is displayed
    Then Color hthr stone is selected by user
    And Size medium is selected by user
    Then Verify color hthr stone is selected
    And Verify size medium is selected

  Scenario: Wishlist should update properly
    Given User is in product detail page
    Then Color hthr stone is selected by user
    And Size medium is selected by user
    And Wishlist button is pressed
    Then Verify update message for wishlist is displayed and go to wishlist page
    And Verify product Jeweled wool back-zip sweater color is hthr stone size is medium and quantity is 1 in wishlist page
    Then Edit wishlist for product Jeweled wool back-zip sweater
    Then User is in product detail page
    Then Color hthr stone is selected by user
    And Size MEDIUM is selected by user
    And Wishlist button is pressed
    Then Verify update message for button wishlist is displayed
    Then Verify update wishlist button is displayed
    Then Add to cart button is pressed
    And A minicart modal should appear with message '1 item has been added to your cart.'

