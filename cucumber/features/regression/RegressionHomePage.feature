@Navigate
Feature: Homepage Embedded Header and Footer

  Background:
    Given User is on homepage

  #Removed, we had a copy in RegressionFooter.feature
  #Scenario: Contact Us Page Header Footer Links

  #Moved to RegressionHeader.feature
  #Scenario: Home Page header Links

  #Removed, we had a copy in RegressionHeader.feature
  #Scenario: Menu is functional

  Scenario: Homepage embedded Header
    Then Verify embedded headers are visible and functional

  Scenario: Homepage embedded Footer
    Then Verify embedded footer is visible and functional