@BR
Feature: Browse By - Purchage Products are excluded 

Scenario:  Browse By_Make sure all purchased products are excluded
	When user executes feed validation feedBrowseProductPurchageProuductsExecludedQuery sql in DB
	And user verify feed validation and log the results for the query feedBrowseProductPurchageProuductsExecludedQuery
					