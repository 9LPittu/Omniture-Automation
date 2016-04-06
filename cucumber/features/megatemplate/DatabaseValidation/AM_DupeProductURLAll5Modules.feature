@AM
Feature: All Modules - Verify duplicates in the product url across 5 CW,PA, NA, TR and BR

Scenario:  Verify if there is any duplication in product URLs across 5 modules CW,PA, NA, TR and BR
	When user executes feed validation AMDupProductURLQuery sql in DB
	And user verify feed validation and log the results for the query AMDupProductURLQuery
	
