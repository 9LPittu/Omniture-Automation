@Initialize
@emailmarketing
Feature: Test Marketing Emails

  Scenario Outline: Test Marketing Emails
    When I open "<ticket>"
    Then I should be able to extract all fields from jira ticket
    And I download all attachments
    When I open email in browser
    Then I should see email in browser
#    And I validate "SUBJECT" in email
    And I validate "PRE-HEADER" in email
    And I validate "PRE-HEADER LINK" in email
    And I validate "DISCLAIMER COPY" in email
    And I validate "ADDITIONAL LINKS" in email
    And I validate all images
    And I compare all links in email with excel sheet and verify there is no broken link
#    And I validate "Source Code" in each link

    Examples:
      | ticket    | 
#      | FAD-7427 | 
      | JCBEMAIL-4771 |