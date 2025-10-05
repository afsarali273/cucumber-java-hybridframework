@webcrawler
Feature: To generate or execute automated tests by web crawling

  @e2e
  Scenario Outline: Autonomous Functional and Non-Functional test generation
    Given user launch the application "<url>" with "<applitoolsEnable>"
    When user crawl the web page
    Then compare crawl output with "<crawlBaselineReport>" by status
      | False |
    And verify dead link and redirects for url present in "<detailReport>"
    And identify new broken link and redirect issues compare with baseline "<redirectValidationReport>"
      | False |
    And extract lighthouse report URL present in "<webCrawlReportlist>" and generate consolidated report
    And compare lighthouse report with baseline "<lightHouseReport>"
      | False |
    And Generate functional test script for url present in "<url>"

    Examples: 
      | url                           | applitoolsEnable | crawlBaselineReport | webCrawlReportlist  | lightHouseReport                                                            | redirectValidationReport       | detailReport       |
      | https://www.bioreference.com/ | False            | WebCrawlReport.csv  | ListOfUniqueURL.txt | LighthouseConsolidatedCSVreport.csv^LighthouseConsolidatedCSVreport_new.csv | DeadLinkRedirectValidation.csv | WebCrawlReport.csv |

  @outputComparison
  Scenario Outline: Compare web crawl output with baseline data
    Given user launch the application "<url>" with "<applitoolsEnable>"
    When user crawl the web page
    And compare crawl output with "<CrawlBaselineReport>" by status
      | False |

    Examples: 
      | url                  | applitoolsEnable | CrawlBaselineReport | webCrawlReportlist   |
      | https://www.kay.com/ | false            | WebCrawlReport.csv  | https://www.kay.com/ |

  @lighthouse
  Scenario Outline: To generate LightHouse report
    Given extract lighthouse report URL present in "<filename>" and generate consolidated report

    Examples: 
      | filename           |
      | LighthouseURLs.txt |

  @applitools
  Scenario Outline: AppliTools User Interface Validation
    Given validate UI for list of URL in "<filename>"

    Examples: 
      | filename           |
      | LighthouseURLs.txt |

  @deadlinkreport
  Scenario Outline: URL Redirection & Deadlink validation
    Given verify dead link and redirects for url present in "<filename>"

    Examples: 
      | filename          |
      | RedirectLinks.txt |

  @GenerateTest
  Scenario Outline: Autonomous Functional Test script generation
    Given Generate functional test script for url present in "<webCrawlReportlist>"

    Examples: 
      | webCrawlReportlist          |
      | https://www.unilever.co.uk/ |

  @ExecuteTestScript
  Scenario Outline: Autonomous Function Test script execution <TestScript_Name>
    Given Execute functional "<TestScript_Name>" using "<InputJson_Path>"

    Examples: 
      | InputJson_Path        | TestScript_Name                                          |
      | ScenarioOutput_1.json | TestCase - 024 Equity, Diversity & Inclusion at Unilever |

  @ExecuteTest
  Scenario Outline: Autonomous Function Test script execution
    Given Execute functional test script using "<InputJson_Path>"

    Examples: 
      | InputJson_Path        |
      | ScenarioOutput_1.json |

  @cookieValidation
  Scenario Outline: Cookie validation for web page
    Given validate cookie for URL present in "<webCrawlReport>"

    Examples: 
      | webCrawlReport   |
      | DetailReport.csv |

  @redirectValidationReport
  Scenario Outline: Redirect report comparison
    Given identify new broken link and redirect issues compare with baseline "<redirectValidationReport>"

    Examples: 
      | redirectValidationReport |
      | Redirectbasereport.csv   |

  @lightHouseReportComparison
  Scenario Outline: LightHouse report comparison with baseline
    Given compare lighthouse report with baseline "<lightHouseReport>"
      | True |

    Examples: 
      | lightHouseReport                                                            |
      | LighthouseConsolidatedCSVreport.csv^LighthouseConsolidatedCSVreport_new.csv |

  @summaryreport
  Scenario Outline: Generate Summary report
    Given generate summary report with "<destination_directory>" and "<base_url>"

    Examples: 
      | base_url                           | destination_directory |
      | https://www.iatinsurancegroup.com/ | 2022_03_01_09_05_30   |
