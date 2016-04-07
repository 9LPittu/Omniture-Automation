@megaShoppingBag
Feature: Megatemplate Shopping Bag page Validation

Scenario: Browseby URL validation
    When user executes shoppingbagquery sql query in DB
	And user navigates to url and should see the Shopping Bag page