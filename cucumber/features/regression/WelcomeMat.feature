@WelcomeMat
Feature: Welcome Mat for ROW

#US15294_TC01,US15294_TC03,US15294_TC04,US15294_TC05
  Scenario Outline: Welcome mat display validation for international pages
    Given User is on clean session international <country_group> page
      | PDP      |
      | Category |
      | Sale     |
      | Login    |
    And Welcome mat page is displayed
    And Welcome mat header message is displayed as Hello, Canada for canada, Around the World for the ROW
    And JCrew Logo is present on the welcome mat
    And Corresponding country name and flag is displayed
    And Welcome mat content is displayed
    And User clicks on START SHOPPING on welcome mat
    And user should see selected country in the footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |

#US15294_TC02, (for US, welcome mat should not be displayed.for canada, it will but with diff message)
  Scenario: Welcome mat display validation for US
    Given User is on homepage with clean session
    And Welcome mat page is not displayed
    And Verify user is in homepage

#US15294_TC08
  Scenario Outline: Validation of Take me to the U.S. site. link functionality
    Given User is on clean session international <country_group> page
      | PDP      |
      | Category |
      | Sale     |
      | Login    |
    And Welcome mat page is displayed
    And User clicks on Take me to the U.S. site. on welcome mat
    And user should see "United States" in footer

    Examples:
      | country_group |
      | PRICEBOOK     |
      | NON-PRICEBOOK |
