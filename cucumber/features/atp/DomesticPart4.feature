@ATPDomestic4

Feature:  View should be displayed for guest user in Domestic Context
  
  Scenario Outline: ATP view should be displayed for guest checkout
    #ATP_11
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
    And enter first name on shipping address page    
    And enter last name on shipping address page
    And enter address line1 as "<shipping_address1>" on shipping address page
    And enter address line2 as "<shipping_address2>" on shipping address page
    And enter zip code as "<shipping_zipcode>" on shipping address page
    And enter phone number on shipping address page
    
    And Presses continue button on shipping address
    
    And User is on internal /checkout2/shipping.jsp page
    Then Verifies user is in shipping method page
    And Verify that all shipping methods are available including Thursday cut
    Then Verify all shipping methods show estimated shipping date
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
    
    When enter "Visa_Card" details on billing page
    And enter email address as "jcrewcolab@gmail.com"
    And Submits payment data in billing page
    Then User is on internal /checkout2/billing.jsp page
    
    When User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    Examples:
    |shipping_address1|shipping_address2|shipping_zipcode|
    |904 Oak Gln||92168|