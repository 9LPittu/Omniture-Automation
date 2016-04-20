@TR
Feature: Top Rated - Gender prefernce for Kids 

Scenario: Top Rated_Validate if kid's gender score is 1 all the items should displayed for boys/girls in the feeds
   	When user executes feed validation feedPopularinYourAreaGenderPrefQuery sql in DB
   And user verify feed validation and log the results for the query feedPopularinYourAreaGenderPrefQuery
