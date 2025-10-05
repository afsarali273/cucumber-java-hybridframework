package com.framework.playwright;

import java.util.Properties;
import com.framework.components.FrameworkException;
import com.framework.components.Settings;
import com.framework.selenium.SeleniumTestParameters;
import com.microsoft.playwright.*;

public class PlaywrightDriverFactory {

	private static Properties properties = Settings.getInstance();
	
	public static Browser createBrowserInstance(SeleniumTestParameters testParameters) {
		Browser browser = null;
		Playwright playwright = Playwright.create();
		
		try {
			switch (testParameters.getBrowser()) {
			case CHROME:
				browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
						.setHeadless(Boolean.parseBoolean(properties.getProperty("PlaywrightHeadless", "false"))));
				break;
				
			case FIREFOX:
				browser = playwright.firefox().launch(new BrowserType.LaunchOptions()
						.setHeadless(Boolean.parseBoolean(properties.getProperty("PlaywrightHeadless", "false"))));
				break;
				
			case SAFARI:
				browser = playwright.webkit().launch(new BrowserType.LaunchOptions()
						.setHeadless(Boolean.parseBoolean(properties.getProperty("PlaywrightHeadless", "false"))));
				break;
				
			default:
				throw new FrameworkException("Unhandled browser: " + testParameters.getBrowser());
			}
		} catch (Exception e) {
			throw new FrameworkException("Error creating Playwright browser: " + e.getMessage());
		}
		
		return browser;
	}
	
	public static BrowserContext createBrowserContext(Browser browser) {
		try {
			Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
			contextOptions.setViewportSize(1920, 1080);
			return browser.newContext(contextOptions);
		} catch (Exception e) {
			throw new FrameworkException("Error creating browser context: " + e.getMessage());
		}
	}
	
	public static Page createPage(BrowserContext context) {
		try {
			Page page = context.newPage();
			page.setDefaultTimeout(30000);
			return page;
		} catch (Exception e) {
			throw new FrameworkException("Error creating page: " + e.getMessage());
		}
	}
}