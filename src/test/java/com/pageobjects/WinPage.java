///*
// *  © [2022] Qualitest. All rights reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *     http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.pageobjects;
//
////import com.framework.components.ApplitoolsOperations;
//import com.framework.reusable.WinReusableComponents;
//import org.testng.Assert;
//
//public class WinPage extends WinReusableComponents {
//
//
//    // protected ApplitoolsOperations appliTools = new ApplitoolsOperations();
//
//    protected void launchApp(String url) {
//        launchApp(url);
//        // maximizeWindow();
//        // appliTools.captureContent("");
//    }
//
//    public void clickPageButtonbyName(String buttonName) {
//        clickButtonbyName(buttonName);
//
//    }
//    public void clickPageButtonbyAccessibilityID(String accessibleID) {
//        clickButtonbyAccessibleID(accessibleID);
//
//    }
//
//    public void validateText( String expectedValue, String fieldName) {
//        //textValidation(textValue, fieldName );
//
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("expected value - "+expectedValue);
//        String actualValue = driver.findElementByName(fieldName).getAttribute("Value.Value");
//        System.out.println("actual value - "+actualValue);
//        if (actualValue == null || actualValue.trim().isEmpty()){
//            Assert.assertTrue(true);
//            System.out.println("The WinApp Edit TimeDate test case is passed");
//        } else
//            Assert.assertTrue(false);
//
//
//    }
//
//}