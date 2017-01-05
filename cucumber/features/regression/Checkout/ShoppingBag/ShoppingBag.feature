@ShoppingBag @HighLevel
Feature: Checkout - Editing items from shopping bag

  Scenario: Checkout - Multiple shopping bag functions
    Given User is on homepage with clean session
    
    When User clicks on hamburger menu
    And user selects WOMEN category from hamburger menu
    And user selects NEW ARRIVALS subcategory
    And Handle the Email Capture pop up

    And Selects the first product with available colors from product grid list
    Then User is in product detail page
    And A color is selected
    And A size is selected

    When Add to cart button is pressed
    Then A minicart modal should appear with message '1 item has been added to your cart.'
    And Bag should have 1 item(s) added
    
    When User searches for a random search term    
    Then User is in product detail page
    
    When A color is selected
    And A size is selected        
    And Add to cart button is pressed 

    When User goes to homepage
    And User clicks on sale link from top nav
    Then User is in sale landing page
    And user clicks on random sale department from below list
  	   |women|
       |men  |
       |boys |
       |girls|
    Then User is in Sale results page
    When User selects random product from array    
    Then User is in product detail page
    
    When A color is selected
    And A size is selected        
    And Add to cart button is pressed

    When User clicks on item bag
    Then Verify products added matches with products in bag
    And Verify all products have edit and remove buttons

    #User is able to edit quantity from shopping bag
    When User edits quantity of first item from bag
    Then Verify products added matches with products in bag
    And Verify items quantity and prices

    #User is able to remove item from shopping bag
    When User removes first item from bag
    Then Verify products added matches with products in bag

    #User is able to edit item from shopping bag
    When User edits last item from bag    
    Then user should see that previously selected color is retained    
    And user should see that previously selected size is retained    
    And Verify update bag button is present    
    
    When user selects a new color
    And user selects a new size
    
    When Update Bag button is pressed   
    And User clicks on item bag

    Then User should be in shopping bag page
    And Verify products added matches with products in bag
    And Verify edited item is displayed first in shopping bag