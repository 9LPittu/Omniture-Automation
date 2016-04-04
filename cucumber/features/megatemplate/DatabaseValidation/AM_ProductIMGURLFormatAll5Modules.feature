@AM
Feature: All Modules - Verify the format of Product image url

Scenario: Verify image url format across 5 modules
	When user executes feed validation AMIMGUrlFormatfor5ModuleQuery sql in DB
	And user verify feed validation and log the results for the query AMIMGUrlFormatfor5ModuleQuery
	
