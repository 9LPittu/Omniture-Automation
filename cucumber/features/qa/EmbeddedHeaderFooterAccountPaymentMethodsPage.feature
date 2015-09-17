@EmbeddedHeaderFooterAccountPaymentMethodsPage
Feature: Embedded Header Footer Payment Methods Preferences Page

  Scenario: Account Payment Methods Page Header Footer Links
    Given User is on homepage
    And Goes to sign in page
    When User enters account@test.org as email
    And User enters test1234 as password
    And Hits sign in button
    Then User is in My Account page
    And User clicks on PAYMENT METHODS link in My Account Page
    And User should be in payment_info.jsp menu link page
    Then Verify embedded header and footer are visible and functional