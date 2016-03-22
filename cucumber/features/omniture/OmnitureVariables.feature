@Omniture

Feature: Omniture variables check

  Scenario: Visit sites from secondary map from /sitemap-index.xml
    Given User goes https://www.jcrew.com/sitemap-index.xml page
    When Select pages to check
    Then All pages should contain s.pageName variable