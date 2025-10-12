# QualiFrameV2 Configuration Guide

## Overview
This guide provides configuration examples for different testing scenarios using the Global Settings.properties file.

## 🔧 Configuration Templates

### 1. API Testing Only
```properties
# API TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=API
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=False
TakeScreenshotPassedStep=False
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://api.example.com
DataTable=APIModularScenario

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=API Test Suite
HtmlReport=True
```

### 2. Selenium Web Testing Only
```properties
# SELENIUM WEB TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=LOCALChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# Browser Options
# LOCALChrome, LOCALFirefox, LOCALEdge, LOCALChrome_HEADLESS

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Web Test Suite
HtmlReport=True
```

### 3. Playwright Web Testing Only
```properties
# PLAYWRIGHT WEB TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=LOCALChrome
AutomationFramework=PLAYWRIGHT
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# Playwright Specific Settings
PlaywrightHeadless=false
PlaywrightSlowMo=0
PlaywrightTimeout=30000
PlaywrightNavigationTimeout=30000
PlaywrightViewportWidth=1920
PlaywrightViewportHeight=1080
PlaywrightRecordVideo=false
PlaywrightTracing=false
PlaywrightLocale=en-US

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Playwright Test Suite
HtmlReport=True
```

### 4. Mobile Testing (Appium)
```properties
# MOBILE TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=AppiumNativeAndroid
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
defaultEnvironment=qa
DataTable=MobileModularScenario

# Mobile Options
# AppiumNativeAndroid, AppiumWebAndroid, AppiumNativeIOS, AppiumWebIOS
# Note: Mobile detection is based on TestConfigurationID containing "Appium"

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Mobile Test Suite
HtmlReport=True
```

### 5. Windows Application Testing
```properties
# WINDOWS APPLICATION TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=WINDOWSEXECUTION
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
DataTable=WebModularScenario

# Windows Settings
WindowsAppPath=C:\\Windows\\System32\\notepad.exe
WindowsMachineURL=http://127.0.0.1
WindowsMachinePort=4723
WindowsPlatformName=Windows
WindowsDeviceName=WindowsPC

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Windows App Test Suite
HtmlReport=True
```

### 6. Cloud Testing - SauceLabs
```properties
# SAUCELABS CLOUD TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=SaucelabsWebChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=False
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# SauceLabs Settings
SauceUserName=your_username
SauceAccessKey=your_access_key
SauceBuildName=QualitestBuildName
SauceTestName=QualitestTestName
SauceVisualRunner=False

# Options: SaucelabsWebChrome, SaucelabsWebFirefox, SaucelabsMobileAndroid, SaucelabsMobileIOS

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=SauceLabs Test Suite
HtmlReport=True
```

### 7. Cloud Testing - LambdaTest
```properties
# LAMBDATEST CLOUD TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=LAMBDATESTWebChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=False
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# LambdaTest Settings
LambdaTestUserName=your_username
LambdaTestAccessKey=your_access_key
LambdaTestBuildName=QualitestBuildName
LambdaTestProjectName=QualitestProjectName
LambdaTestTestName=QualitestTestName

# Options: LAMBDATESTWebChrome, LAMBDATESTWebFirefox, LAMBDATESTMobileAndroid, LAMBDATESTMobileIOS

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=LambdaTest Suite
HtmlReport=True
```

### 8. Cloud Testing - BrowserStack
```properties
# BROWSERSTACK CLOUD TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=BROWSERSTACKWebChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=False
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# BrowserStack Settings
BrowserStackUserName=your_username
BrowserStackAccessKey=your_access_key
BrowserStackProjectName=QualitestProjectName
BrowserStackBuildName=QualitestBuildName
BrowserStackSessionName=QualitestSessionName

# Options: BROWSERSTACKWebChrome, BROWSERSTACKWebFirefox, BROWSERSTACKMobileAndroid, BROWSERSTACKMobileIOS

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=BrowserStack Test Suite
HtmlReport=True
```

### 9. Hybrid Testing (Web + API)
```properties
# HYBRID TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=LOCALChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Hybrid Test Suite
HtmlReport=True
```

### 10. Visual Testing with Applitools
```properties
# VISUAL TESTING CONFIGURATION
ExecutionApproach=CUCUMBER
TestData=EXCEL
TestConfigurationID=LOCALChrome
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=True
TakeScreenshotPassedStep=True
Timeout=30
defaultEnvironment=qa
qaAppUrl=https://www.saucedemo.com/
DataTable=WebModularScenario

# Applitools Settings
AppliTools=True
AfterStepAppliTools=True
APIKey=your_applitools_api_key
BatchInfo=Visual Test Suite

# Reporting
SaveReports=True
Allure-Report=True
ProjectName=Visual Test Suite
HtmlReport=True
```

## 📋 Configuration Reference

### TestConfigurationID Options

#### Local Execution
- `LOCALChrome` - Chrome browser
- `LOCALFirefox` - Firefox browser  
- `LOCALEdge` - Edge browser
- `LOCALChrome_HEADLESS` - Headless Chrome
- `LOCALChromeEmulation` - Chrome mobile emulation

#### Mobile Execution
- `AppiumNativeAndroid` - Native Android app
- `AppiumWebAndroid` - Android web browser
- `AppiumNativeIOS` - Native iOS app
- `AppiumWebIOS` - iOS web browser

#### Cloud Execution
- `SaucelabsWebChrome` - SauceLabs Chrome
- `LAMBDATESTWebChrome` - LambdaTest Chrome
- `BROWSERSTACKWebChrome` - BrowserStack Chrome
- `AWSDEVICEFARMWebChrome` - AWS Device Farm

#### Special Execution
- `WINDOWSEXECUTION` - Windows applications
- `API` - API testing only

### AutomationFramework Options
- `SELENIUM` - Selenium WebDriver (also used for Appium mobile testing)
- `PLAYWRIGHT` - Microsoft Playwright

Note: Mobile testing uses SELENIUM as AutomationFramework, with mobile detection based on TestConfigurationID

### TestData Options
- `EXCEL` - Excel files (.xls, .xlsx)
- `CSV` - CSV files
- `JSON` - JSON files
- `ACCESSDB` - Access database
- `MSSQL` - MySQL database

### ExecutionApproach Options
- `CUCUMBER` - Cucumber BDD
- `KEYWORD` - Keyword-driven
- `MODULAR` - Modular approach

## 🚀 Quick Setup Commands

### Switch to API Testing
```properties
TestConfigurationID=API
AutomationFramework=SELENIUM
TakeScreenshotFailedStep=False
TakeScreenshotPassedStep=False
```

### Switch to Playwright
```properties
AutomationFramework=PLAYWRIGHT
PlaywrightHeadless=false
```

### Switch to Mobile
```properties
TestConfigurationID=AppiumNativeAndroid
AutomationFramework=SELENIUM
DataTable=MobileModularScenario
```

### Switch to Cloud
```properties
TestConfigurationID=SaucelabsWebChrome
# Add cloud credentials
```

## 💡 Best Practices

1. **Environment Management**: Use different property files for different environments
2. **Credential Security**: Store sensitive data in environment variables
3. **Screenshot Strategy**: Enable screenshots for debugging, disable for performance
4. **Timeout Configuration**: Adjust timeouts based on application response times
5. **Data Management**: Use appropriate data sources for test complexity