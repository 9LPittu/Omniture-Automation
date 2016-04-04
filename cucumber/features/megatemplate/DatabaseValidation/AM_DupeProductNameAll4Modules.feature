@AM
Feature: All Modules - Verify duplicates in the product name across PA, NA, TR and BR

Scenario:  Verify if there is any duplication in product names across 4 modules PA, NA, TR and BR
	When user executes feed validation AMNameDupforModuleQuery sql in DB
	And user verify feed validation and log the results for the query AMNameDupforModuleQuery
	
