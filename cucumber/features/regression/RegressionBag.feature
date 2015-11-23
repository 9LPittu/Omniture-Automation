# TODO: Data driven
#@Bag
Feature: Edit Product In Bags Scenarios

  Background:
    Given User is on homepage

  Scenario: Edit link redirects user to PDP
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Marled lambswool sweater to display PDP
    And User is in product detail page
    And Color MARLED CHILI is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected

  Scenario: Verify on edit mode add to bag changes to update bag
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Marled lambswool sweater to display PDP
    And User is in product detail page
    And Color MARLED CHILI is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected
    And Verify update bag button is present
    Then Color MARLED ATLANTIC is selected by user
    And Size MEDIUM is selected by user
    Then Verify color MARLED ATLANTIC is selected
    And Verify size MEDIUM is selected

  Scenario: Verify shopping bag page reflects changes made to edited item
    And User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Marled lambswool sweater to display PDP
    And User is in product detail page
    And Color MARLED CHILI is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected
    And Verify update bag button is present
    Then Color MARLED ATLANTIC is selected by user
    And Size MEDIUM is selected by user
    And Quantity 3 is selected by user
    Then Verify color MARLED ATLANTIC is selected
    And Verify size MEDIUM is selected
    Then Update Bag button is pressed
    Then User should be in shopping bag page
    And Verify color MARLED ATLANTIC is displayed in shopping bag
    And Verify size MEDIUM is displayed in shopping bag
    And Verify 3 items are specified as quantity in shopping bag

  Scenario: signed in Edit link redirects user to PDP
    And Goes to sign in page
    And User enters wishlist1@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    And User is on homepage
    And User bag is cleared
    And User goes to homepage
    Then User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Marled lambswool sweater to display PDP
    And User is in product detail page
    And Color MARLED CHILI is selected by user
    And Size SMALL is selected by user
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected