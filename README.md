# NLTech OpenCart Automation Framework

A comprehensive Selenium-based automation framework for testing the OpenCart e-commerce application using Java, TestNG, Maven, and modern reporting tools.

## Overview

This project is designed to automate functional UI testing for the OpenCart application. It follows a structured, maintainable architecture built around the Page Object Model (POM), reusable utility methods, browser configuration management, and test reporting integration.

The framework supports:
- Login and account flow automation
- Registration flow testing
- Search and product information validation
- Multi-browser execution
- Environment-based configuration
- Parallel execution support
- Screenshot capture on failures
- Detailed logs and Allure/ChainTest reporting
- Remote WebDriver execution via Selenium Grid
- CI/CD integration with Jenkins

## Tech Stack

- Java 17
- Selenium WebDriver 4.46.0
- TestNG 7.12.0
- Maven
- Apache POI for Excel support
- OpenCSV for CSV support
- Log4j2 for logging
- Allure for reporting
- ChainTest for enhanced reporting
- AspectJ for weaving support

## Project Structure

```text
NLTechOpenCartAutomation/
├── .gitignore
├── README.md
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── qa/
│   │   │           └── opencart/
│   │   │               ├── constants/
│   │   │               │   └── AppConstants.java
│   │   │               ├── exceptions/
│   │   │               │   ├── ElementException.java
│   │   │               │   └── FrameworkException.java
│   │   │               ├── factory/
│   │   │               │   ├── DriverFactory.java
│   │   │               │   └── OptionsManager.java
│   │   │               ├── listeners/
│   │   │               │   ├── AnnotationTransformer.java
│   │   │               │   ├── Retry.java
│   │   │               │   └── TestAllureListener.java
│   │   │               ├── pages/
│   │   │               │   ├── AccountPage.java
│   │   │               │   ├── LoginPage.java
│   │   │               │   ├── ProductInfoPage.java
│   │   │               │   ├── RegisterPage.java
│   │   │               │   └── SearchResultsPage.java
│   │   │               └── utils/
│   │   │                   ├── AppError.java
│   │   │                   ├── CSVUtils.java
│   │   │                   ├── ElementUtils.java
│   │   │                   ├── ExcelUtil.java
│   │   │                   └── StringUtils.java
│   │   └── resources/
│   │       └── log4j2.properties
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── qa/
│       │           └── opencart/
│       │               ├── base/
│       │               │   └── BaseTest.java
│       │               └── tests/
│       │                   ├── AccountPageTest.java
│       │                   ├── LoginPageTest.java
│       │                   ├── ProductInfoTest.java
│       │                   ├── RegisterPageTest.java
│       │                   └── SearchProductTest.java
│       └── resources/
│           ├── chaintest.properties
│           ├── config/
│           │   ├── config.properties
│           │   ├── config_dev.properties
│           │   ├── config_stage.properties
│           │   └── config_uat.properties
│           ├── testdata/
│           │   ├── UserRegistration.xlsx
│           │   └── registration.csv
│           └── testrunners/
│               ├── testng_regression.xml
│               └── testng_sanity.xml
```

## Framework Components

### 1. Driver Factory
The `DriverFactory` class initializes the browser driver based on the configured browser and application environment. It also manages ThreadLocal WebDriver for parallel execution and captures screenshots when needed.

Key responsibilities:
- Browser initialization (Chrome, Firefox, Edge, Safari)
- Properties loading for environment configuration
- WebDriver retrieval using ThreadLocal
- Screenshot file generation
- Remote WebDriver setup for Selenium Grid execution

### 2. Page Object Model
Each page of the application is represented by a dedicated Java class inside `src/main/java/com/qa/opencart/pages`.

Examples:
- `LoginPage.java`
- `AccountPage.java`
- `RegisterPage.java`
- `SearchResultsPage.java`
- `ProductInfoPage.java`

These page classes handle:
- Locator definitions
- Page-specific actions
- Page validations and navigations

### 3. Utility Layer
The `utils` package contains reusable support classes for element interaction, data handling, string operations, and error handling.

Important utilities:
- `ElementUtils.java`: custom WebDriver helper methods and wait logic
- `ExcelUtil.java`: Excel-driven data handling
- `CSVUtils.java`: CSV parsing support
- `AppError.java`: central error message constants
- `StringUtils.java`: string manipulation helpers

### 4. Base Test Class
`BaseTest.java` provides the foundation for all tests:
- Browser setup
- Driver initialization
- Page object creation
- Cleanup after tests
- Failure screenshot attachment

### 5. Listeners and Reporting
The framework includes listener classes to improve test execution and reporting:
- `TestAllureListener.java`: integrates with Allure reports
- `AnnotationTransformer.java`: supports test retry logic
- `Retry.java`: custom retry implementation for flaky tests

### 6. Configuration Management
Environment-specific properties are externalized under `src/test/resources/config/`.

Default configuration file:
- `config.properties`

Available environment files:
- `config_dev.properties`
- `config_stage.properties`
- `config_uat.properties`

This helps in running the same tests against different environments without changing source code.

## Dependencies and Why They Are Used

### Selenium Java
Used for browser automation and interaction with the OpenCart UI.

### TestNG
Used to organize tests, run them with annotations, and support parameterization, listeners, and parallel execution.

### Apache POI
Used to read data from Excel files such as `UserRegistration.xlsx` for data-driven testing.

### OpenCSV
Used to process CSV-based test inputs, which helps in running registration and other test cases with external data.

### Log4j2
Used to generate execution logs for debugging and monitoring test runs.

### Allure / ChainTest
Used to capture execution reports and improve visibility into pass/fail results, screenshots, and steps.

## Setup Instructions

### Prerequisites
- Java 17 or above
- Maven 3.8+
- Git
- Browser drivers compatible with the installed browser version

### Clone the Repository
```bash
git clone https://github.com/Adarsh-Kumar-M/NLTechOpenCartAutomation.git
cd NLTechOpenCartAutomation
```

### Install Dependencies
```bash
mvn clean install
```

### Verify Build
```bash
mvn test
```

## Configuration Details

The default app configuration is stored in:
```text
src/test/resources/config/config.properties
```

Example content:
```properties
browser=chrome
url=https://opencart.nltechtrainings.com/en-gb?route=account/login
username=nltech01@gmail.com
password=admin@123
headless=true
incognito=true
remote=true
huburl=http://localhost:4444/wd/hub
```

### Environment Selection
The framework supports environment selection using `-Denv` property. For example:
```bash
mvn test -Denv=uat
```

The appropriate property file is selected by `DriverFactory.initProp()`.

## Browser Setup

The `OptionsManager` class configures browser-specific options such as:
- Headless mode
- Incognito/private mode
- Browser flags and capability setup

The framework supports:
- Chrome
- Firefox
- Edge
- Safari

## Test Execution

### Run All Tests
```bash
mvn test
```

### Run a Specific Test Class
```bash
mvn test -Dtest=LoginPageTest
```

### Run a Specific Test Method
```bash
mvn test -Dtest=LoginPageTest#testLogin
```

### Run XML Suite
```bash
mvn test -DsuiteXmlFile=src/test/resources/testrunners/testng_regression.xml
```

### Run with Custom Browser
```bash
mvn test -Dbrowser=firefox
```

### Run with Remote WebDriver (Selenium Grid)
To execute tests on a remote Selenium Grid, set `remote=true` in the config file and provide the hub URL:
```properties
remote=true
huburl=http://localhost:4444/wd/hub
```

Alternatively, you can override via command line:
```bash
mvn test -Dremote=true -Dhuburl=http://your-selenium-grid:4444/wd/hub
```

## Test Suite Files

The project contains TestNG XML files under `src/test/resources/testrunners/`:
- `testng_regression.xml`
- `testng_sanity.xml`

These suites help organize regression and sanity testing flows and can be used for parallel execution.

## Logging

The project uses Log4j2 with configuration stored in `src/main/resources/log4j2.properties`.

The logger writes logs to:
- Console
- File: `logs/openCart.log`

This makes debugging test failures easier and improves transparency during execution.

## Reporting

The framework integrates with:
- Allure for standard HTML-based test reports
- ChainTest for extended reporting and test chaining

The listeners are configured in the TestNG XML files and capture result-level information automatically.

## Common Features Covered by the Framework

- User login testing
- Registration validation
- Product search validation
- Product detail page verification
- Navigation across pages in the ecommerce flow
- Data-driven test execution using Excel/CSV
- Screenshot on failure
- Custom wait and element utilities
- Remote execution via Selenium Grid
- CI/CD pipeline integration

## Design Pattern Used

This project follows the Page Object Model pattern, which separates:
- Test logic from page logic
- UI locators from actual test actions
- Reusable helper methods from business-level validation

This improves maintainability and reduces duplication.

## Best Practices Implemented

- Centralized configuration
- Reusable helper methods
- Object-oriented page classes
- Explicit waits instead of hardcoded sleeps where possible
- Custom exceptions for framework-level validation
- Screenshot capture on failures
- Logging for execution details
- Externalized test data

## CI/CD Integration with Jenkins

The framework is integrated with Jenkins for automated test execution in CI/CD pipelines. The Jenkins pipeline can be configured to:

- Trigger builds on code changes or scheduled intervals
- Execute tests in parallel across multiple environments
- Generate and publish Allure reports
- Send email notifications on build success/failure
- Archive test artifacts and logs

### Jenkins Pipeline Configuration

A typical Jenkins pipeline for this framework includes:

1. **Build Stage**: Maven clean install
2. **Test Stage**: Execute TestNG suites with environment selection
3. **Report Stage**: Generate Allure reports
4. **Notification Stage**: Send build status notifications

Example pipeline command:
```bash
mvn clean test -Denv=stage -DsuiteXmlFile=src/test/resources/testrunners/testng_regression.xml
```

## Notes

This is an educational and practical Selenium framework built for OpenCart automation. It is suitable for both learning and extending into a production-ready test suite with enhancements such as:
- Cloud execution (BrowserStack/Sauce Labs)
- More robust reporting
- More page coverage
- API level validation integration
- Docker containerization for Selenium Grid

## License

This project is currently a personal/public repository without a specific license file. 

## Contact

Repository: https://github.com/Adarsh-Kumar-M/NLTechOpenCartAutomation

## Summary

The NLTech OpenCart Automation Framework is a Java-based Selenium TestNG framework for end-to-end UI testing of the OpenCart application. It uses a solid design structure, useful utilities, configurable environments, extensive logging, and reporting capabilities to support maintainable, scalable automation testing.
