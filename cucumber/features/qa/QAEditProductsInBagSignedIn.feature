@EditProductsInBagSignedIn
Feature: Edit Product In Bags Signed In Scenarios

  Scenario: Verify Redirect to PDP Scenario Signed In User
    Given User is on homepage
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

