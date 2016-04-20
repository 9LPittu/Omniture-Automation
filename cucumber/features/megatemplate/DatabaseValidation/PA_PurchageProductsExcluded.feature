@PA
Feature: Popular in Your Area - Purchage Products are excluded 

Scenario:  Popular in Your Area_Make sure all purchased products are excluded
	When user executes feed validation feedPopularInYourAreaPurchageProuductsExecludedQuery sql in DB
	And user verify feed validation and log the results for the query feedPopularInYourAreaPurchageProuductsExecludedQuery
					