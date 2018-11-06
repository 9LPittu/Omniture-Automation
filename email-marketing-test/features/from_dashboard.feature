@Initialize
@dashboard_email_marketing_test
Feature: Test Marketing Emails

  Scenario Outline: Test Marketing Emails
    When I open "Branding Email Delivery Dash" Dashboard
#    When I extract all tickets which are assigned to QA for entire month in "<dashboard>" Dashboard
    Then I should be able to test following metrics of each ticket:
      | SUBJECT           |
      | PRE-HEADER        |
      | PRE-HEADER LINK   |
      | DISCLAIMER COPY   |
      | ADDITIONAL LINKS  |
      | IMAGES            |
      | EXCEL SHEET LINKS |

    Examples:
      | dashboard               |
      | J.Crew Email Delivery   |
#      | Factory Email Delivery  |
#      | Madewell Email Delivery |