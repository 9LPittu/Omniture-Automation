@TR
Feature: Top Rated - Purchage Products are excluded 

Scenario:  Top Rated_Make sure all purchased products are excluded
	When user executes feed validation feedTopRatedPurchageProuductsExecludedQuery sql in DB
	And user verify feed validation and log the results for the query feedTopRatedPurchageProuductsExecludedQuery
					