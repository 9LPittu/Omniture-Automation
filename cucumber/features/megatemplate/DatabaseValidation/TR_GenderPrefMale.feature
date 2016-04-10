@TR
Feature: Top Rated - Gender Prefrence Male

Scenario:  Top Rated_Validate if men's gender score is 1 all the items should displayed for men in the feeds
	When user executes feed validation feedTopratedMenGenderQuery sql in DB
   And user verify feed validation and log the results for the query feedTopratedMenGenderQuery
