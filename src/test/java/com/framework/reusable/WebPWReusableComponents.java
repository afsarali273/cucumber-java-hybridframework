/*
 *  © [2022] Qualitest. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.framework.reusable;

import java.util.List;
import java.util.Properties;

import com.framework.components.FrameworkParameters;
import com.framework.components.RestAssuredUtils;
import com.framework.components.ScriptHelper;
import com.framework.components.Settings;
import com.framework.data.FrameworkDataTable;
import com.framework.report.Status;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;

/**
 * Abstract base class for Playwright-based reusable libraries
 * 
 * @author Qualitest
 */
public abstract class WebPWReusableComponents extends GenericResuableComponents {

	/**
	 * The {@link FrameworkDataTable} object (passed from the test script)
	 */
	protected FrameworkDataTable dataTable;

	/**
	 * The Playwright objects
	 */
	protected Playwright playwright;
	protected Browser browser;
	protected BrowserContext context;
	protected Page page;

	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library
	 * from another)
	 */
	protected ScriptHelper scriptHelper;

	/**
	 * The {@link Properties} object with settings loaded from the framework
	 * properties file
	 */
	protected Properties properties;

	/**
	 * The {@link RestAssuredUtils} object
	 */
	protected RestAssuredUtils apiDriver;

	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters;

	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the
	 * objects wrapped by it
	 * 
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public WebPWReusableComponents(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		
		if (scriptHelper != null) {
			this.dataTable = scriptHelper.getDataTable();
			this.report = scriptHelper.getReport();
			this.apiDriver = scriptHelper.getApiDriver();
			properties = Settings.getInstance();
			frameworkParameters = FrameworkParameters.getInstance();
			
			// Initialize Playwright objects from ScriptHelper
			this.playwright = scriptHelper.getPlaywright();
			this.browser = scriptHelper.getPlaywrightBrowser();
			this.context = scriptHelper.getPlaywrightContext();
			this.page = scriptHelper.getPlaywrightPage();
		}
	}

	/**
	 * Constructor for standalone usage
	 */
	public WebPWReusableComponents() {
		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
		// Get page instance from Hooks if available
		if (page == null) {
			page = com.cucumber.steps.Hooks.getPlaywrightPageInstance();
		}
	}

	// ========== PAGE NAVIGATION ==========

	/**
	 * Navigate to URL
	 */
	public void navigateTo(String url) {
		try {
			page.navigate(url);
			addTestLog("Navigate", "Navigated to: " + url, Status.PASS);
		} catch (Exception e) {
			addTestLog("Navigate", "Failed to navigate: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Reload page
	 */
	public void reloadPage() {
		try {
			page.reload();
			addTestLog("Reload", "Page reloaded successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Reload", "Failed to reload: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Go back
	 */
	public void goBack() {
		try {
			page.goBack();
			addTestLog("Go Back", "Navigated back successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Go Back", "Failed to go back: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Go forward
	 */
	public void goForward() {
		try {
			page.goForward();
			addTestLog("Go Forward", "Navigated forward successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Go Forward", "Failed to go forward: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== ELEMENT INTERACTIONS ==========

	/**
	 * Click element
	 */
	public void click(String selector) {
		try {
			page.click(selector);
			addTestLog("Click", "Clicked element: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Click", "Failed to click: " + selector + " - " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Double click element
	 */
	public void doubleClick(String selector) {
		try {
			page.dblclick(selector);
			addTestLog("Double Click", "Double clicked: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Double Click", "Failed to double click: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Right click element
	 */
	public void rightClick(String selector) {
		try {
			page.click(selector, new Page.ClickOptions().setButton(MouseButton.RIGHT));
			addTestLog("Right Click", "Right clicked: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Right Click", "Failed to right click: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Fill text input
	 */
	public void fill(String selector, String text) {
		try {
			page.fill(selector, text);
			addTestLog("Fill", "Filled text '" + text + "' in: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Fill", "Failed to fill text: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Type text with delay
	 */
	public void type(String selector, String text, int delay) {
		try {
			page.type(selector, text, new Page.TypeOptions().setDelay(delay));
			addTestLog("Type", "Typed text '" + text + "' in: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Type", "Failed to type text: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Clear input field
	 */
	public void clear(String selector) {
		try {
			page.fill(selector, "");
			addTestLog("Clear", "Cleared field: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Clear", "Failed to clear field: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== ELEMENT QUERIES ==========

	/**
	 * Get element text
	 */
	public String getText(String selector) {
		try {
			String text = page.textContent(selector);
			addTestLog("Get Text", "Retrieved text from: " + selector, Status.PASS);
			return text;
		} catch (Exception e) {
			addTestLog("Get Text", "Failed to get text: " + e.getMessage(), Status.FAIL);
			return "";
		}
	}

	/**
	 * Get element attribute
	 */
	public String getAttribute(String selector, String attribute) {
		try {
			String value = page.getAttribute(selector, attribute);
			addTestLog("Get Attribute", "Retrieved attribute '" + attribute + "' from: " + selector, Status.PASS);
			return value;
		} catch (Exception e) {
			addTestLog("Get Attribute", "Failed to get attribute: " + e.getMessage(), Status.FAIL);
			return "";
		}
	}

	/**
	 * Get input value
	 */
	public String getValue(String selector) {
		try {
			String value = page.inputValue(selector);
			addTestLog("Get Value", "Retrieved value from: " + selector, Status.PASS);
			return value;
		} catch (Exception e) {
			addTestLog("Get Value", "Failed to get value: " + e.getMessage(), Status.FAIL);
			return "";
		}
	}

	// ========== ELEMENT STATE CHECKS ==========

	/**
	 * Check if element is visible
	 */
	public boolean isVisible(String selector) {
		try {
			return page.isVisible(selector);
		} catch (Exception e) {
			addTestLog("Is Visible", "Failed to check visibility: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}

	/**
	 * Check if element is enabled
	 */
	public boolean isEnabled(String selector) {
		try {
			return page.isEnabled(selector);
		} catch (Exception e) {
			addTestLog("Is Enabled", "Failed to check enabled state: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}

	/**
	 * Check if element is checked
	 */
	public boolean isChecked(String selector) {
		try {
			return page.isChecked(selector);
		} catch (Exception e) {
			addTestLog("Is Checked", "Failed to check state: " + e.getMessage(), Status.FAIL);
			return false;
		}
	}

	// ========== WAIT METHODS ==========

	/**
	 * Wait for element to be visible
	 */
	public void waitForVisible(String selector, int timeout) {
		try {
			page.waitForSelector(selector, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.VISIBLE)
					.setTimeout(timeout * 1000));
			addTestLog("Wait Visible", "Element became visible: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait Visible", "Element not visible: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Wait for element to be hidden
	 */
	public void waitForHidden(String selector, int timeout) {
		try {
			page.waitForSelector(selector, new Page.WaitForSelectorOptions()
					.setState(WaitForSelectorState.HIDDEN)
					.setTimeout(timeout * 1000));
			addTestLog("Wait Hidden", "Element became hidden: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait Hidden", "Element still visible: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Wait for page load
	 */
	public void waitForLoad() {
		try {
			page.waitForLoadState(LoadState.LOAD);
			addTestLog("Wait Load", "Page loaded successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait Load", "Page load timeout: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Wait for network idle
	 */
	public void waitForNetworkIdle() {
		try {
			page.waitForLoadState(LoadState.NETWORKIDLE);
			addTestLog("Wait Network", "Network became idle", Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait Network", "Network idle timeout: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== DROPDOWN OPERATIONS ==========

	/**
	 * Select dropdown by value
	 */
	public void selectByValue(String selector, String value) {
		try {
			page.selectOption(selector, new SelectOption().setValue(value));
			addTestLog("Select Value", "Selected value '" + value + "' in: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Select Value", "Failed to select: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Select dropdown by text
	 */
	public void selectByText(String selector, String text) {
		try {
			page.selectOption(selector, new SelectOption().setLabel(text));
			addTestLog("Select Text", "Selected text '" + text + "' in: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Select Text", "Failed to select: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== FRAME OPERATIONS ==========

	/**
	 * Switch to frame by selector
	 */
	public Frame switchToFrame(String selector) {
		try {
			Frame frame = page.frame(selector);
			addTestLog("Switch Frame", "Switched to frame: " + selector, Status.PASS);
			return frame;
		} catch (Exception e) {
			addTestLog("Switch Frame", "Failed to switch frame: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}

	// ========== KEYBOARD ACTIONS ==========

	/**
	 * Press key
	 */
	public void pressKey(String key) {
		try {
			page.keyboard().press(key);
			addTestLog("Press Key", "Pressed key: " + key, Status.PASS);
		} catch (Exception e) {
			addTestLog("Press Key", "Failed to press key: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Press key combination
	 */
	public void pressKeys(String... keys) {
		try {
			for (String key : keys) {
				page.keyboard().press(key);
			}
			addTestLog("Press Keys", "Pressed keys: " + String.join(", ", keys), Status.PASS);
		} catch (Exception e) {
			addTestLog("Press Keys", "Failed to press keys: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== MOUSE ACTIONS ==========

	/**
	 * Hover over element
	 */
	public void hover(String selector) {
		try {
			page.hover(selector);
			addTestLog("Hover", "Hovered over: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Hover", "Failed to hover: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Drag and drop
	 */
	public void dragAndDrop(String sourceSelector, String targetSelector) {
		try {
			page.dragAndDrop(sourceSelector, targetSelector);
			addTestLog("Drag Drop", "Dragged from " + sourceSelector + " to " + targetSelector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Drag Drop", "Failed to drag and drop: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== SCREENSHOT ==========

	/**
	 * Take screenshot
	 */
	public void takeScreenshot(String fileName) {
		try {
			page.screenshot(new Page.ScreenshotOptions().setPath(java.nio.file.Paths.get(fileName)));
			addTestLog("Screenshot", "Screenshot saved: " + fileName, Status.PASS);
		} catch (Exception e) {
			addTestLog("Screenshot", "Failed to take screenshot: " + e.getMessage(), Status.FAIL);
		}
	}

	/**
	 * Take element screenshot
	 */
	public void takeElementScreenshot(String selector, String fileName) {
		try {
			page.locator(selector).screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(fileName)));
			addTestLog("Element Screenshot", "Element screenshot saved: " + fileName, Status.PASS);
		} catch (Exception e) {
			addTestLog("Element Screenshot", "Failed to take element screenshot: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== JAVASCRIPT EXECUTION ==========

	/**
	 * Execute JavaScript
	 */
	public Object executeScript(String script, Object... args) {
		try {
			Object result = page.evaluate(script, args);
			addTestLog("Execute Script", "JavaScript executed successfully", Status.PASS);
			return result;
		} catch (Exception e) {
			addTestLog("Execute Script", "Failed to execute JavaScript: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}

	/**
	 * Scroll to element
	 */
	public void scrollToElement(String selector) {
		try {
			page.locator(selector).scrollIntoViewIfNeeded();
			addTestLog("Scroll", "Scrolled to element: " + selector, Status.PASS);
		} catch (Exception e) {
			addTestLog("Scroll", "Failed to scroll: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== ALERT HANDLING ==========

	/**
	 * Handle alert dialog
	 */
	public void handleAlert(boolean accept, String text) {
		try {
			page.onDialog(dialog -> {
				if (text != null && !text.isEmpty()) {
					dialog.accept(text);
				} else if (accept) {
					dialog.accept();
				} else {
					dialog.dismiss();
				}
			});
			addTestLog("Handle Alert", "Alert handled successfully", Status.PASS);
		} catch (Exception e) {
			addTestLog("Handle Alert", "Failed to handle alert: " + e.getMessage(), Status.FAIL);
		}
	}

	// ========== UTILITY METHODS ==========

	/**
	 * Get page title
	 */
	public String getTitle() {
		try {
			return page.title();
		} catch (Exception e) {
			addTestLog("Get Title", "Failed to get title: " + e.getMessage(), Status.FAIL);
			return "";
		}
	}

	/**
	 * Get current URL
	 */
	public String getCurrentUrl() {
		try {
			return page.url();
		} catch (Exception e) {
			addTestLog("Get URL", "Failed to get URL: " + e.getMessage(), Status.FAIL);
			return "";
		}
	}

	/**
	 * Get all elements matching selector
	 */
	public List<Locator> getAllElements(String selector) {
		try {
			return page.locator(selector).all();
		} catch (Exception e) {
			addTestLog("Get Elements", "Failed to get elements: " + e.getMessage(), Status.FAIL);
			return null;
		}
	}

	/**
	 * Get element count
	 */
	public int getElementCount(String selector) {
		try {
			return page.locator(selector).count();
		} catch (Exception e) {
			addTestLog("Get Count", "Failed to get count: " + e.getMessage(), Status.FAIL);
			return 0;
		}
	}

	/**
	 * Wait for specified time
	 */
	public void waitFor(int seconds) {
		try {
			page.waitForTimeout(seconds * 1000);
			addTestLog("Wait", "Waited for " + seconds + " seconds", Status.PASS);
		} catch (Exception e) {
			addTestLog("Wait", "Wait failed: " + e.getMessage(), Status.FAIL);
		}
	}
}