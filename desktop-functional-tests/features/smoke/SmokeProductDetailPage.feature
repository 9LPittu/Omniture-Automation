@PDP
Feature: Product Detail Page

  Background:
    Given User goes to homepage
    And User closes email capture
    And User hovers on a random category from list
    	|Women|
    	|Men|
    	|Girls|
    	|Boys|
    And User selects random subcategory array

  Scenario: Product Detail Page Validation
    And User closes email capture
    And User selects random product from product array
    And Verify product detail page is displayed
    
    When User selects random color
    And User selects random size        
    And User adds product to bag

    When User clicks in bag
	Then Verify shopping bag is displayed