@Omniture

Feature: Omniture variables check

  Scenario: Visit sites from secondary map from /sitemap-index.xml
    Given User opens stream to https://www.jcrew.com/sitemap-index.xml page
    When Select sitemaps to check
    And Select urls to check
    And Exclude url https://jobs.jcrew.com/ from list
    And Exclude url https://jobs.jcrew.com/?sidecar=true from list
    And Exclude url https://stores.jcrew.com/? from list
    Then All pages should contain s.pageName variable