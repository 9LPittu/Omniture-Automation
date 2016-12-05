@ATPInternational3
Feature: ATP should not display for international - Hong Kong

  Background: Clean bag for user
    Given User is on homepage with clean session
    And Handle the Email Capture pop up
    And verify change link is displayed in the ship to section of footer
    Then click on change link from footer
    And User is on context chooser page
	When User selects au country code
    
   Scenario: ATP should not display for regular items in international context
    #Add item to the bag
    Given User is on international homepage
    When User navigates to regular product
    When Add to cart button is pressed
    When User clicks on item bag
    Then Verify products added matches with products in bag
   
    And Clicks on checkout    
    And page url should contain /checkout2/shoppingbag.jsp
    And click on CHECK OUT AS A GUEST button
    When User fills International shipping data and continues
    
    And User is on internal /checkout2/shipping.jsp page
    Then Verifies user is in shipping method page
    And validate correct shipping methods displayed on the page
    And Clicks continue button on shipping method page
    Then Verify Billing page is displayed
	
    When enter "Visa_Card" details on billing page
    And enter email address as "jcrewcolab@gmail.com"
    And Submits payment data in billing page
    Then User is on internal /checkout2/billing.jsp page
    And User clicks on place your order button
    Then User should be in order confirmation page
    And verify order number is generated
    