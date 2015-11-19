# TODO: Data driven
#@EditProductsInBag
Feature: Edit Product In Bags Scenarios

  Background:
    Given User is on homepage
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

  Scenario: Verify Redirect to PDP Scenario
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected

  Scenario: Edit color, size and verify update button in edit mode PDP
    Then Verify color MARLED CHILI is selected
    And Verify size SMALL is selected
    And Verify update bag button is present
    Then Color MARLED ATLANTIC is selected by user
    And Size MEDIUM is selected by user
    Then Verify color MARLED ATLANTIC is selected
    And Verify size MEDIUM is selected

  Scenario: Verify shopping bag page reflects changes made to edited item
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

