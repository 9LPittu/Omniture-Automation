@ATPInternational1
Feature: ATP should not display for international - Canada

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And verify change link is displayed in the ship to section of footer
    Then click on change link from footer
    And User is on context chooser page
	When User selects ca country code
    
   Scenario Outline: ATP should not display for regular items in international context
    #Add item to the bag
    When User is on homepage
    And User clicks on hamburger menu
    And user selects any category from hamburger menu
	And user selects any subcategory
	
	And user selects any item from array page, select any color and size
    Then User is in product detail page    
    And product name and price should match with array page
         
    When Add to cart button is pressed
    When User clicks on item bag
    Then Verify products added matches with products in bag
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    When user fills selected country shipping address
    
    And Presses continue button on shipping address
    
    And User is on internal /checkout2/shipping.jsp page
    Then Verifies user is in shipping method page
    And Verify that all shipping methods are available including Thursday cut
    Then Verify all shipping methods show estimated shipping date
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
	
	#Billing Method change
	
    When enter "Visa_Card" details on billing page
    And enter email address as "jcrewcolab@gmail.com"
    And Submits payment data in billing page
    Then User is on internal /checkout2/billing.jsp page
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|
    |904 Oak Gln||92168|
    