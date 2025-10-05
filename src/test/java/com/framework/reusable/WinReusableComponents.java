///*
// *  © [2022] Qualitest. All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http:www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.framework.reusable;
//
//import com.framework.components.FrameworkParameters;
//import com.framework.components.ScriptHelper;
//import com.framework.components.Settings;
//import com.framework.cucumber.DriverManager;
//import com.framework.data.FrameworkDataTable;
//import com.framework.report.Status;
//import com.framework.selenium.CustomDriver;
//import io.appium.java_client.windows.WindowsDriver;
////import io.appium.java_client.windows.WindowsElement;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.interactions.Actions;
//
//import java.net.HttpURLConnection;
//import java.util.Properties;
//
///**
// * Abstract base class for reusable libraries created by the user
// *
// * @author Qualitest
// */
//public abstract class WinReusableComponents extends GenericResuableComponents {
//
//    private int responseStatus;
//    private HttpURLConnection httpURLConnect;
//
//    /**
//     * The {@link FrameworkDataTable} object (passed from the test script)
//     */
//    protected FrameworkDataTable dataTable;
//
//    /**
//     * The {@link CustomDriver} object
//     */
//    protected WindowsDriver driver;
//
//    /**
//     * The {@link WindowsDriver} object (required for calling one reusable library
//     * from another)
//     */
//
//    protected ScriptHelper scriptHelper;
//
//    /**
//     * The {@link Properties} object with settings loaded from the framework
//     * properties file
//     */
//    protected Properties properties;
//
//    /**
//     * The {@link FrameworkParameters} object
//     */
//    protected FrameworkParameters frameworkParameters;
//
//    /**
//     * Constructor to initialize the {@link ScriptHelper} object and in turn the
//     * objects wrapped by it
//     *
//     * @param scriptHelper
//     *            The {@link ScriptHelper} object
//     */
//
//
//     public WinReusableComponents(ScriptHelper scriptHelper) {
//     this.scriptHelper = scriptHelper;
//     if (scriptHelper != null) {
//     this.dataTable = scriptHelper.getDataTable();
//     this.report = scriptHelper.getReport();
//      this.driver = scriptHelper.getcustomDriver().getWinDriver();
//
//     properties = Settings.getInstance();
//
//      this.driver = scriptHelper.getcustomDriver().getWinDriver();
//
//     frameworkParameters = FrameworkParameters.getInstance();
//     }
//
//     }
//
//     /**
//     * Constructor to initialize Windows driver based on the parameters
//     */
//     public WinReusableComponents() {
//
//     this.driver = DriverManager.getWindowsDriver();
//
//     }
//    /**
//     * Function that returns a Windows Element based on the supplied AutomationObject
//     *
//     * @param localLocator
//     *           AutomationObject reflecting the desired WebElement
//     * @return WebElement
//     * @throws Exception
//     */
//    private static WindowsElement getElement(By localLocator) throws Exception {
//        return getElement(localLocator);
//    }
//    /**
//     * Function to get a text from an object
//     *
//     * @return String - Text contained within the object
//     * @throws Exception
//     */
//    public String getText(By Locator) throws Exception {
//        return getElement(Locator).getText();
//    }
//    /**
//     * Function to click element when it is visible
//     *
//     * @param buttonName {@link WindowsDriver} locator used to identify the element //
//     *           * @param name of the element
//     */
//
//     public void clickButtonbyName(String buttonName) {
//
//    try {
//        driver.findElementByName(buttonName).click();
//        addTestLog("Click Element", "The element clicked successfully - " + buttonName, Status.PASS);
//    }catch(Exception e) {
//
//        addTestLog("Click Element ", "The element not clicked successfully - " + buttonName + ". Error message - " + e.getMessage(),
//                Status.FAIL);
//         }
//    }
//    /**
//     * Function to click element when it is visible
//     *
//     * @param accessibleID {@link WindowsDriver} locator used to identify the element //
//     *           * @param name of the element
//     */
//
//    public void clickButtonbyAccessibleID(String accessibleID) {
//
//        try {
//
//        driver.findElementByAccessibilityId(accessibleID).click();
//            addTestLog("Click Element", "The element clicked successfully - " + accessibleID, Status.PASS);
//         //   Actions oaction = new Actions(driver);
//
//
//        }catch(Exception e) {
//
//            addTestLog("Click Element ", "The element not clicked successfully - " + accessibleID + ". Error message - " + e.getMessage(),
//                    Status.FAIL);
//        }
//    }
//    /**
//     * Function to enter Text in the field
//     *
//     * @param accessibleID {@link WindowsDriver} locator used to identify the element //
//     *           * @param accessibleID of the element
//     */
//        public void enterText(String text, String accessibleID) {
//
//        try {
//
//            driver.findElementByAccessibilityId(accessibleID).sendKeys(text);
//            addTestLog("Enter Text", "The text entered successfully - " + accessibleID, Status.PASS);
//        }catch(Exception e) {
//
//            addTestLog("Enter Text ", "The text not entered successfully - " + accessibleID + ". Error message - " + e.getMessage(),
//                    Status.FAIL);
//        }
//    }
//    /**
//     * Function to get Text from the field
//     *
//     * @param accessibleID {@link WindowsDriver} locator used to identify the element //
//     *           * @param accessibleID of the element
//     */
//    public String getText( String accessibleID) {
//        String expectedText = null;
//        try {
//
//         expectedText =  driver.findElementByAccessibilityId(accessibleID).getText();
//         addTestLog("Get Text", "The get Text successful - " + accessibleID, Status.PASS);
//        }catch(Exception e) {
//
//            addTestLog("Get Text ", "The getText not successful - " + accessibleID + ". Error message - " + e.getMessage(),
//                    Status.FAIL);
//        }
//        return expectedText;
//    }
//
//
//    /**
//     * Function to send a keyboard key to the application
//     *
//     * @param sKeys
//     *           String - The key to send to the application. Valid options are: CTRL, CTRLDN, CTRLUP, ALT, ALTDN, ALTUP, SHIFTDN, SHIFTUP, ESC, ENTER,
//     *           F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, PAUSE, TAB, UP, DOWN, LEFT, RIGHT, PGDN, PGUP, DELETE, and BACKSPACE Adding + at
//     *           the start holds down the SHIFT key, adding ^ at the start holds down the CONTROL key, and adding % at the start holds down the ALT key
//     */
//    public void keyboardAction(String sKeys) {
//        Actions oKeyboard = new Actions(driver);
//        switch (sKeys) {
//            case "CTRL":
//                oKeyboard.sendKeys(Keys.CONTROL).perform();
//                break;
//            case "CTRLDN":
//                oKeyboard.keyDown(Keys.CONTROL).perform();
//                break;
//            case "CTRLUP":
//                oKeyboard.keyUp(Keys.CONTROL).perform();
//                break;
//            case "ALT":
//                oKeyboard.sendKeys(Keys.ALT).perform();
//                break;
//            case "ALTDN":
//                oKeyboard.keyDown(Keys.ALT).perform();
//                break;
//            case "ALTUP":
//                oKeyboard.keyUp(Keys.ALT).perform();
//                break;
//            case "SHIFTDN":
//                oKeyboard.keyDown(Keys.SHIFT).perform();
//                break;
//            case "SHIFTUP":
//                oKeyboard.keyUp(Keys.SHIFT).perform();
//                break;
//            case "ESC":
//                oKeyboard.sendKeys(Keys.ESCAPE).perform();
//                break;
//            case "ENTER":
//                oKeyboard.sendKeys(Keys.ENTER).perform();
//                break;
//            case "F1":
//                oKeyboard.sendKeys(Keys.F1).perform();
//                break;
//            case "F2":
//                oKeyboard.sendKeys(Keys.F2).perform();
//                break;
//            case "F3":
//                oKeyboard.sendKeys(Keys.F3).perform();
//                break;
//            case "F4":
//                oKeyboard.sendKeys(Keys.F4).perform();
//                break;
//            case "F5":
//                oKeyboard.sendKeys(Keys.F5).perform();
//                break;
//            case "F6":
//                oKeyboard.sendKeys(Keys.F6).perform();
//                break;
//            case "F7":
//                oKeyboard.sendKeys(Keys.F7).perform();
//                break;
//            case "F8":
//                oKeyboard.sendKeys(Keys.F8).perform();
//                break;
//            case "F9":
//                oKeyboard.sendKeys(Keys.F9).perform();
//                break;
//            case "F10":
//                oKeyboard.sendKeys(Keys.F10).perform();
//                break;
//            case "F11":
//                oKeyboard.sendKeys(Keys.F11).perform();
//                break;
//            case "F12":
//                oKeyboard.sendKeys(Keys.F12).perform();
//                break;
//            case "PAUSE":
//                oKeyboard.sendKeys(Keys.PAUSE).perform();
//                break;
//            case "TAB":
//                oKeyboard.sendKeys(Keys.TAB).perform();
//                break;
//            case "UP":
//                oKeyboard.sendKeys(Keys.ARROW_UP).perform();
//                break;
//            case "DOWN":
//                oKeyboard.sendKeys(Keys.ARROW_DOWN).perform();
//                break;
//            case "LEFT":
//                oKeyboard.sendKeys(Keys.ARROW_LEFT).perform();
//                break;
//            case "RIGHT":
//                oKeyboard.sendKeys(Keys.ARROW_RIGHT).perform();
//                break;
//            case "PGDN":
//                oKeyboard.sendKeys(Keys.PAGE_DOWN).perform();
//                break;
//            case "PGUP":
//                oKeyboard.sendKeys(Keys.PAGE_UP).perform();
//                break;
//            case "DELETE":
//                oKeyboard.sendKeys(Keys.DELETE).perform();
//                break;
//            case "BACKSPACE":
//                oKeyboard.sendKeys(Keys.BACK_SPACE).perform();
//                break;
//            default:
//                if (sKeys.startsWith("+")) // Shift
//                {
//                    sKeys = sKeys.substring(1).toLowerCase();
//                    if (sKeys.startsWith("^")) // Control
//                    {
//                        sKeys = sKeys.substring(1);
//                        if (sKeys.startsWith("%")) // Alt
//                        {
//                            sKeys = sKeys.substring(1);
//                            oKeyboard.keyDown(Keys.SHIFT).keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys(sKeys).keyUp(Keys.SHIFT).keyUp(Keys.CONTROL)
//                                    .keyUp(Keys.ALT).perform();
//                        } else {
//                            oKeyboard.keyDown(Keys.SHIFT).keyDown(Keys.CONTROL).sendKeys(sKeys).keyUp(Keys.SHIFT).keyUp(Keys.CONTROL).perform();
//                        }
//                    } else {
//                        oKeyboard.keyDown(Keys.SHIFT).sendKeys(sKeys).keyUp(Keys.SHIFT).perform();
//                    }
//                } else if (sKeys.startsWith("^")) // Control
//                {
//                    sKeys = sKeys.substring(1).toLowerCase();
//                    if (sKeys.startsWith("%")) // Alt
//                    {
//                        sKeys = sKeys.substring(1);
//                        oKeyboard.keyDown(Keys.CONTROL).keyDown(Keys.ALT).sendKeys(sKeys).keyUp(Keys.CONTROL).keyUp(Keys.ALT).perform();
//                    } else {
//                        oKeyboard.keyDown(Keys.CONTROL).sendKeys(sKeys).keyUp(Keys.CONTROL).perform();
//                    }
//                } else if (sKeys.startsWith("%")) // Alt
//                {
//                    sKeys = sKeys.substring(1).toLowerCase();
//                    oKeyboard.keyDown(Keys.ALT).sendKeys(sKeys).keyUp(Keys.ALT).perform();
//                } else {
//                    oKeyboard.sendKeys(sKeys).perform();
//                }
//                break;
//        }
//    }
//
//
//
//
//}