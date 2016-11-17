@Footer
Feature: Header and Footer - Let us help you section

Background:
  Given User is on homepage with clean session
  And User closes email capture

  Scenario Outline: Verify links displayed under Let us help you section are functional

    When Verify <footer_link> link is displayed under <header_name> in footer
    And User clicks on <footer_link> link under <header_name> in footer
    Then Verify user is navigated to <footer_link> page


  Examples:
  | header_name | footer_link |
  | Let Us Help You| Order Status|

#    And Verify Order Status link is displayed under Let Us Help You accordion in footer
#    When User clicks on Order Status link under Let Us Help You accordion in footer



