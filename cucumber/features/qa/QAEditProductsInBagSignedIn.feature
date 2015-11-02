@EditProductsInBagSignedIn
Feature: Edit Product In Bags Signed In Scenarios

  Scenario: Verify Redirect to PDP Scenario Signed In User
    Given User is on homepage
    And Goes to sign in page
    And User enters wishlist1@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User clicks on hamburger menu
    And Selects Men Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Lambswool sweater to display PDP
    And User is in product detail page
    And Color DEEP NAVY is selected by user
    And Size SMALL is selected by user
    #And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color DEEP NAVY is selected
    And Verify size SMALL is selected

