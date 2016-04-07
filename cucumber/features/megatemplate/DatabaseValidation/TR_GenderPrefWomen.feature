@TR
Feature: Top Rated - Gender Preference Women 

Scenario:  Top Rated_Validate if Women's gender score is 1 all the items should displayed for women in the feeds
	When user executes feed validation feedTopratedWomenGenderQuery sql in DB
   And user verify feed validation and log the results for the query feedTopratedWomenGenderQuery
