@AM
Feature: All Modules - Verify duplicates in the product url across PA, NA, TR and BR

Scenario:  Verify if there is any duplication in product URLs across 4 modules PA, NA, TR and BR
	When user executes feed validation AMDupforModuleQuery sql in DB
	And user verify feed validation and log the results for the query AMDupforModuleQuery
	
