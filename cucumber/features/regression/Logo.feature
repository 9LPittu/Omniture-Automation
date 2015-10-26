@Logo
Feature: Logo as homepage link

  Background:
    Given User is on homepage

  Scenario: Verify logo redirects to homepage in category page
    Then JCrew Logo is present
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    Then Clicks on JCrew Logo
    And Verify user is in homepage

  Scenario: Verify logo redirects to homepage in pdp page
    Then User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    Then Clicks on JCrew Logo
    And Verify user is in homepage

  Scenario: Verify logo redirects to homepage in Order Confirmation Page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Fills required payment data in billing page
    And Submits payment data in billing page
    And Clicks on place your order
    And User should be in order confirmation page
    Then Clicks on JCrew Logo
    And Verify user is in homepage

  Scenario: Verify logo redirects to homepage in Review Order Page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    And Fills required payment data in billing page
    And Submits payment data in billing page
    Then Clicks on JCrew Logo
    And Verify user is in homepage

  Scenario: Verify logo redirects to homepage in billing page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    And Uses default value for shipping method
    And Uses default value for gifts option
    And Clicks continue button on shipping method page
    Then Clicks on JCrew Logo
    And Verify user is in homepage

  Scenario: Verify logo redirects to homepage in shipping method page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    And Fills shipping address
    And Presses continue button on shipping address
    And Verifies is in shipping method page
    Then Clicks on JCrew Logo
    And Verify user is in homepage


  Scenario: Verify logo redirects to homepage in shipping address page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    And Selects to checkout as guest
    Then Clicks on JCrew Logo
    And Verify user is in homepage


  Scenario: Verify logo redirects to homepage in select checkout type page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    And Clicks on checkout
    Then Clicks on JCrew Logo
    And Verify user is in homepage


  Scenario: Verify logo redirects to homepage in shopping bag page
    And User clicks on hamburger menu
    And Selects WOMEN Category from hamburger menu
    And User clicks on SWEATERS subcategory from Women Category
    And Selects the first product from product grid list
    And User is in product detail page
    And A color is selected
    And A size is selected
    And Add to cart button is pressed
    And User clicks on item bag
    Then Clicks on JCrew Logo
    And Verify user is in homepage

