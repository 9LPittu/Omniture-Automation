@megaPDP
Feature: Megatemplate PDP page Validation

Scenario: Browseby URL validation
    When user executes browsebyquery sql query in DB
	And user navigates to url and should see the PDP page
	
Scenario: Newly Added URL validation
    When user executes newlyaddedquery sql query in DB
	And user navigates to url and should see the PDP page
	
Scenario: Famous in Your Area URL validation
    When user executes famousareaquery sql query in DB
	And user navigates to url and should see the PDP page
	
Scenario: Top Rated URL validation
    When user executes topratedquery sql query in DB
	And user navigates to url and should see the PDP page