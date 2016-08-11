@MergeCheckout
Feature: Users with clean bag do not get a Merge Cart page

  Scenario: Registered user does not get to a merge cart page
    Given User goes to homepage
    And User closes email capture
    When User opens menu
    And User clicks user button SIGN IN TO MY ACCOUNT in drawer
    And User fills form and signs in
    And This script cleans bag for current user
    And User goes to homepage

    When User opens menu
    And User clicks user button My Account in drawer
    And User signs out

    Given User goes to homepage
    When User opens menu
    And User clicks on Clothing in drawer
    And User clicks on Tees & More in drawer
    And User clicks on random product in category array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    And User clicks bag in header
    And User clicks in CHECK OUT NOW button
    And User signs in and checks out
    Then Verify user is in review page
    And Verify Review Page url is /checkout2/signin.jsp
    And Verify madewell logo is visible
    And Verify checkout breadcrumb is REVIEW
    And Verify that Review title is Checkout
    And Verify products added matches with products in bag

  Scenario: Guest user does not get to a merge cart page
    Given User goes to homepage
    And HandleUser closes email capture
    When This script cleans bag for current user

    Given User goes to homepage
    When User opens menu
    And User clicks on Clothing in drawer
    And User clicks on Tees & More in drawer
    And User clicks on random product in category array
    Then Verify a product detail page is displayed

    When User selects a color
    And User selects size
    And User clicks ADD TO BAG button
    And User clicks bag in header
    And User clicks in CHECK OUT NOW button
    And User checks out as guest
    Then Verify Shipping Page is displayed
    And Verify madewell logo is visible
    And Verify Shipping Page url is /checkout2/signin.jsp
    And Verify that Shipping title is Checkout
    And Verify checkout breadcrumb is SHIPPING ADDRESS