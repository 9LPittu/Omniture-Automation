@AM
Feature: All Modules - Verify the format of Product url

Scenario: Verify product URL format across all 5 modules
	When user executes feed validation AMUrlFormatfor5ModuleQuery sql in DB
	And user verify feed validation and log the results for the query AMUrlFormatfor5ModuleQuery
	
