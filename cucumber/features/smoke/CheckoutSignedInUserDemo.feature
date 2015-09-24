@CheckoutSignedinUser2
Feature: Checkout Process for signed in user

Background:
	Given User is on homepage
	
Scenario Outline: Checkout signed in user
	 
	And User clicks on hamburger menu
	Then select "<category>" from left nav
	Then select "<subcategory>" from subcategories
	Then select a product from array page and select variation, color, size
	And A wishlist button is present
	And Add to cart button is pressed
	And A minicart modal should appear with message '1 item has been added to your cart.'
	And Bag should have item(s) added
	Given item bag is clicked
	And Verifies edit button is present
	And Verifies remove button is present
	Then change URL to mobile URL
	Then verify mobile page is displayed
	And Clicks on checkout	
	When User provides mobile login credentials
	Then click on sign in and checkout button
	Then verify shipping address section is displayed
	Then verify billing address section is displayed
	And enter credit card security code as "<securitycode>"
	And click on place your order button
	And verify order number is generated
	
	Examples:
	|category|subcategory   |securitycode|
	#|Women   |Shirts & Tops|123		 |
	#|Women   |New Arrivals |123		 |
	 |Women   |Outerwear    |123		 |