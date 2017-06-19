@ShoppingBag
Feature: Checkout - Editing items from shopping bag for register user

Background:
  
    Given User goes to homepage
    And User closes email capture
    When User clicks on sign in using header
    And User fills form and signs in
    And This script cleans bag for current user
	And User hovers on a random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User goes to homepage
    And User clicks on crew clearance link from top nav
    And User selects random sale category
    When User selects random product from array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User searches for a random search term
    And User selects random product from array
    Then Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag
    
Scenario: Checkout - Verify Save button on shopping bag for register user
    When User clicks in bag
    Then Verify products added matches with products in bag
    And Verify all products have save buttons for register user