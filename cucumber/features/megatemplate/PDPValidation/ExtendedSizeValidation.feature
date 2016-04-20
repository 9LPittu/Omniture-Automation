@megaExtendedSize
Feature: Megatemplate Extended Sizes validation

Scenario: Extended Size validation for Famous in Your Area URLs
    When user executes extendedFamousAreaQuery sql query in DB
	And user navigates to url and should see the PDP page and not see extended size	

Scenario: Extended Size validation for Top Rated URLs
    When user executes extendedTopRatedQuery sql query in DB
	And user navigates to url and should see the PDP page and not see extended size
	
Scenario: Extended Size validation for New Arrivals URLs
    When user executes extendedNewArrivalQuery sql query in DB
	And user navigates to url and should see the PDP page and not see extended size		