@BR
Feature: Browse By - Minimum and Maximum 3 items per account

Scenario: Browse By_Validate feed Have maximum and minimum 3 item for account in the Feed
   	When user executes feed validation browseproductminimumQuery sql in DB
   And user verify feed validation and log the results for the query feedTopratedminimumQuery	
	
			