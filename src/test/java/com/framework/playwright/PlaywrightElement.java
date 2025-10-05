package com.framework.playwright;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/**
 * Wrapper class for Playwright Locator with enhanced functionality
 */
public class PlaywrightElement {
    
    private final Locator locator;
    private final String selector;
    
    public PlaywrightElement(Page page, String selector) {
        this.selector = selector;
        this.locator = page.locator(selector);
    }
    
    public PlaywrightElement(Locator locator, String selector) {
        this.locator = locator;
        this.selector = selector;
    }
    
    // Basic interactions
    public void click() {
        locator.click();
    }
    
    public void doubleClick() {
        locator.dblclick();
    }
    
    public void rightClick() {
        locator.click(new Locator.ClickOptions().setButton(com.microsoft.playwright.options.MouseButton.RIGHT));
    }
    
    public void fill(String text) {
        locator.fill(text);
    }
    
    public void type(String text) {
        locator.type(text);
    }
    
    public void clear() {
        locator.fill("");
    }
    
    public void hover() {
        locator.hover();
    }
    
    public void focus() {
        locator.focus();
    }
    
    // State checks
    public boolean isVisible() {
        return locator.isVisible();
    }
    
    public boolean isEnabled() {
        return locator.isEnabled();
    }
    
    public boolean isChecked() {
        return locator.isChecked();
    }
    
    public boolean isHidden() {
        return locator.isHidden();
    }
    
    // Content retrieval
    public String getText() {
        return locator.textContent();
    }
    
    public String getAttribute(String name) {
        return locator.getAttribute(name);
    }
    
    public String getValue() {
        return locator.inputValue();
    }
    
    public String getInnerHTML() {
        return locator.innerHTML();
    }
    
    // Wait methods
    public void waitForVisible(int timeout) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(timeout * 1000));
    }
    
    public void waitForHidden(int timeout) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN)
                .setTimeout(timeout * 1000));
    }
    
    // Dropdown operations
    public void selectByValue(String value) {
        locator.selectOption(new SelectOption().setValue(value));
    }
    
    public void selectByText(String text) {
        locator.selectOption(new SelectOption().setLabel(text));
    }
    
    // Advanced operations
    public void scrollIntoView() {
        locator.scrollIntoViewIfNeeded();
    }
    
    public void dragTo(PlaywrightElement target) {
        locator.dragTo(target.getLocator());
    }
    
    public void screenshot(String fileName) {
        locator.screenshot(new Locator.ScreenshotOptions().setPath(java.nio.file.Paths.get(fileName)));
    }
    
    // Getters
    public Locator getLocator() {
        return locator;
    }
    
    public String getSelector() {
        return selector;
    }
    
    public int count() {
        return locator.count();
    }
    
    public PlaywrightElement nth(int index) {
        return new PlaywrightElement(locator.nth(index), selector + ":nth-child(" + (index + 1) + ")");
    }
    
    public PlaywrightElement first() {
        return new PlaywrightElement(locator.first(), selector + ":first-child");
    }
    
    public PlaywrightElement last() {
        return new PlaywrightElement(locator.last(), selector + ":last-child");
    }
    
    // ========== ASSERTION METHODS ==========
    
    /**
     * Assert element is visible
     */
    public void assertVisible() {
        locator.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.VISIBLE));
    }
    
    /**
     * Assert element is hidden
     */
    public void assertHidden() {
        locator.waitFor(new Locator.WaitForOptions().setState(com.microsoft.playwright.options.WaitForSelectorState.HIDDEN));
    }
    
    /**
     * Assert element is enabled
     */
    public void assertEnabled() {
        if (!locator.isEnabled()) {
            throw new AssertionError("Element is not enabled: " + selector);
        }
    }
    
    /**
     * Assert element is disabled
     */
    public void assertDisabled() {
        if (locator.isEnabled()) {
            throw new AssertionError("Element is not disabled: " + selector);
        }
    }
    
    /**
     * Assert element is checked
     */
    public void assertChecked() {
        if (!locator.isChecked()) {
            throw new AssertionError("Element is not checked: " + selector);
        }
    }
    
    /**
     * Assert element is unchecked
     */
    public void assertUnchecked() {
        if (locator.isChecked()) {
            throw new AssertionError("Element is checked: " + selector);
        }
    }
    
    /**
     * Assert element text equals expected
     */
    public void assertTextEquals(String expectedText) {
        String actualText = locator.textContent();
        if (!expectedText.equals(actualText)) {
            throw new AssertionError("Text mismatch. Expected: '" + expectedText + "', Actual: '" + actualText + "'");
        }
    }
    
    /**
     * Assert element text contains expected
     */
    public void assertTextContains(String expectedText) {
        String actualText = locator.textContent();
        if (actualText == null || !actualText.contains(expectedText)) {
            throw new AssertionError("Text does not contain expected. Expected: '" + expectedText + "', Actual: '" + actualText + "'");
        }
    }
    
    /**
     * Assert element value equals expected
     */
    public void assertValueEquals(String expectedValue) {
        String actualValue = locator.inputValue();
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError("Value mismatch. Expected: '" + expectedValue + "', Actual: '" + actualValue + "'");
        }
    }
    
    /**
     * Assert element attribute equals expected
     */
    public void assertAttributeEquals(String attributeName, String expectedValue) {
        String actualValue = locator.getAttribute(attributeName);
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError("Attribute '" + attributeName + "' mismatch. Expected: '" + expectedValue + "', Actual: '" + actualValue + "'");
        }
    }
    
    /**
     * Assert element attribute contains expected
     */
    public void assertAttributeContains(String attributeName, String expectedValue) {
        String actualValue = locator.getAttribute(attributeName);
        if (actualValue == null || !actualValue.contains(expectedValue)) {
            throw new AssertionError("Attribute '" + attributeName + "' does not contain expected. Expected: '" + expectedValue + "', Actual: '" + actualValue + "'");
        }
    }
    
    /**
     * Assert element count equals expected
     */
    public void assertCountEquals(int expectedCount) {
        int actualCount = locator.count();
        if (expectedCount != actualCount) {
            throw new AssertionError("Count mismatch. Expected: " + expectedCount + ", Actual: " + actualCount);
        }
    }
    
    /**
     * Assert element count is greater than expected
     */
    public void assertCountGreaterThan(int expectedCount) {
        int actualCount = locator.count();
        if (actualCount <= expectedCount) {
            throw new AssertionError("Count not greater than expected. Expected > " + expectedCount + ", Actual: " + actualCount);
        }
    }
    
    /**
     * Assert element has class
     */
    public void assertHasClass(String className) {
        String classAttribute = locator.getAttribute("class");
        if (classAttribute == null || !classAttribute.contains(className)) {
            throw new AssertionError("Element does not have class: " + className + ". Actual classes: " + classAttribute);
        }
    }
    
    /**
     * Assert element CSS property equals expected
     */
    public void assertCSSEquals(String propertyName, String expectedValue) {
        Object actualValue = locator.evaluate("el => getComputedStyle(el)." + propertyName);
        if (!expectedValue.equals(actualValue)) {
            throw new AssertionError("CSS property '" + propertyName + "' mismatch. Expected: '" + expectedValue + "', Actual: '" + actualValue + "'");
        }
    }
    
    /**
     * Assert element is focused
     */
    public void assertFocused() {
        Boolean isFocused = (Boolean) locator.evaluate("el => el === document.activeElement");
        if (!isFocused) {
            throw new AssertionError("Element is not focused: " + selector);
        }
    }
    
    /**
     * Assert element is not focused
     */
    public void assertNotFocused() {
        Boolean isFocused = (Boolean) locator.evaluate("el => el === document.activeElement");
        if (isFocused) {
            throw new AssertionError("Element is focused: " + selector);
        }
    }
    
    /**
     * Assert element is editable
     */
    public void assertEditable() {
        if (!locator.isEditable()) {
            throw new AssertionError("Element is not editable: " + selector);
        }
    }
    
    /**
     * Assert element is not editable
     */
    public void assertNotEditable() {
        if (locator.isEditable()) {
            throw new AssertionError("Element is editable: " + selector);
        }
    }
    
    /**
     * Assert element exists in DOM
     */
    public void assertExists() {
        if (locator.count() == 0) {
            throw new AssertionError("Element does not exist: " + selector);
        }
    }
    
    /**
     * Assert element does not exist in DOM
     */
    public void assertNotExists() {
        if (locator.count() > 0) {
            throw new AssertionError("Element exists: " + selector);
        }
    }
    
    /**
     * Assert element text matches regex pattern
     */
    public void assertTextMatches(String regexPattern) {
        String actualText = locator.textContent();
        if (actualText == null || !actualText.matches(regexPattern)) {
            throw new AssertionError("Text does not match pattern. Pattern: '" + regexPattern + "', Actual: '" + actualText + "'");
        }
    }
    
    /**
     * Assert element is within viewport
     */
    public void assertInViewport() {
        Boolean inViewport = (Boolean) locator.evaluate("el => { const rect = el.getBoundingClientRect(); return rect.top >= 0 && rect.left >= 0 && rect.bottom <= window.innerHeight && rect.right <= window.innerWidth; }");
        if (!inViewport) {
            throw new AssertionError("Element is not in viewport: " + selector);
        }
    }
}