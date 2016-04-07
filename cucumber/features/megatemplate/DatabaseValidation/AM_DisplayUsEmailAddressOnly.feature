@AM
Feature: All Modules - Only US location accounts are displayed
  
Scenario:  All Modules_Validate only US location accounts are displayed in the feeds
	When user executes feed validation AMUSLocationQuery sql in DB
	And user verify feed validation and log the results for the query AMUSLocationQuery
	
