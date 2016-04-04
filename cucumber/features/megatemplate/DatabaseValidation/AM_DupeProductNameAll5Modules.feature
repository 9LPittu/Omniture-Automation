@AM
Feature: All Modules - Verify duplicates in the product name across CW,PA, NA, TR and BR

Scenario:  Verify if there is any duplication in product names across 5 modules CW,PA, NA, TR and BR
	When user executes feed validation AMNameDupfor5ModuleQuery sql in DB
	And user verify feed validation and log the results for the query AMNameDupfor5ModuleQuery
	
