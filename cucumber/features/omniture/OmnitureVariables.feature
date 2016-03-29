@Omniture

Feature: Omniture variables check

  Scenario: Visit sites from secondary map from /sitemap-index.xml
    Given User opens stream to https://www.jcrew.com/sidecar/sitemap-index.xml page
    When Select sitemaps to check
    And Select urls to check
    And Exclude url https://jobs.jcrew.com/ from list
    And Exclude url https://jobs.jcrew.com/?sidecar=true from list
    And Exclude url https://stores.jcrew.com/? from list
    Then All pages should contain the following variables
       #|variable name|value|  <---- any in value field will check that the variable is not empty
        |s.pageName|any|
        |s.prop69  |sidecar|