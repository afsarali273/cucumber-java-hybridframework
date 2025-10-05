/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.selenium;

import com.framework.components.FrameworkException;
import com.framework.components.Settings;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WindowsDriverFactory {

    private static Properties properties=Settings.getInstance();

    private WindowsDriverFactory() {
        // To prevent external instantiation of this class
    }

    /**
     * Function to return the object for WindowsDriver {@link WindowsDriver} object
     * 
     * @param executionMode
     *            URL of the Windows URL
     * 
     * @return Instance of the {@link WindowsDriver object
     */
    @SuppressWarnings("rawtypes")
    public static WindowsDriver getWindowsDriver(ExecutionMode executionMode) {

        WindowsDriver driver = null;
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        try {
            switch (executionMode) {

                case WINDOWS:

                    try {

                        // Start the WinAppDriver Programmatically
//                        String command  = properties.getProperty("WindowsDriverPath");
//                        ProcessBuilder builder = new ProcessBuilder(command).inheritIO();
//                        Process process = builder.start();
//                       File fileDirectory = new File(command);
//                       Runtime.getRuntime().exec(new String[]{"cmd","/C","start WinAppDriver.exe"}, null, fileDirectory);
                       // Process myappProcess = Runtime.getRuntime().exec("powershell.exe Start-Process command -verb RunAs");

                     // Runtime.getRuntime().exec("cmd /c command");
                        DesiredCapabilities capabilities = new DesiredCapabilities();
                        capabilities.setCapability("app", properties.getProperty("WindowsAppPath"));
                        capabilities.setCapability("platformName", properties.getProperty("WindowsPlatformName"));
                        capabilities.setCapability("deviceName", properties.getProperty("WindowsDeviceName"));
                        String url = properties.getProperty("WindowsMachineURL")+":"+properties.getProperty("WindowsMachinePort");
                        driver = new WindowsDriver(new URL(url), capabilities);
                        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                    } catch (MalformedURLException e) {
                        throw new FrameworkException(
                                "The android driver invokation has problem, please re-check the capabilities or Start Appium");
                    }
                    break;

                default:
                    throw new FrameworkException("Unhandled ExecutionMode!");

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new FrameworkException(
                    "The appium driver invocation created a problem , please check the capabilities");
        }

        return driver;

    }

}
