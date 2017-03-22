@PDP4 @HighLevel
Feature: PDP Layout3

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User hovers on a random category from list
    	|Women|
    	|Men|
	And User selects sweaters subcategory array
    Then Verify user is in category array page

 
  Scenario: PDP layout from bag page
    When User selects random product from product array
    Then Verify product detail page is displayed
    And User selects random color
    And User selects random size
    And User adds product to bag
    And User clicks in bag
    Then Verify shopping bag is displayed

    When User edits last item from bag
    Then Verify product detail page is displayed
    And Verify that page contains a selected color
    And Verify that page contains a selected size
    And Verify product name on PDP matches with category array
    And Verify Item code displayed in PDP
    And Verify price matches with category array
    And Verify that page contains a selected color
    And Verify size chips are displayed
    And Verify quantity displayed in PDP
    And Verify UPDATE BAG displayed in PDP
    And Verify Wishlist displayed in PDP
    And Verify social icons displayed in PDP

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    And Verify PRODUCT DETAILS is displayed between SIZE & FIT and reviews

    And Verify reviews displayed in PDP
    And Verify baynotes displayed in PDP
    And Verify endcaps displayed in PDP
    When User selects random color
    And User selects random size
    And User adds product to bag

    When User clicks in bag
    Then Verify products added matches with products in bag
