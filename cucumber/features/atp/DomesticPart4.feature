@ATPDomestic4
Feature:  View should be displayed for guest user in Domestic Context
  
  Scenario: ATP view should be displayed for guest checkout
    #ATP_12
    When User is on homepage
   	When User navigates to regular product
    When Add to cart button is pressed
    When User clicks on item bag
    Then Verify products added matches with products in bag
    
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    #ATP_13
    And click on CHECK OUT AS A GUEST button
    When User fills shipping data and continues
    
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
    