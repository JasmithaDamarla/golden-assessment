Feature: execute associate service

  Scenario: get associates by gender
    Given associates "M"
    When requested to find associates
    Then the status code should be 200
