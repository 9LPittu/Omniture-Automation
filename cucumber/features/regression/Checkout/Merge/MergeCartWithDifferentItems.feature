@MergeCheckout
Feature: Checkout - Merge cart page validation for e-gift card, regular item, backorder item and few left item

  Background: Clean bag for user
    Given User goes to homepage
    And Handle the Email Capture pop up
    
    When click on SIGN IN from header
    And User fills form and signs in
    And User bag is cleared
    And User goes to homepage

  Scenario: Checkout - Merge cart page validation for e-gift card, regular item, backorder item and few left item

	#Add backordered item to bag
    When User navigates to backordered product
    And Add to cart button is pressed
	
	#Add few left item to bag
    When User navigates to only few left product    
    And Add to cart button is pressed
    
    #Add regular item to bag
    When User navigates to regular product   
    And Add to cart button is pressed    
    
	#Add e-gift card to bag
	And Click on footer link Our Cards to open
    And Click on sublink The J.Crew Gift Card from Our Cards footer link
    Then Verify user is on the j.crew gift card page
    And User is on internal /help/gift_card.jsp?sidecar=true page
    	
	When User adds e-gift card worth any value to bag	
	 	
	#sign out 
	And User goes to homepage
    And click on MY ACCOUNT from header
    When user clicks on "Sign Out" from My Account dropdown 
    Then Verify user is in homepage
  	
	#Add item as guest user
    When User clicks on hamburger menu    
    And user selects any category from hamburger menu
	And user selects any subcategory
    
    And user selects any item from array page, select any color and size
    Then User is in product detail page
    
    And Add to cart button is pressed
    
    When User clicks on item bag
    
    #checkout and sign in as previous user
    And Clicks on checkout   
    And User signs in and checks out
    
    #Merge cart page validation
    Then Verify user is in Merge Cart page
    And Verify user is welcome to Merge Cart page
    And user should see 'SAVE TO WISHLIST & CONTINUE' button on the page    
	And user should see 'ADD ITEMS TO BAG & REVIEW ORDER' button on the page
    
    #item status verification
    Then Verify the message as 'IN STOCK - ONLY A FEW LEFT' for the few left item in Merge Cart page
    And Verify the message as 'IN STOCK' for the regular item in Merge Cart page    
    And Verify the message for the backordered item in Merge Cart page    
    And Verify e-gift card details in Merge Cart page    