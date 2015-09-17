@EditProductsInBagSignedIn
Feature: Edit Product In Bags Signed In Scenarios

  Scenario: Verify Redirect to PDP Scenario Signed In User
    Given User is on homepage
    And Goes to sign in page
    And User enters wishlist@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User clicks on hamburger menu
    And Selects MEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Men Category
    And Click on product Rustic cotton fisherman sweater to display PDP
    And User is in product detail page
    And Color HTHR IVORY is selected by user
    And Size LARGE is selected by user
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks edit button on item bag page
    Then Verify color HTHR IVORY is selected
    And Verify size LARGE is selected

