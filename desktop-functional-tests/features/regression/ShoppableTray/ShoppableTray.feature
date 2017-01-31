@ShoppableTray @HighLevel
Feature: PDP Layout

  Background:
    Given User goes to homepage
    And User closes email capture    
    When User hovers on a random category from list
    	|Men|
	
  Scenario: ShoppableTray from sub category page
    When User selects Suits subcategory array
	#And User selects random MPDP for shoppableTray
	
	When Verify SHOP THE LOOK title is displayed on top
	And Verify the itmes count in shoppableTray
	And Verify the items counts matches with Carousel items
	And Verify the ecach product has name item number colour size
	
	   