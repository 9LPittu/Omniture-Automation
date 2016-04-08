@megaImagePDP
Feature: Megatemplate PDP Validation

Scenario: Browseby URL validation
    When user executes imagebrowsebyquery sql query in DB
	And user navigates to url and should see the Image
	
Scenario: Newly Added URL validation
    When user executes imagenewlyaddedquery sql query in DB
	And user navigates to url and should see the Image
	
Scenario: Famous in Your Area URL validation
    When user executes imagefamousareaquery sql query in DB
	And user navigates to url and should see the Image
	
Scenario: Top Rated URL validation
    When user executes imagetopratedquery sql query in DB
	And user navigates to url and should see the Image