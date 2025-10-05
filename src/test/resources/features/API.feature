Feature: Validating Place API

  @api
  Scenario Outline: Verify if employee is getting addded succesfully using create API
    Given Access "<url>" with "<headers>" for "<tc_id>"
    When Send "<method>"
    Then verify request is success with status "<code>"
    And verify "status" in response body is "success"

    Examples: 
      | tc_id            | url                | headers | method | code |
      | APIPracticeTest1 | GetEmployeeDetails | NA      | GET    |  200 |

  Scenario Outline: Verify if employee is getting addded succesfully using create API
    Given Access "<url>" with "<headers>" for "<tc_id>"
    When Send "<method>" with request body "<payload>"
    Then verify request is success with status "<code>"
    And verify "status" in response body is "success"
    And verify "data.id" in response body is "2225"

    Examples: 
      | tc_id            | url               | headers | method | payload         | code |
      | APIPracticeTest2 | AddEmployeeDetail | NA      | POST   | EmployeesDetail |  200 |

	@grpc
  Scenario Outline: Sample GRPC request
    Given login sample application using grpc request for "<tc_id>"

    Examples: 
      | tc_id   |
      | gRPC001 |
