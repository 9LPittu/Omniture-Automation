@PDP2 @HighLevel
Feature: PDP Layout2

  Background:
    Given User goes to homepage
    And User closes email capture
    
    When User hovers on a random category from list
    	|Women|
    	|Men|
	And User selects sweaters subcategory array
    Then Verify user is in category array page

    Scenario: PDP layout from search array page
    When User searches specific term dresses
    And User is in search results page

    When User selects random product from array
    Then Verify product detail page is displayed
    And Verify product name on PDP matches with category array
    And Verify Item code displayed in PDP
    And Verify price matches with category array
    And Verify color swatchs displayed in PDP
    And Verify size chips displayed in PDP
    And Verify quantity displayed in PDP
    And Verify Add to Bag displayed in PDP
    And Verify Wishlist displayed in PDP
    And Verify social icons displayed in PDP

    And Verify SIZE & FIT is displayed between Add to Bag and PRODUCT DETAILS
    And Verify PRODUCT DETAILS is displayed between SIZE & FIT and reviews

    And Verify reviews displayed in PDP
    And Verify baynotes displayed in PDP
    And Verify endcaps displayed in PDP

